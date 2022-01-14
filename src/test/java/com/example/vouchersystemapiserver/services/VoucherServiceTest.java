package com.example.vouchersystemapiserver.services;

import com.example.vouchersystemapiserver.TokenGenerator.CodeConfig;
import com.example.vouchersystemapiserver.TokenGenerator.Token;
import com.example.vouchersystemapiserver.models.Voucher;
import com.example.vouchersystemapiserver.repo.VoucherRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class VoucherServiceTest {

    @MockBean
    VoucherRepository voucherRepository;

    @InjectMocks
    VoucherService voucherService;

    @Test
    void findVoucherByCode() {
        String code=Token.generate(CodeConfig.length(10));
        Voucher voucher=new Voucher(code,100);

        Mockito.when(voucherRepository.findVoucherByCode(code)).thenReturn(java.util.Optional.of(voucher));
        Optional<Voucher> voucher1=voucherService.findVoucherByCode(code);

        assertEquals(voucher1.get(),voucher);
    }

    @Test
    void redeemVoucher() {

        Mockito.when(voucherRepository.save(Mockito.any(Voucher.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArgument(0);
            }
        });

        String code=Token.generate(CodeConfig.length(10));
        Voucher voucher=new Voucher(code,100);

        int value=voucherService.redeemVoucher(voucher);
        assertEquals(value,100);
    }
}