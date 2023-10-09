package com.genival.home.broker.controller;

import com.genival.home.broker.dto.user.UserDTO;
import com.genival.home.broker.dto.user.UserMinDTO;
import com.genival.home.broker.entities.User;
import com.genival.home.broker.requests.UserPostRequestBody;
import com.genival.home.broker.services.UserServices;
import com.genival.home.broker.util.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("user")
@Log4j2
@RequiredArgsConstructor
public class UserController {
    private final UserServices userServices;
    private final DateUtil dateUtil;

    @PostMapping()
    public ResponseEntity<UserMinDTO> save(@RequestBody @Valid UserPostRequestBody body){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + "user post");
        return new ResponseEntity<> (userServices.save(body), HttpStatus.CREATED);
    }
}
