package com.genival.home.broker.controller;

import com.genival.home.broker.dto.user.UserMinDTO;
import com.genival.home.broker.requests.UserPostRequestBody;
import com.genival.home.broker.services.UserServices;
import com.genival.home.broker.utils.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("user")
@Log4j2
@RequiredArgsConstructor
public class UserController {
    private final UserServices userServices;
    private final DateUtil dateUtil;

    @GetMapping(path = "admin/{id}")
    public ResponseEntity<Page<UserMinDTO>> listAll(@PathVariable long id, Pageable pageable){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now())+ " GetlistAllPageable");
        return new ResponseEntity<>(userServices.listAll(id, pageable), HttpStatus.OK);
    }

    @GetMapping(path = "admin/all/{id}")
    public ResponseEntity<List<UserMinDTO>> listAllNonPageable(@PathVariable long id){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now())+ " Get listAllNonPageable");
        return new ResponseEntity<>(userServices.listAllNonPageable(id), HttpStatus.OK);
    }

    @GetMapping(path = "admin/find/{id}")
    public ResponseEntity<List<UserMinDTO>> findById(@PathVariable long id){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now())+ " Get findById");
        return new ResponseEntity<>(userServices.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserMinDTO> save(@RequestBody @Valid UserPostRequestBody body){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + "user post");
        return new ResponseEntity<> (userServices.save(body), HttpStatus.CREATED);
    }
}
