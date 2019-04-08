package edu.uclm.esi.games.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uclm.esi.games.model.Match;

@Repository
public interface MatchRepository extends CrudRepository<Match, String>{

}
