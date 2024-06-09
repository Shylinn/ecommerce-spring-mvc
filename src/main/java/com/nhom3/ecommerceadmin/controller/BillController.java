package com.nhom3.ecommerceadmin.controller;

import com.nhom3.ecommerceadmin.dto.BillDto;
import com.nhom3.ecommerceadmin.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@Controller
public class BillController {
    private BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/bills")
    public String listBillPage(Model model) {
        List<BillDto> bills = billService.findAllBills();
        model.addAttribute("bills", bills);
        return "bill-list";
    }
    @PostMapping("/bills/delete")
    public String deleteBill(@RequestParam("id") Long id) {
        billService.deleteBillById(id);
        return "redirect:/bills?deleteSuccess";
    }

    @GetMapping("/bills/{billId}")
    public String billDetail(@PathVariable("billId") Long billId, Model model) {
        BillDto billDto = billService.findBillById(billId);
        model.addAttribute("bill", billDto);
        return "bill-details";
    }

    @GetMapping("/bills/{billId}/export/pdf")
    public ResponseEntity<InputStreamResource> exportBillToPdf(@PathVariable("billId") Long billId) {
        ByteArrayInputStream bis = billService.exportBillToPdf(billId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=bill.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}