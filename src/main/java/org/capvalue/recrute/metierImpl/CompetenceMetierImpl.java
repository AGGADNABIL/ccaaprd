package org.capvalue.recrute.metierImpl;

import java.util.List;

import org.capvalue.recrute.dao.CompetenceRepository;
import org.capvalue.recrute.domaine.Competence;
import org.capvalue.recrute.metier.ICompetenceMetier;
import org.capvalue.recrute.metier.IGroupCompetenceMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CompetenceMetierImpl implements ICompetenceMetier{
	@Autowired
	CompetenceRepository competenceRepository;
	@Autowired
	private IGroupCompetenceMetier groupCompetenceMetier;

	public List<Competence> findAllCompetence() {
		return competenceRepository.findAll();
	}

	public Competence findOneCompetence(Long id) {
		return competenceRepository.findOne(id);
	}

	public Competence getOneCompetence(Long id) {
		return competenceRepository.getOne(id);
	}

	public Competence saveCompetence(Competence competence) {
		return competenceRepository.save(competence);
	}

	public  Competence updateCompetence(Long id, Competence competence) {

		Competence newCompetence = competenceRepository.findOne(id);
		newCompetence.setTitre(competence.getTitre());
		newCompetence.setActivated(competence.getActivated());
		return competenceRepository.saveAndFlush(newCompetence);
	}

	public void deleteCompetence(Long id) {
		competenceRepository.delete(id);
	}

	public void changeEtatOffre(Long id, boolean etat) {
		Competence competence= competenceRepository.findOne(id);
		competence.setActivated(etat);
		competenceRepository.saveAndFlush(competence);
	}

	@Override
	public List<Competence> findCompetencelst() {
		// TODO Auto-generated method stub
		return competenceRepository.findCompetenceInOC();
	}


}
