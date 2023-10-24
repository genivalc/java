package com.genival.home.broker.repositories;

import com.genival.home.broker.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepositories extends JpaRepository<Account, Long> {
}
