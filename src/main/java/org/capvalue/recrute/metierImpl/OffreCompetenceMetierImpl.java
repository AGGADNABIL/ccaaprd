package org.capvalue.recrute.metierImpl;

import org.capvalue.recrute.dao.CompetenceRepository;
import org.capvalue.recrute.dao.OffreCompetenceRepository;
import org.capvalue.recrute.dao.OffreRepository;
import org.capvalue.recrute.domaine.OffreCompetence;
import org.capvalue.recrute.metier.IOffreCompetenceMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Aimad MAJDOU on July 14, 2016
 */
@Service
public class OffreCompetenceMetierImpl implements IOffreCompetenceMetier{

    @Autowired
    private OffreCompetenceRepository offreCompetenceRepository;

    @Autowired
    private OffreRepository offreRepository;

    @Autowired
    private CompetenceRepository competenceRepository;


    @Override
    public void delete(Long id) {
        offreCompetenceRepository.delete(id);
    }

    @Override
    public List<OffreCompetence> findAll() {
        return offreCompetenceRepository.findAll();
    }

    @Override
    public OffreCompetence findOne(Long id) {
        return offreCompetenceRepository.findOne(id);
    }

    @Override
    public OffreCompetence save(OffreCompetence offreCompetence) {
        return offreCompetenceRepository.save(offreCompetence);
    }

    @Override
    public OffreCompetence update(Long id, OffreCompetence offreCompetence) {
        OffreCompetence newOffreCompetence = offreCompetenceRepository.findOne(id);
        newOffreCompetence.setOffre(offreRepository.findOne(offreCompetence.getOffre().getCodeOffre()));
        newOffreCompetence.setCompetence(competenceRepository.findOne(offreCompetence.getCompetence().getCodeCompetence()));
        newOffreCompetence.setNiveauRequis(offreCompetence.getNiveauRequis());
        return offreCompetenceRepository.saveAndFlush(newOffreCompetence);
    }

	@Override
	public List<OffreCompetence> findAllNotDuplicate() {
		// TODO Auto-generated method stub
		return offreCompetenceRepository.offreCompetenceNotDuplicate();
	}

	@Override
	public List<OffreCompetence> findAllByIdOffre(Long id) {
		// TODO Auto-generated method stub
		return offreCompetenceRepository.findAllByIdOffre(id);
	}
}
