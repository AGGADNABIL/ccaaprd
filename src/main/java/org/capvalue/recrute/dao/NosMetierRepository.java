package org.capvalue.recrute.dao;

import java.util.List;

import org.capvalue.recrute.domaine.NosInfos;
import org.capvalue.recrute.domaine.NosMetier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NosMetierRepository extends JpaRepository<NosMetier, Long> {

	@Query("select f from NosMetier f order by f.priorite")
	public List<NosMetier> findAllMet();
}
