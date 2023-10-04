package com.genival.dslist.services;

import com.genival.dslist.dto.GameListDTO;
import com.genival.dslist.entities.GameList;
import com.genival.dslist.projection.GameMinProjection;
import com.genival.dslist.repositories.GameListRepository;
import com.genival.dslist.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameListService {

    @Autowired
    private GameListRepository gameListRepository;

    @Autowired
    private GameRepository gameRepository;


    @Transactional(readOnly = true)
    public  List<GameListDTO> findAll() {
        List<GameList> result = gameListRepository.findAll();
        return result.stream().map(games -> new GameListDTO(games)).collect(Collectors.toList());
    }

    @Transactional
    public void move(Long listId, int sourceIndex, int destinationIndex){
        List<GameMinProjection> list = gameRepository.searchByList(listId);
        GameMinProjection obj = removeItemFromList(list, sourceIndex);
        addItemAtPosition(list, obj, destinationIndex);
        updateBelongingPositions(list, listId, sourceIndex, destinationIndex);
    }

    private GameMinProjection removeItemFromList(List<GameMinProjection> list, int index) {
        return list.remove(index);
    }

    private void addItemAtPosition(List<GameMinProjection> list, GameMinProjection obj, int index) {
        list.add(index, obj);
    }

    private void updateBelongingPositions(List<GameMinProjection> list, Long listId, int sourceIndex, int destinationIndex) {
        int min = Math.min(sourceIndex, destinationIndex);
        int max = Math.max(sourceIndex, destinationIndex);

        for (int i = min; i <= max; i++) {
            gameListRepository.updateBelongingPosition(listId, list.get(i).getId(), i);
        }
    }
}
