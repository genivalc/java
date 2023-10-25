package com.genival.home.broker.controller;

import com.genival.home.broker.entities.Account;
import com.genival.home.broker.entities.AccountMovement;
import com.genival.home.broker.services.AccountMovementServices;
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
@RequestMapping("account/movement")
@Log4j2
@RequiredArgsConstructor
public class AccountMovementController {
    private final DateUtil dateUtil;
    private final AccountMovementServices accountMovementServices;

    @GetMapping(path = "empty")
    public ResponseEntity<Boolean> isEmpty() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Account Movement isEmpty");
        return new ResponseEntity<>(accountMovementServices.isEmpty(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Boolean> save(@RequestBody @Valid AccountMovement body) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Account Movement Post(Save)");
        return new ResponseEntity<>(accountMovementServices.save(body), HttpStatus.CREATED);
    }

    @PostMapping(path = "created/movement")
    public ResponseEntity<Void> saveMovement(@RequestBody @Valid Account debit, Account credit, BigDecimal value, String descriptionOfAccountMovement) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Account Movement Post(Save Movement)");
        accountMovementServices.saveMovement(debit, credit, value, descriptionOfAccountMovement);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<AccountMovement> findById(@PathVariable long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Account Movement isEmpty");
        return new ResponseEntity<>(accountMovementServices.findById(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Boolean> remover(@PathVariable long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Remover Account Movement");
        return new ResponseEntity<>(accountMovementServices.remover(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<AccountMovement>> findByAll() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " findByAll");
        return new ResponseEntity<>(accountMovementServices.findByAll(), HttpStatus.OK);
    }
}
