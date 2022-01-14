package com.example.vouchersystemapiserver.repo;

import com.example.vouchersystemapiserver.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepo extends JpaRepository<Account, UUID> {
    Optional<Account> findByUserName(String userName);
}
