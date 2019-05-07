package org.capvalue.recrute.metier;

import org.capvalue.recrute.domaine.Offre;
import org.capvalue.recrute.domaine.TypeContrat;
import org.capvalue.recrute.domaine.Ville;

import java.util.List;

public interface OffreMetier {

	void deleteOffre(Long id);
	List<Offre> findAllOffre();
	Offre findOneOffre(Long id);
	Offre saveOffre(Offre offre);
	void sauver();
	Offre updateOffre(Offre offre);
	List<Offre> offresSearch(String typeContrat,String ville, String competence);
	void changeEtatOffre(Long id, boolean eta);
	List<Offre> RechercheOffres();
	List<Offre> OffreByCompetence(Long id);
	List<Offre> OffreByVille(Long id);
	List<Offre> OffreByTypeContrat(Long id);
	List<Offre> offresByIdCandidat(String username);
	void incrementNbVue(Long codeOffre);
	List<Offre> offresNotSended();
	List<Offre> findOffreNotExpired();
	
	List<Offre> findAllVilleByOffre();
	List<Offre> findAllContratByOffre();
}
