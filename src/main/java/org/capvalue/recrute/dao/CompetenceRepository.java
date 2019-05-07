package org.capvalue.recrute.dao;

import java.util.List;

import org.capvalue.recrute.domaine.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompetenceRepository extends JpaRepository<Competence, Long> {
	
    @Query("SELECT c FROM OffreCompetence oc join oc.competence c")
	public List<Competence> findCompetenceInOC();
}
