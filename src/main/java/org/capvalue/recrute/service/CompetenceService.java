package org.capvalue.recrute.service;

import org.capvalue.recrute.dao.GroupCompetenceRepository;
import org.capvalue.recrute.domaine.Competence;
import org.capvalue.recrute.domaine.GroupCompetence;
import org.capvalue.recrute.metier.ICompetenceMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompetenceService {

	@Autowired
	ICompetenceMetier competenceMetier;
	
	@Autowired
	GroupCompetenceRepository groupCompetenceRepository;


	@RequestMapping(value = "/competences", method = RequestMethod.GET)
	public List<Competence> findAllCompetence() {
		return competenceMetier.findAllCompetence();
		
	}

	@RequestMapping(value = "/competencelst", method = RequestMethod.GET)
	public List<Competence> findAllC() {
		return competenceMetier.findCompetencelst();
		
	}
	
	
	@RequestMapping(value="/competences/{id}", method=RequestMethod.GET)
	public Competence findOneCompetence(@PathVariable Long id) {
		return competenceMetier.findOneCompetence(id);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/competences", method=RequestMethod.POST)
	public Competence saveCompetence(@RequestBody Competence competence) {
		return competenceMetier.saveCompetence(competence);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/competences/{codeGC}", method=RequestMethod.POST)
	public Competence saveCompetence(@PathVariable Long codeGC ,@RequestBody Competence competence) {
		GroupCompetence gc =groupCompetenceRepository.findOne(codeGC);
		//gc.setCodeGroupCompetence(codeGC);
		competence.setGroupCompetence(gc);
		System.out.println("codeGC :"+codeGC);
		return competenceMetier.saveCompetence(competence);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/competences/{id}", method=RequestMethod.PUT)
	public @ResponseBody Competence updateCompetence(@PathVariable Long id,@RequestBody Competence competence) {
		return competenceMetier.updateCompetence(id, competence);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/competences/{id}",method= RequestMethod.DELETE)
	public void deleteCompetence(@PathVariable("id") Long id) {
		competenceMetier.deleteCompetence(id);
	}


	@RequestMapping(value = "/competences/changeEtat/{id}/{etat}", method = RequestMethod.PUT)
	public int changeEtatOffre(@PathVariable ("id")Long id, @PathVariable("etat") boolean etat) {
		competenceMetier.changeEtatOffre(id, etat);
		return 1;
	}

	

}
