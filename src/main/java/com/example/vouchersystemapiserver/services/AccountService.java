package com.example.vouchersystemapiserver.services;

import com.example.vouchersystemapiserver.models.Account;
import com.example.vouchersystemapiserver.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    public UUID createAccount(Account account){
        accountRepo.save(account);
        return account.getId();
    }

    public Account findAccountByUsername(String username){
        return accountRepo.findByUserName(username).get();
    }

    public boolean existsByUsername(String username){
        Optional<Account> account=accountRepo.findByUserName(username);

        if (account.isEmpty()){
            return false;
        }
        return  true;
    }
}
