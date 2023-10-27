package com.genival.home.broker.controller;

import com.genival.home.broker.entities.Account;
import com.genival.home.broker.entities.Execution;
import com.genival.home.broker.entities.Ordem;
import com.genival.home.broker.services.ExecutionServices;
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
@RequestMapping("execution")
@RequiredArgsConstructor
public class ExecutionController {
    private DateUtil dateUtil;
    private ExecutionServices executionServices;

    private Log log;

    @PostMapping()
    public ResponseEntity<Boolean> save(@RequestBody @Valid Execution element) {
        
        return new ResponseEntity<>(executionServices.save(element), HttpStatus.CREATED);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Execution> findById(@PathVariable long id) {
        
        return new ResponseEntity<>(executionServices.findById(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Boolean> remover(@PathVariable long id) {
        
        return new ResponseEntity<>(executionServices.remove(id), HttpStatus.OK);
    }

    @GetMapping(path = "all")
    public ResponseEntity<List<Execution>> findById() {
        
        return new ResponseEntity<>(executionServices.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "empty")
    public ResponseEntity<Boolean> isEmpty() {
        
        return new ResponseEntity<>(executionServices.isEmpty(), HttpStatus.OK);
    }

    @PostMapping(path = "new")
    public ResponseEntity<Boolean> newExecution(@RequestBody @Valid Account buyer, Account seller, Ordem ordem, int salesQuantity) {
        
        return new ResponseEntity<>(executionServices.newExecution(buyer, seller, ordem, salesQuantity), HttpStatus.CREATED);
    }

}
