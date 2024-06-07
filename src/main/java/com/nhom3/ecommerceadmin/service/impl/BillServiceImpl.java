package com.nhom3.ecommerceadmin.service.impl;

import com.nhom3.ecommerceadmin.dto.BillCreateDto;
import com.nhom3.ecommerceadmin.dto.BillDto;
import com.nhom3.ecommerceadmin.mapper.BillMapper;
import com.nhom3.ecommerceadmin.models.Bill;
import com.nhom3.ecommerceadmin.repository.BillRepository;
import com.nhom3.ecommerceadmin.repository.CustomerRepository;
import com.nhom3.ecommerceadmin.repository.StaffRepository;
import com.nhom3.ecommerceadmin.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
