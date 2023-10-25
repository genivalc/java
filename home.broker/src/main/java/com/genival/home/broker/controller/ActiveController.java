package com.genival.home.broker.controller;

import com.genival.home.broker.entities.Active;
import com.genival.home.broker.entities.ActiveAccount;
import com.genival.home.broker.services.ActiveServices;
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
@RequestMapping("active")
@Log4j2
@RequiredArgsConstructor
public class ActiveController {
    private final DateUtil dateUtil;
    private final ActiveServices activeServices;

    @PostMapping()
    public ResponseEntity<Active> save(@RequestBody @Valid Active active) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Active save POST");
        return new ResponseEntity<>(activeServices.save(active), HttpStatus.CREATED);
    }

    @GetMapping(path = "isempty")
    public ResponseEntity<Boolean> isEmpty() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Active isEmpty GET");
        return new ResponseEntity<>(activeServices.isEmpty(), HttpStatus.OK);
    }

    @GetMapping(path = "all")
    public ResponseEntity<List<Active>> findAll() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Active find All GET");
        return new ResponseEntity<>(activeServices.findAll(), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<ActiveAccount> remover(@PathVariable long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Active remover  Delete");
        activeServices.remover(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Active> changesPrice(@RequestBody @Valid String ticker, BigDecimal valor) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Active changesPrice GET");
        activeServices.changesPrice(ticker, valor);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping(path = "busca")
    public ResponseEntity<Active> changesPrice(@RequestBody @Valid String ticker) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Active busca GET");
        return new ResponseEntity<>(activeServices.busca(ticker), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Active> upgrade(@RequestBody @Valid Active active) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Active update Put");
        return new ResponseEntity<>(activeServices.upgrade(active), HttpStatus.OK);
    }

    @GetMapping(path = "hasactive")
    public ResponseEntity<Boolean> changesPrice(@RequestBody @Valid Active active) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Active hasactive GET");
        activeServices.hasActive(active);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
