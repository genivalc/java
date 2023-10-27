package com.genival.home.broker.controller;

import com.genival.home.broker.doman.auth.AuthenticationDTO;
import com.genival.home.broker.doman.auth.LoginResponseDTO;
import com.genival.home.broker.doman.auth.RegisterDTO;
import com.genival.home.broker.entities.Client;
import com.genival.home.broker.infra.TokenService;
import com.genival.home.broker.services.ClientServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private ClientServices clientServices;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;


    @PostMapping(path = "/register")
    public ResponseEntity<Boolean> register(@RequestBody @Valid RegisterDTO data) {
        return new ResponseEntity<>(clientServices.register(data), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Client) auth.getPrincipal());
        return new ResponseEntity<>(new LoginResponseDTO(token), HttpStatus.OK);
    }
}
