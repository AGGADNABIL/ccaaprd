package org.capvalue.recrute.metier;

import org.capvalue.recrute.domaine.Ville;

import java.util.List;


public interface IVilleMetier {
	
	public void deleteVille(Long id) ;

	public List<Ville> findAllVilles() ;

	public Ville findOneVille(Long id);

	public Ville saveVille(Ville ville);

	public Ville saveAndFlush(Ville ville) ;

}
