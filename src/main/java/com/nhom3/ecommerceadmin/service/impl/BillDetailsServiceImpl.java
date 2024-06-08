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
import com.nhom3.ecommerceadmin.dto.BillCreateDto;
import com.nhom3.ecommerceadmin.dto.BillDto;
import com.nhom3.ecommerceadmin.mapper.BillMapper;
import com.nhom3.ecommerceadmin.models.Bill;
import com.nhom3.ecommerceadmin.models.BillDetails;
import com.nhom3.ecommerceadmin.repository.BillDetailsRepository;
import com.nhom3.ecommerceadmin.repository.BillRepository;
import com.nhom3.ecommerceadmin.repository.CustomerRepository;
import com.nhom3.ecommerceadmin.repository.StaffRepository;
import com.nhom3.ecommerceadmin.service.BillDetailsService;
import com.nhom3.ecommerceadmin.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.nhom3.ecommerceadmin.mapper.BillMapper.mapToBill;
import static com.nhom3.ecommerceadmin.mapper.BillMapper.mapToBillDto;

@Service
public class BillDetailsServiceImpl implements BillDetailsService {

    private BillRepository billRepository;
    private final BillDetailsRepository billDetailsRepository;

    @Autowired
    public BillDetailsServiceImpl(BillRepository billRepository,
                                  BillDetailsRepository billDetailsRepository)
    {
        this.billRepository = billRepository;
        this.billDetailsRepository = billDetailsRepository;
    }


    @Override
    public void deleteBillDetailById(Long id) {
        billDetailsRepository.deleteById(id);
    }
}
