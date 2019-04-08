package edu.uclm.esi.games.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uclm.esi.games.model.Player;

@Repository
public interface PlayerRepository extends CrudRepository<Player, String>{
	Player findByUserNameAndPwd(String userName, String pwd);

}
