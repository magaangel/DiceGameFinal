package com.dicegamefinal.repository;

import com.dicegamefinal.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IplayerRepository extends CrudRepository<Player, Long> {

    public Player findByEmail(String email);

    public Player findByNickName(String nickName);

}
