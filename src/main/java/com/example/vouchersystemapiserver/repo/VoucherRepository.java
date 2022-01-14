package com.example.vouchersystemapiserver.repo;

import com.example.vouchersystemapiserver.models.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher,Integer> {
    Optional<Voucher> findVoucherByCode(String code);
}
