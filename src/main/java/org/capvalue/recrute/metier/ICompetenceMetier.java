package org.capvalue.recrute.metier;

import org.capvalue.recrute.domaine.Competence;

import java.util.List;

public interface ICompetenceMetier {
	
	public List<Competence> findAllCompetence() ;

	public Competence findOneCompetence(Long id);

	public Competence getOneCompetence(Long id) ;

	public Competence saveCompetence(Competence competence) ;

	public  Competence updateCompetence(Long id, Competence competence) ;

	void deleteCompetence(Long id);

	void changeEtatOffre(Long id, boolean etat);
	
	List<Competence> findCompetencelst();
	

}
