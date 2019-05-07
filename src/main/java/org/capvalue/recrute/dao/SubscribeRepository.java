package org.capvalue.recrute.dao;

import java.util.List;

import org.capvalue.recrute.domaine.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscribeRepository extends JpaRepository<Subscribe, String> {
	@Query("select s from Subscribe s where s.email =:x")
	Subscribe getSubscribeByUsername( @Param("x") String username);
	
	@Query("SELECT s FROM Subscribe s where s.desinscrire=true")
	List<Subscribe> findAllNotDisabled();
	

}
