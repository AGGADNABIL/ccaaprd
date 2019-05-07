package org.capvalue.recrute.dao;

import org.capvalue.recrute.domaine.Postuler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PostulerRepository extends JpaRepository<Postuler, Long> {
	@Transactional
	@Modifying
	@Query("delete from Postuler p WHERE p.offre.codeOffre =:x")
	public void deleteLitPustulerByOffre(@Param("x") Long codeOffre);
	@Transactional
	@Modifying
	@Query("delete from Postuler p WHERE p.candidat.username =:x")
	public void deleteListPostulerByCandidat(@Param("x") String username);
	
	@Query("select count(distinct p.candidat.username) from Postuler p where p.offre.codeOffre =:x")
	public int getNumberPosulerByOffre(@Param("x") Long codeOffre);
	
	@Query("select distinct p.candidat.username from Postuler p where p.offre.codeOffre =:x ")
	public List<String> getCandidatPosulerByOffre(@Param("x") Long codeOffre);
	@Query("select p from Postuler p where  p.candidat.username =:x and  p.offre.codeOffre =:y ORDER BY p.codePostuler DESC")
	public List<Postuler> getPosulerByOffre(@Param("x") String username,@Param("y") Long codeOffre);
	
	@Query("select p from Postuler p where  p.candidat.username =:x ")
	public List<Postuler> getPosulerByCandidat(@Param("x") String username);
	
	@Query("select  p from Postuler p where  p.candidat.username =:x ")
	public List<Postuler> getPosulerOneByCandidat(@Param("x") String username);
	
	@Query("select p from Postuler p where  p.offre.codeOffre =:x  ")
	public List<Postuler> getOffre(@Param("x") Long codeOffre);
	
	@Query("select max(p.offre.codeOffre), p from Postuler p where p.offre.codeOffre =:x  GROUP BY p.candidat.username ")
	public List<Postuler> getOffresByCode(@Param("x") Long codeOffre);
	
}
