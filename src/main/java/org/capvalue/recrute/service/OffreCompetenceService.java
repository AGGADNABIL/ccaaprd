package org.capvalue.recrute.service;

import org.capvalue.recrute.domaine.OffreCompetence;
import org.capvalue.recrute.metier.IOffreCompetenceMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Aimad MAJDOU on July 14, 2016
 */
@RestController
public class OffreCompetenceService {
    
    @Autowired
    private IOffreCompetenceMetier offreCompetenceMetier;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/offre-competence/{codeOffreCompetence}",method= RequestMethod.DELETE)
    public void deleteOffreCompetence(@PathVariable Long codeOffreCompetence) {
        offreCompetenceMetier.delete(codeOffreCompetence);
    }

    @RequestMapping(value="/offre-competence", method=RequestMethod.GET)
    public List<OffreCompetence> findAllOffreCompetences() {
        return offreCompetenceMetier.findAll();
    }

    @RequestMapping(value="/offre-competence-not", method=RequestMethod.GET)
    public List<OffreCompetence> findAllOffreComptenc() {
        return offreCompetenceMetier.findAllNotDuplicate();
    }
    
    @RequestMapping(value="/offre-competence/{id}", method=RequestMethod.GET)
    public OffreCompetence findOneOffreCompetence(@PathVariable Long id) {
        return offreCompetenceMetier.findOne(id);
    }

    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/offre-competence", method=RequestMethod.POST)
    public OffreCompetence saveOffreCompetence(@RequestBody OffreCompetence offreCompetence) {
        return offreCompetenceMetier.save(offreCompetence);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/offre-competence/{id}", method=RequestMethod.PUT)
    public @ResponseBody OffreCompetence saveAndFlush(@PathVariable Long id,@RequestBody OffreCompetence offreCompetence) {
        return offreCompetenceMetier.update(id, offreCompetence);
    }
    @RequestMapping(value="/offre-competences/{id}", method=RequestMethod.GET)
    public List<OffreCompetence> findAllByIdOffre(@PathVariable Long id) {
        return offreCompetenceMetier.findAllByIdOffre(id);
    }
}
