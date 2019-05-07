package org.capvalue.recrute.dao;

import java.util.List;

import org.capvalue.recrute.domaine.NosInfos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NosInfosRepository extends JpaRepository<NosInfos, Long> {
  
	@Query("select f from NosInfos f order by f.priorite")
	public List<NosInfos> findAllOrdered();
}
