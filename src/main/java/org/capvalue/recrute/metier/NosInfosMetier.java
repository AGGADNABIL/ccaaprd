package org.capvalue.recrute.metier;

import java.util.List;

import org.capvalue.recrute.domaine.NosInfos;

public interface NosInfosMetier {
	
	public void delete(Long id);

	public List<NosInfos> findAll();

	public NosInfos findOne(Long id) ;

	public NosInfos save(NosInfos NosInfos) ;

	public NosInfos saveAndFlush(NosInfos NosInfos);
	
	public List<NosInfos> findAllOrdered();
	

}
