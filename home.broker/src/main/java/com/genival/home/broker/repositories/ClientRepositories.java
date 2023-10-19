package com.genival.home.broker.repositories;

import com.genival.home.broker.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepositories extends JpaRepository<Client, Long> {

    Client findByLoginAndPassword(String login, String password);
}
