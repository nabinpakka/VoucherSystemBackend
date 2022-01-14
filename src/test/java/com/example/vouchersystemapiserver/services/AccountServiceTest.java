package com.example.vouchersystemapiserver.services;

import com.example.vouchersystemapiserver.models.Account;
import com.example.vouchersystemapiserver.repo.AccountRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)

class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @MockBean
    AccountRepo accountRepo;

    Account account=new Account("user","pass");

    @Test
    void createAccount() {
        Mockito.when(accountRepo.save(Mockito.any())).thenReturn(null);
        UUID id=accountService.createAccount(account);
        assertEquals(account.getId(),id);
    }

    @Test
    void findAccountByUsername() {
        Mockito.when(accountRepo.findByUserName("user")).thenReturn(java.util.Optional.ofNullable(account));
        Account found=accountService.findAccountByUsername("user");
        assertEquals(found,account);

    }

    @Test
    void existsByUsername() {
        Mockito.when(accountRepo.findByUserName("user")).thenReturn(java.util.Optional.ofNullable(account));
        assertEquals(accountService.existsByUsername("user"),true);
        assertEquals(accountService.existsByUsername("name"),false);
    }
}