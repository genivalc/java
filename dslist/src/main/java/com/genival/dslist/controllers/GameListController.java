package com.genival.dslist.controllers;

import com.genival.dslist.dto.GameDTO;
import com.genival.dslist.dto.GameListDTO;
import com.genival.dslist.dto.GameMinDTO;
import com.genival.dslist.services.GameListService;
import com.genival.dslist.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/list")
public class GameListController {

    @Autowired
    private GameListService gameListService;

    @GetMapping
    public List<GameListDTO> findAll() {
        List<GameListDTO> result = gameListService.findAll();
        return result;
    }
}
