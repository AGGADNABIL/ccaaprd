package org.capvalue.recrute.metier;

import java.util.List;

import org.capvalue.recrute.domaine.NosMetier;

public interface NosMetierMetier {
	
	public void delete(Long id);

	public List<NosMetier> findAll();

	public NosMetier findOne(Long id) ;

	public NosMetier save(NosMetier nosMetier) ;

	public NosMetier saveAndFlush(NosMetier nosMetier);
	
	public List<NosMetier> findAllMetier();
	

}
