package com.example.vouchersystemapiserver.services;

import com.example.vouchersystemapiserver.models.Account;
import com.example.vouchersystemapiserver.models.MyUserDetail;
import com.example.vouchersystemapiserver.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    AccountRepo accountRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepo.findByUserName(username);

        account.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        return account.map(MyUserDetail::new).get();
    }


}
