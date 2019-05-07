package org.capvalue.recrute.metier;

import org.capvalue.recrute.domaine.OffreCompetence;

import java.util.List;

/**
 * Created by Aimad MAJDOU on July 14, 2016
 */
public interface IOffreCompetenceMetier {

    void delete(Long id) ;

    List<OffreCompetence> findAll() ;

    OffreCompetence findOne(Long id);

    OffreCompetence save(OffreCompetence offreCompetence);

    OffreCompetence update(Long id, OffreCompetence offreCompetence) ;
    
    
    List<OffreCompetence> findAllNotDuplicate();
    List<OffreCompetence> findAllByIdOffre(Long id);
    
}
