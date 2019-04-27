package edu.uclm.esi.games.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uclm.esi.games.model.Word;

@Repository
public interface WordRepository extends CrudRepository<Word, String>{
	
}
