package com.genival.home.broker.controller;

import com.genival.home.broker.entities.Client;
import com.genival.home.broker.services.ClientServices;
import com.genival.home.broker.utils.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("client")
@RequiredArgsConstructor
public class ClientController {
    private DateUtil dateUtil;
    private ClientServices clientServices;
    private Log log;

    @PostMapping()
    public ResponseEntity<Boolean> save(@RequestBody @Valid Client client) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " client save Post");
        return new ResponseEntity<>(clientServices.save(client), HttpStatus.CREATED);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Client> findById(@PathVariable long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " client findById GET");
        return new ResponseEntity<>(clientServices.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "empty")
    public ResponseEntity<Boolean> isEmpty() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " client isEmpty GET");
        return new ResponseEntity<>(clientServices.isEmpty(), HttpStatus.OK);
    }

    @GetMapping(path = "all")
    public ResponseEntity<List<Client>> findAll() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " client findAll GET");
        return new ResponseEntity<>(clientServices.findAll(), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> remover(@PathVariable long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + "client  Remover Delete");
        clientServices.remover(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Client> upgrade(Client client) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + "client  upgrade Put");
        return new ResponseEntity<>(clientServices.upgrade(client), HttpStatus.OK);
    }

    @GetMapping(path = "login")
    public ResponseEntity<Client> searchCustomerLogin(@RequestBody @Valid String login, String password) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + "client  login GET");
        return new ResponseEntity<>(clientServices.searchCustomerLogin(login, password), HttpStatus.OK);
    }
}
