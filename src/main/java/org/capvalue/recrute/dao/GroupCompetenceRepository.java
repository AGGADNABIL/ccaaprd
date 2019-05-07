package org.capvalue.recrute.dao;

import org.capvalue.recrute.domaine.GroupCompetence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Aimad MAJDOU on July 14, 2016
 */
@Repository
@Transactional
public interface GroupCompetenceRepository extends JpaRepository<GroupCompetence, Long> {

	//public Long findLastId();
}
