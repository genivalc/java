package com.genival.home.broker.controller;

import com.genival.home.broker.entities.Client;
import com.genival.home.broker.services.ClientServices;
import com.genival.home.broker.utils.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("client")
@RequiredArgsConstructor
public class ClientController {

    @Autowired
    private ClientServices clientServices;




    @GetMapping(path = "{id}")
    public ResponseEntity<Client> findById(@PathVariable long id) {
        
        return new ResponseEntity<>(clientServices.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "empty")
    public ResponseEntity<Boolean> isEmpty() {
        
        return new ResponseEntity<>(clientServices.isEmpty(), HttpStatus.OK);
    }

    @GetMapping(path = "all")
    public ResponseEntity<List<Client>> findAll() {
        
        return new ResponseEntity<>(clientServices.findAll(), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> remover(@PathVariable long id) {
                clientServices.remover(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Client> upgrade(Client client) {
                return new ResponseEntity<>(clientServices.upgrade(client), HttpStatus.OK);
    }


}
