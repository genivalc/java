package com.genival.home.broker.repositories;

import com.genival.home.broker.entities.Ordem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdemRepositories extends JpaRepository<Ordem, Long> {
}
