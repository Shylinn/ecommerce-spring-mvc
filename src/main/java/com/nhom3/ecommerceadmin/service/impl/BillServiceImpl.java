package com.nhom3.ecommerceadmin.service.impl;


import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.io.*;


import com.nhom3.ecommerceadmin.dto.BillCreateDto;
import com.nhom3.ecommerceadmin.dto.BillDto;
import com.nhom3.ecommerceadmin.mapper.BillMapper;
import com.nhom3.ecommerceadmin.models.Bill;
import com.nhom3.ecommerceadmin.models.BillDetails;
import com.nhom3.ecommerceadmin.repository.BillRepository;
import com.nhom3.ecommerceadmin.repository.CustomerRepository;
import com.nhom3.ecommerceadmin.repository.StaffRepository;
import com.nhom3.ecommerceadmin.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.nhom3.ecommerceadmin.mapper.BillMapper.mapToBill;
import static com.nhom3.ecommerceadmin.mapper.BillMapper.mapToBillDto;

@Service
public class BillServiceImpl implements BillService {

    private BillRepository billRepository;
    private final StaffRepository staffRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public BillServiceImpl(BillRepository billRepository,
                           StaffRepository staffRepository,
                           CustomerRepository customerRepository) {
        this.billRepository = billRepository;
        this.staffRepository = staffRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Bill saveBill(BillCreateDto billCreateDto) {
        BillDto billDto = BillDto.builder()
                .staff(staffRepository.findById(billCreateDto.staff).get())
                .customer(customerRepository.findById(Long.parseLong(billCreateDto.customer)).get())
                .build();
        Bill bill = mapToBill(billDto);
        return billRepository.save(bill);
    }

    @Override
    public ByteArrayInputStream exportBillToPdf(Long billId) {
        BillDto billDto = findBillById(billId);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDoc);

        try {
            // Lấy font từ resources folder
            String fontPath = getClass().getResource("/static/fonts/Roboto-Regular.ttf").getPath();
            PdfFont font = PdfFontFactory.createFont(fontPath, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

            // Sửa Date formatter sang Vietnamese locale
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.forLanguageTag("vi-VN"));

            // Sửa Number formatter sang đơn vị Việt Nam
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            // Ghi tiêu đề hóa đơn
            document.add(new Paragraph("Chi tiết hóa đơn")
                    .setFont(font)
                    .setFontSize(18));

            // Ghi thông tin chung bill
            document.add(new Paragraph("Mã hóa đơn: " + billDto.getId()).setFont(font));
            document.add(new Paragraph("Ngày tạo: " + billDto.getCreatedAt().format(dateFormatter)).setFont(font));
            document.add(new Paragraph("Nhân viên tạo HĐ: " + billDto.getStaff().getFullName()).setFont(font));
            document.add(new Paragraph("Khách hàng: " + billDto.getCustomer().getFullName()).setFont(font));
            document.add(new Paragraph("Giá trị hóa đơn: " + currencyFormatter.format(billDto.getValue())).setFont(font));

            // Ghi thông tin chi tiết hóa đơn
            document.add(new Paragraph("Chi tiết hóa đơn:")
                    .setFont(font)
                    .setFontSize(14));

            Table table = new Table(new float[]{4, 2, 2, 2});
            table.setWidth(UnitValue.createPercentValue(100));
            table.addHeaderCell(new Cell().add(new Paragraph("Tên sản phẩm").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("Số lượng").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("Giá").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("Tổng").setFont(font)));

            for (BillDetails detail : billDto.getDetails()) {
                table.addCell(new Cell().add(new Paragraph(detail.getProduct().getName()).setFont(font)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(detail.getQuantity())).setFont(font)));
                table.addCell(new Cell().add(new Paragraph(currencyFormatter.format(detail.getProduct().getPrice())).setFont(font)));
                table.addCell(new Cell().add(new Paragraph(currencyFormatter.format(detail.getProduct().getPrice() * detail.getQuantity())).setFont(font)));
            }

            document.add(table);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public void updateBillDetails(BillDto updatedBillDto) {
        // Lấy thông tin hóa đơn từ cơ sở dữ liệu
        Bill existingBill = billRepository.findById(updatedBillDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hóa đơn"));

        // Cập nhật thông tin của hóa đơn từ dữ liệu gửi từ form
        existingBill.setCustomer(updatedBillDto.getCustomer());
        // Cập nhật chi tiết hóa đơn
        for (BillDetails updatedDetail : updatedBillDto.getDetails()) {
            // Tìm chi tiết hóa đơn tương ứng hoặc tạo mới nếu chưa có
            BillDetails existingDetail = existingBill.getDetails().stream()
                    .filter(detail -> detail.getId().equals(updatedDetail.getId()))
                    .findFirst()
                    .orElse(new BillDetails());

            // Cập nhật thông tin chi tiết hóa đơn
            existingDetail.setProduct(updatedDetail.getProduct());
            existingDetail.setQuantity(updatedDetail.getQuantity());

            // Thêm chi tiết hóa đơn vào danh sách chi tiết hóa đơn của hóa đơn nếu chưa tồn tại
            if (!existingBill.getDetails().contains(existingDetail)) {
                existingBill.getDetails().add(existingDetail);
            }
        }

        // Lưu lại thông tin hóa đơn đã cập nhật
        billRepository.save(existingBill);
    }

    @Override
    public List<BillDto> findAllBills() {
        List<Bill> bills = billRepository.findAll();
        return bills.stream().map(BillMapper::mapToBillDto).collect(Collectors.toList());
    }
    public void deleteBillById(Long id) {
        billRepository.deleteById(id);
    }

    @Override
    public BillDto findBillById(Long billId) {
        return mapToBillDto(billRepository.findById(billId).get());
    }
}
