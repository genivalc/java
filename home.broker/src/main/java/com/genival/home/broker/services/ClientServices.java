package com.genival.home.broker.services;

import com.genival.home.broker.entities.Client;
import com.genival.home.broker.repositories.ClientRepositories;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClientServices {

    @Autowired
    private ClientRepositories clientRepositories;

    public boolean save(Client client) {
        clientRepositories.save(client);
        return true;
    }

    private Client findById(long id) {
        return clientRepositories.findById(id).orElse(null);
    }

    public boolean isEmpty() {
        return clientRepositories.count() == 0;
    }

    public List<Client> findAll() {
        // Your code to fetch all clients from the repository
        return clientRepositories.findAll();
    }

    private StringBuilder printList(List<Client> cliente) {
        StringBuilder sb = new StringBuilder();
        for (Client c : cliente) {
            sb.append(c).append("\n");
        }
        return sb;
    }

    public void remover(Long elemento) {
        clientRepositories.deleteById(elemento);
    }

    public Client upgrade(Client client) {
        return clientRepositories.save(client);
    }

    public Client searchCustomerLogin(String login, String password) {
        return clientRepositories.findByLoginAndPassword(login, password);
    }
}
