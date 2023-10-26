package com.genival.home.broker.controller;

import com.genival.home.broker.entities.Active;
import com.genival.home.broker.entities.ActiveAccount;
import com.genival.home.broker.services.ActiveServices;
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
@RequestMapping("active")
@RequiredArgsConstructor
public class ActiveController {

    private ActiveServices activeServices;


    @PostMapping()
    public ResponseEntity<Active> save(@RequestBody @Valid Active active) {
        
        return new ResponseEntity<>(activeServices.save(active), HttpStatus.CREATED);
    }

    @GetMapping(path = "isempty")
    public ResponseEntity<Boolean> isEmpty() {
        
        return new ResponseEntity<>(activeServices.isEmpty(), HttpStatus.OK);
    }

    @GetMapping(path = "all")
    public ResponseEntity<List<Active>> findAll() {
        
        return new ResponseEntity<>(activeServices.findAll(), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<ActiveAccount> remover(@PathVariable long id) {
        
        activeServices.remover(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Active> changesPrice(@RequestBody @Valid String ticker, BigDecimal valor) {
        
        activeServices.changesPrice(ticker, valor);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping(path = "busca")
    public ResponseEntity<Active> changesPrice(@RequestBody @Valid String ticker) {
        
        return new ResponseEntity<>(activeServices.busca(ticker), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Active> upgrade(@RequestBody @Valid Active active) {
        
        return new ResponseEntity<>(activeServices.upgrade(active), HttpStatus.OK);
    }

    @GetMapping(path = "hasactive")
    public ResponseEntity<Boolean> changesPrice(@RequestBody @Valid Active active) {
        
        activeServices.hasActive(active);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
