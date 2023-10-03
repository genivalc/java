package com.genival.pocredesocial.controller;

import com.genival.pocredesocial.domain.User;
import com.genival.pocredesocial.requests.UserPostRequest;
import com.genival.pocredesocial.service.UserService;
import com.genival.pocredesocial.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("user")
@Log4j2
@RequiredArgsConstructor
public class UserController {
    private final DateUtil dateUtil;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> lis(){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return  new ResponseEntity<>("oi", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid UserPostRequest userPostRequest) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return  new ResponseEntity<>(userService.save(userPostRequest), HttpStatus.CREATED);
    }
}
