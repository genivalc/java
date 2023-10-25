package com.genival.home.broker.controller;

import com.genival.home.broker.entities.Client;
import com.genival.home.broker.entities.Ordem;
import com.genival.home.broker.services.OrdemServices;
import com.genival.home.broker.utils.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("ordem")
@Log4j2
@RequiredArgsConstructor
public class OrdemController {
    private final DateUtil dateUtil;
    private final OrdemServices ordemServices;

    @PostMapping()
    public ResponseEntity<Boolean> save(@RequestBody @Valid Ordem ordem) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Ordem save Post");
        return new ResponseEntity<>(ordemServices.save(ordem), HttpStatus.CREATED);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Ordem> findById(@PathVariable long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Ordem findById Get");
        return new ResponseEntity<>(ordemServices.findById(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Ordem remove Delete");
        return new ResponseEntity<>(ordemServices.delete(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Boolean> upgrade(@RequestBody @Valid Ordem ordem) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Ordem upgrade PUT");
        return new ResponseEntity<>(ordemServices.upgrade(ordem), HttpStatus.OK);
    }

    @GetMapping(path = "all")
    public ResponseEntity<List<Ordem>> searchAll(@RequestBody @Valid Client client) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Ordem search All Get");
        return new ResponseEntity<>(ordemServices.searchAll(client), HttpStatus.OK);
    }

    @GetMapping(path = "search/{id}")
    public ResponseEntity<Ordem> searchOrderByID(@RequestBody @Valid Long idOrdem, Client client) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Ordem search Order By ID Get");
        return new ResponseEntity<>(ordemServices.searchOrderByID(idOrdem, client), HttpStatus.OK);
    }

    @GetMapping(path = "purchase/orders")
    public ResponseEntity<String> showPurchaseOrders(@RequestBody @Valid Client client) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Ordem show Purchase Orders Get");
        return new ResponseEntity<>(ordemServices.showPurchaseOrders(client), HttpStatus.OK);
    }

    @GetMapping(path = "sales/orders")
    public ResponseEntity<StringBuilder> showSalesOrders(@RequestBody @Valid Client client) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Ordem Show Sales Orders Get");
        return new ResponseEntity<>(ordemServices.showSalesOrders(client), HttpStatus.OK);
    }

    @GetMapping(path = "search/ticker/{id}")
    public ResponseEntity<String> searchTickerById(@PathVariable long id, @RequestBody @Valid Client client) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Ordem search Ticker By Id Get");
        return new ResponseEntity<>(ordemServices.searchTickerById(id, client), HttpStatus.OK);
    }

    @PostMapping(path = "execute")
    public ResponseEntity<StringBuilder> save(@RequestBody @Valid Client client) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Ordem execute Orders Post");
        return new ResponseEntity<>(ordemServices.executeOrders(client), HttpStatus.CREATED);
    }
}
