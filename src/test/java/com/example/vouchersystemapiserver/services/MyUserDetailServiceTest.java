package com.example.vouchersystemapiserver.services;

import com.example.vouchersystemapiserver.models.Account;
import com.example.vouchersystemapiserver.models.MyUserDetail;
import com.example.vouchersystemapiserver.repo.AccountRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class MyUserDetailServiceTest {

    @InjectMocks
    MyUserDetailService myUserDetailService;

    @MockBean
    AccountRepo accountRepo;

    Account account=new Account("user","password");

    @Test
    void loadUserByUsername() {
        Mockito.when(accountRepo.findByUserName("user")).thenReturn(java.util.Optional.ofNullable(account));

        UserDetails userDetails=myUserDetailService.loadUserByUsername("user");

        assertEquals(userDetails.getUsername(),account.getUserName());
        assertEquals(userDetails.getPassword(),account.getPassword());
    }
}