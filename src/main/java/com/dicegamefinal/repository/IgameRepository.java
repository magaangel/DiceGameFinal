package com.dicegamefinal.repository;

import com.dicegamefinal.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IgameRepository extends CrudRepository<Game, Long> {
}
