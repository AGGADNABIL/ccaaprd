package org.capvalue.recrute.dao;

import org.capvalue.recrute.domaine.Candidat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import javax.transaction.Transactional;

@Transactional
public interface CandidatRepository extends UserBaseRepository<Candidat>  {
	@Query("select c from Candidat c where c.codeUser =:x")
	Candidat getCandidatById( @Param("x") Long codeUser);
	
	@Query("select c from Candidat c where c.username =:x")
	Candidat getCandidatByUsername( @Param("x") String username);
	
	@Query("SELECT c FROM Candidat c where c.notification=false")
	List<Candidat> getAllByReceiver();

}
