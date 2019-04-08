package edu.uclm.esi.games.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uclm.esi.games.model.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, String>{
	public Token findByUserNameAndId(String userName, int idToken);
}
