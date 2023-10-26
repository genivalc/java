package com.genival.home.broker.controller;

import com.genival.home.broker.entities.Client;
import com.genival.home.broker.entities.Ordem;
import com.genival.home.broker.services.OrdemServices;
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
@RequestMapping("ordem")
@RequiredArgsConstructor
public class OrdemController {

    private OrdemServices ordemServices;

   

    @PostMapping()
    public ResponseEntity<Boolean> save(@RequestBody @Valid Ordem ordem) {
        
        return new ResponseEntity<>(ordemServices.save(ordem), HttpStatus.CREATED);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Ordem> findById(@PathVariable long id) {
        
        return new ResponseEntity<>(ordemServices.findById(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id) {
        
        return new ResponseEntity<>(ordemServices.delete(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Boolean> upgrade(@RequestBody @Valid Ordem ordem) {
        
        return new ResponseEntity<>(ordemServices.upgrade(ordem), HttpStatus.OK);
    }

    @GetMapping(path = "all")
    public ResponseEntity<List<Ordem>> searchAll(@RequestBody @Valid Client client) {
       
        return new ResponseEntity<>(ordemServices.searchAll(client), HttpStatus.OK);
    }

    @GetMapping(path = "search/{id}")
    public ResponseEntity<Ordem> searchOrderByID(@RequestBody @Valid Long idOrdem, Client client) {
       
        return new ResponseEntity<>(ordemServices.searchOrderByID(idOrdem, client), HttpStatus.OK);
    }

    @GetMapping(path = "purchase/orders")
    public ResponseEntity<String> showPurchaseOrders(@RequestBody @Valid Client client) {
       
        return new ResponseEntity<>(ordemServices.showPurchaseOrders(client), HttpStatus.OK);
    }

    @GetMapping(path = "sales/orders")
    public ResponseEntity<StringBuilder> showSalesOrders(@RequestBody @Valid Client client) {
        
        return new ResponseEntity<>(ordemServices.showSalesOrders(client), HttpStatus.OK);
    }

    @GetMapping(path = "search/ticker/{id}")
    public ResponseEntity<String> searchTickerById(@PathVariable long id, @RequestBody @Valid Client client) {
        
        return new ResponseEntity<>(ordemServices.searchTickerById(id, client), HttpStatus.OK);
    }

    @PostMapping(path = "execute")
    public ResponseEntity<StringBuilder> save(@RequestBody @Valid Client client) {
        
        return new ResponseEntity<>(ordemServices.executeOrders(client), HttpStatus.CREATED);
    }
}
