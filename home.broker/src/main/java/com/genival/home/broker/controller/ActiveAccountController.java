package com.genival.home.broker.controller;

import com.genival.home.broker.entities.ActiveAccount;
import com.genival.home.broker.entities.Client;
import com.genival.home.broker.services.ActiveAccountServices;
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
@RequestMapping("active/account")
@RequiredArgsConstructor
public class ActiveAccountController {
 
    private ActiveAccountServices activeAccountServices;


    @PostMapping()
    public ResponseEntity<Boolean> addActiveAccount(@RequestBody @Valid ActiveAccount activeAccount) {
       
        return new ResponseEntity<>(activeAccountServices.addActiveAccount(activeAccount), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Boolean> updateActiveAccount(@RequestBody @Valid ActiveAccount activeAccount) {
       
        return new ResponseEntity<>(activeAccountServices.updateActiveAccount(activeAccount), HttpStatus.OK);
    }

    @PostMapping(path = "pay/dividends")
    public ResponseEntity<Void> payDividends(@RequestBody @Valid String ticker, BigDecimal dividendPerShare, Client client) {
       
        activeAccountServices.payDividends(ticker, dividendPerShare, client);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }


    @GetMapping(path = "isfull")
    public ResponseEntity<Boolean> isFull() {
        
        return new ResponseEntity<>(activeAccountServices.isFull(), HttpStatus.OK);
    }

    @GetMapping(path = "isempty")
    public ResponseEntity<Boolean> empty() {
        
        return new ResponseEntity<>(activeAccountServices.isEmpty(), HttpStatus.OK);
    }

    @GetMapping(path = "search/all")
    public ResponseEntity<List<ActiveAccount>> searchAlActiveAccount() {
       
        return new ResponseEntity<>(activeAccountServices.searchAlActiveAccount(), HttpStatus.OK);
    }

    @GetMapping(path = "all")
    public ResponseEntity<List<ActiveAccount>> getAllActiveAccount(@RequestBody @Valid Client client) {
       
        return new ResponseEntity<>(activeAccountServices.getAllActiveAccount(client), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ActiveAccount> getAtivoContaById(@PathVariable long id) {
     
        return new ResponseEntity<>(activeAccountServices.getAtivoContaById(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<ActiveAccount> deleteAtivoConta(@PathVariable long id) {
      
        activeAccountServices.deleteAtivoConta(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping(path = "show/all")
    public ResponseEntity<StringBuilder> showAllAtivoContas(@RequestBody @Valid Client client) {
      
        return new ResponseEntity<>(activeAccountServices.showAllAtivoContas(client), HttpStatus.OK);
    }

    @GetMapping(path = "ticker/{id}")
    public ResponseEntity<String> getTickerById(@PathVariable long id) {
       
        return new ResponseEntity<>(activeAccountServices.getTickerById(id), HttpStatus.OK);
    }

    @GetMapping(path = "count/assets")
    public ResponseEntity<Integer> countAssets() {
       
        return new ResponseEntity<>(activeAccountServices.countAssets(), HttpStatus.OK);
    }
}