package com.genival.home.broker.services;

import com.genival.home.broker.entities.Client;
import com.genival.home.broker.repositories.ClientRepositories;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClientServices {

    @Autowired
    private ClientRepositories clientRepositories;

    public boolean save(Client client) {
        if(clientRepositories.findByLogin(client.getLogin()) != null) return  false;

        String encryptedPassword = new BCryptPasswordEncoder().encode(client.getPassword());
        Client newClient = new Client(client.getName(),client.getAddress(), client.getCPF(), client.getPhone() ,client.getLogin() ,encryptedPassword, client.getUserType());

        clientRepositories.save(newClient);
        return true;
    }

    @Transactional(readOnly = true)
    public Client findById(long id) {
        return clientRepositories.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public boolean isEmpty() {
        return clientRepositories.count() == 0;
    }

    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientRepositories.findAll();
    }

    @Transactional(readOnly = true)
    private StringBuilder printList(List<Client> cliente) {
        StringBuilder sb = new StringBuilder();
        for (Client c : cliente) {
            sb.append(c).append("\n");
        }
        return sb;
    }

    public void remover(Long elemento) {
        findById(elemento);

        clientRepositories.deleteById(elemento);
    }

    public Client upgrade(Client client) {
        findById(client.getId());
        return clientRepositories.save(client);
    }

    @Transactional(readOnly = true)
    public Client searchCustomerLogin(String login, String password) {
        return clientRepositories.findByLoginAndPassword(login, password);
    }
}
