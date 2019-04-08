package edu.uclm.esi.games.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uclm.esi.games.model.Board;

@Repository
public interface BoardRepositoryCustom extends CrudRepository<Board, String>{
	
}
