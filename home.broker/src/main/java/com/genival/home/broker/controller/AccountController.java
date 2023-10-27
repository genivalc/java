package com.genival.home.broker.controller;

import com.genival.home.broker.entities.Account;
import com.genival.home.broker.entities.Client;
import com.genival.home.broker.services.AccountServices;
import com.genival.home.broker.utils.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("account")
@RequiredArgsConstructor
public class AccountController {
    private AccountServices accountServices;
  


    @PostMapping(path = "initial/credit")
    public ResponseEntity<Void> initialAccountCredit(@RequestBody @Valid Client client, Account newAccount) {
 
        accountServices.initialAccountCredit(client, newAccount);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Boolean> upgrade(@RequestBody @Valid Account account) {
        
        return new ResponseEntity<>(accountServices.upgrade(account), HttpStatus.CREATED);
    }

    @PostMapping(path = "month/payment")
    public ResponseEntity<Void> monthlyPayment(@RequestBody @Valid Client client) {
   
        accountServices.monthlyPayment(client);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GetMapping(path = "search/accounts")
    public ResponseEntity<Account> searchAccounts(@RequestBody @Valid Client client) {
        
        return new ResponseEntity<>(accountServices.searchAccounts(client), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Account> findById(@PathVariable long id) {
      
        return new ResponseEntity<>(accountServices.findById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Account>> findAll() {
        
        return new ResponseEntity<>(accountServices.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "list/accounts")
    public ResponseEntity<StringBuilder> listAccounts() {
   
        return new ResponseEntity<>(accountServices.listAccounts(), HttpStatus.OK);
    }

    @GetMapping(path = "customer/search/{id}")
    public ResponseEntity<Client> customerSearchbyAccountId(@PathVariable long id, @RequestBody @Valid Client client) {
      
        return new ResponseEntity<>(accountServices.customersearchbyAccountId(id, client), HttpStatus.OK);
    }

    @GetMapping(path = "validate/{id}")
    public ResponseEntity<Boolean> validateId(@PathVariable long id) {
     
        return new ResponseEntity<>(accountServices.validateId(id), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Boolean> remover(@PathVariable long id) {
        
        return new ResponseEntity<>(accountServices.remover(id), HttpStatus.OK);
    }

    @PostMapping(path = "deposit")
    public ResponseEntity<Boolean> deposit(@RequestBody @Valid Account destiny, BigDecimal value) {
        
        return new ResponseEntity<>(accountServices.deposit(destiny, value), HttpStatus.OK);
    }

    @PostMapping(path = "with/draw")
    public ResponseEntity<Boolean> withdraw(@RequestBody @Valid Account origin, BigDecimal value) {
        
        accountServices.withdraw(origin, value);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(path = "payment")
    public ResponseEntity<Boolean> payment(@RequestBody @Valid Account origin, BigDecimal valor) {
        
        return new ResponseEntity<>(accountServices.payment(origin, valor), HttpStatus.CREATED);
    }

}
