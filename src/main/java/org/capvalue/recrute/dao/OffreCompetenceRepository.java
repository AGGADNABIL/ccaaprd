package org.capvalue.recrute.dao;

import java.util.List;

import org.capvalue.recrute.domaine.OffreCompetence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OffreCompetenceRepository extends JpaRepository<OffreCompetence, Long> {

	@Query("SELECT o FROM OffreCompetence o WHERE o.competence IS NOT NULL GROUP BY o.competence.codeCompetence")
	public List<OffreCompetence> offreCompetenceNotDuplicate();
	
	@Query("SELECT o FROM OffreCompetence o WHERE o.offre.codeOffre=:id")
	List<OffreCompetence> findAllByIdOffre(@Param("id") Long id);
	
}
