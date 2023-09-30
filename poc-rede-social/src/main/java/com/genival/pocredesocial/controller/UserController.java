package com.genival.pocredesocial.controller;

import com.genival.pocredesocial.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("user")
@Log4j2
@RequiredArgsConstructor
public class UserController {
    private final DateUtil dateUtil;
    @GetMapping
    public ResponseEntity<String> lis(){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return  new ResponseEntity<>("oi", HttpStatus.OK);
    }
}
