package com.example.vouchersystemapiserver.controllers;

import com.example.vouchersystemapiserver.TokenGenerator.CodeConfig;
import com.example.vouchersystemapiserver.TokenGenerator.Token;
import com.example.vouchersystemapiserver.models.Voucher;
import com.example.vouchersystemapiserver.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/vouchers")
public class VoucherController {

    @Autowired
    VoucherService voucherService;

    @PostMapping()
    public ArrayList<Voucher> create(int value,int numberOfVoucher){
        CodeConfig codeConfig=CodeConfig.length(10);
        ArrayList<Voucher> vouchers=new ArrayList<>();

        for (int i=0;i<numberOfVoucher;i++){
            vouchers.add(new Voucher(Token.generate(codeConfig),value));
        }
        voucherService.create(vouchers);
        return vouchers;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteVoucher(@RequestParam("code") String code){
        Voucher voucher=voucherService.findVoucherByCode(code).get();

        if (voucher==null){
            return new ResponseEntity<String>("Voucher does not exists", HttpStatus.OK);
        }

        voucherService.delete(voucher);
        return new ResponseEntity<String>("Voucher removed successfully ", HttpStatus.OK);

    }

    @GetMapping()
    public ArrayList<Voucher> getAllVouchers(){
        return voucherService.getAllVouchers();
    }
}
