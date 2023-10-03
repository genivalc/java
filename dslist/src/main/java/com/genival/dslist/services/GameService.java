package com.genival.dslist.services;

import com.genival.dslist.dto.GameMinDTO;
import com.genival.dslist.entities.Game;
import com.genival.dslist.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public  List<GameMinDTO> findAll() {
        List<Game> result = gameRepository.findAll();
        return result.stream().map(games -> new GameMinDTO(games)).collect(Collectors.toList());
    }
}
