package com.genival.home.broker.controller;

import com.genival.home.broker.entities.Account;
import com.genival.home.broker.entities.AccountMovement;
import com.genival.home.broker.services.AccountMovementServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("account/movement")
@RequiredArgsConstructor
public class AccountMovementController {
    private AccountMovementServices accountMovementServices;


    @GetMapping(path = "empty")
    public ResponseEntity<Boolean> isEmpty() {

        return new ResponseEntity<>(accountMovementServices.isEmpty(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Boolean> save(@RequestBody @Valid AccountMovement body) {

        return new ResponseEntity<>(accountMovementServices.save(body), HttpStatus.CREATED);
    }

    @PostMapping(path = "created/movement")
    public ResponseEntity<Void> saveMovement(@RequestBody @Valid Account debit, Account credit, BigDecimal value, String descriptionOfAccountMovement) {

        accountMovementServices.saveMovement(debit, credit, value, descriptionOfAccountMovement);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<AccountMovement> findById(@PathVariable long id) {

        return new ResponseEntity<>(accountMovementServices.findById(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Boolean> remover(@PathVariable long id) {

        return new ResponseEntity<>(accountMovementServices.remover(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<AccountMovement>> findByAll() {

        return new ResponseEntity<>(accountMovementServices.findByAll(), HttpStatus.OK);
    }
}
