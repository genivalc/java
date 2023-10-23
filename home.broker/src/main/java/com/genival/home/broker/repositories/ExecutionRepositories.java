package com.genival.home.broker.repositories;

import com.genival.home.broker.entities.Execution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecutionRepositories extends JpaRepository<Execution, Long> {
}
