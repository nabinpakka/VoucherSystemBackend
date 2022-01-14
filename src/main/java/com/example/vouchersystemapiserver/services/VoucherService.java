package com.example.vouchersystemapiserver.services;

import com.example.vouchersystemapiserver.models.Voucher;
import com.example.vouchersystemapiserver.repo.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    //find voucher by code
    public Optional<Voucher> findVoucherByCode(String code){
        return voucherRepository.findVoucherByCode(code);
    }

    //update the voucher used status to true
    public int redeemVoucher(Voucher voucher){
        voucher.setUsed(true);
        voucher=voucherRepository.save(voucher);
        return voucher.getValue();
    }

    //delete voucher
    public void delete(Voucher voucher){
        voucherRepository.delete(voucher);
    }

    //delete voucher by id
    public void deleteById(int id){
        voucherRepository.deleteById(id);
    }

    //save new voucher into database
    public void create(ArrayList<Voucher> vouchers){
        voucherRepository.saveAll(vouchers);
    }

    //get all vouchers
    //only accessed by admin
    public ArrayList<Voucher> getAllVouchers(){
        return (ArrayList<Voucher>) voucherRepository.findAll();
    }

}
