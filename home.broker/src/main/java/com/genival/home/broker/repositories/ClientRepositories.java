package com.genival.home.broker.repositories;

import com.genival.home.broker.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepositories extends JpaRepository<Client, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM tb_client where login = :login and password = :password")
    Client findByLoginAndPassword(String login, String password);
}
