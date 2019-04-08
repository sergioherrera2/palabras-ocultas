package edu.uclm.esi.games.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.uclm.esi.games.model.AbstractPlayer;

@Repository
public interface AbstractPlayerRepository extends CrudRepository<AbstractPlayer, String>{
	AbstractPlayer findByUserName(String userName);
		
	@Transactional
	@Modifying
	@Query(value= "update abstractplayer set pwd=:pwd where user_name=:userName")
	AbstractPlayer updatePwd(@Param("userName") String userName, @Param("pwd") String pwd);
	
	@Transactional
	@Modifying
	@Query("delete from abstractplayer")
	void deleteAll();
	
	@Transactional
	@Modifying
	@Query(value= "update abstractplayer set userName=?2 where userName=?1")
	void changeUserName(String oldName, String newName);

}
