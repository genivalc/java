package com.genival.home.broker.controller;

import com.genival.home.broker.entities.Account;
import com.genival.home.broker.entities.Client;
import com.genival.home.broker.services.AccountServices;
import com.genival.home.broker.utils.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("account")
@Log4j2
@RequiredArgsConstructor
public class AccountController {

    private final DateUtil dateUtil;
    private final AccountServices accountServices;

    @PostMapping(path = "initial/credit")
    public ResponseEntity<Void> initialAccountCredit(@RequestBody @Valid Client client, Account newAccount) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Account Initial Account Credit Post");
        accountServices.initialAccountCredit(client, newAccount);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Boolean> upgrade(@RequestBody @Valid Account account) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Account upgrade Post");
        return new ResponseEntity<>(accountServices.upgrade(account), HttpStatus.CREATED);
    }

    @PostMapping(path = "month/payment")
    public ResponseEntity<Void> monthlyPayment(@RequestBody @Valid Client client) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Account month payment Post");
        accountServices.monthlyPayment(client);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GetMapping(path = "search/accounts")
    public ResponseEntity<Account> searchAccounts(@RequestBody @Valid Client client) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Account search Get");
        return new ResponseEntity<>(accountServices.searchAccounts(client), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Account> findById(@PathVariable long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Account find By Id Get");
        return new ResponseEntity<>(accountServices.findById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Account>> findAll() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Account find all Get");
        return new ResponseEntity<>(accountServices.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "list/accounts")
    public ResponseEntity<StringBuilder> listAccounts() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Account list accounts Get");
        return new ResponseEntity<>(accountServices.listAccounts(), HttpStatus.OK);
    }

    @GetMapping(path = "customer/search/{id}")
    public ResponseEntity<Client> customerSearchbyAccountId(@PathVariable long id, @RequestBody @Valid Client client) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Account list accounts Get");
        return new ResponseEntity<>(accountServices.customersearchbyAccountId(id, client), HttpStatus.OK);
    }

    @GetMapping(path = "validate/{id}")
    public ResponseEntity<Boolean> validateId(@PathVariable long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Account validate Id Get");
        return new ResponseEntity<>(accountServices.validateId(id), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Boolean> remover(@PathVariable long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Account remove Delete");
        return new ResponseEntity<>(accountServices.remover(id), HttpStatus.OK);
    }

    @PostMapping(path = "deposit")
    public ResponseEntity<Boolean> deposit(@RequestBody @Valid Account destiny, BigDecimal value) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Account deposit Post");
        return new ResponseEntity<>(accountServices.deposit(destiny, value), HttpStatus.OK);
    }

    @PostMapping(path = "with/draw")
    public ResponseEntity<Boolean> withdraw(@RequestBody @Valid Account origin, BigDecimal value) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Account withdraw Post");
        accountServices.withdraw(origin, value);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(path = "payment")
    public ResponseEntity<Boolean> payment(@RequestBody @Valid Account origin, BigDecimal valor) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Account payment Post");
        return new ResponseEntity<>(accountServices.payment(origin, valor), HttpStatus.CREATED);
    }

}
