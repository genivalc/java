package com.genival.home.broker.repositories;

import com.genival.home.broker.entities.AccountMovement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountMovementRepositories extends JpaRepository<AccountMovement, Long> {
}
