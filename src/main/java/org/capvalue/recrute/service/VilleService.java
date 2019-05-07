package org.capvalue.recrute.service;

import org.capvalue.recrute.domaine.Ville;
import org.capvalue.recrute.metier.IVilleMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VilleService {
   
	
	@Autowired
	IVilleMetier villeMetier;

	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/villes/{codeVille}",method=RequestMethod.DELETE)
	public void deleteVille(@PathVariable Long codeVille) {
		villeMetier.deleteVille(codeVille);
	}

	@RequestMapping(value="/villes", method=RequestMethod.GET)
	public List<Ville> findAllVilles() {
		return villeMetier.findAllVilles();
	}

	@RequestMapping(value="/villes/{codeVille}", method=RequestMethod.GET)
	public Ville findOneVille(@PathVariable Long codeVille) {
		return villeMetier.findOneVille(codeVille);
	}
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/villes", method=RequestMethod.POST)
	public Ville saveVille(@RequestBody Ville ville) {
		return villeMetier.saveVille(ville);
	}
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/villes/{codeVille}", method=RequestMethod.PUT)
	public Ville saveAndFlush(@PathVariable("codeVille") Long codeVille,@RequestBody Ville ville) {
		Ville  f=villeMetier.findOneVille(codeVille);
		f.setNomVille(ville.getNomVille());
		return villeMetier.saveAndFlush(f);
	}
	
	
}
