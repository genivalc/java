package com.genival.dslist.services;

import com.genival.dslist.dto.GameListDTO;
import com.genival.dslist.entities.GameList;
import com.genival.dslist.repositories.GameListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameListService {

    @Autowired
    private GameListRepository gameListRepository;


    @Transactional(readOnly = true)
    public  List<GameListDTO> findAll() {
        List<GameList> result = gameListRepository.findAll();
        return result.stream().map(games -> new GameListDTO(games)).collect(Collectors.toList());
    }
}
