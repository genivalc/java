package com.genival.dslist.repositories;

import com.genival.dslist.entities.Game;
import com.genival.dslist.entities.GameList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameListRepository extends JpaRepository<GameList, Long> {
}
