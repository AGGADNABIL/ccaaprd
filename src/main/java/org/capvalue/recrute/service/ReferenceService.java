package org.capvalue.recrute.service;

import java.util.List;

import org.capvalue.recrute.domaine.Reference;
import org.capvalue.recrute.metier.IReferenceMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReferenceService {
	
	@Autowired
	IReferenceMetier referenceMetier;

	public void deleteReference(Long id) {
		referenceMetier.deleteReference(id);
	}
	//@Secured(value={"ROLE_CANDIDAT","ROLE_ADMIN"})
	@RequestMapping(value="/references",method=RequestMethod.GET)
	public List<Reference> findAllReferences() {
		return referenceMetier.findAllReferences();
	}

	public Reference findOneReference(Long id) {
		return referenceMetier.findOneReference(id);
	}

	public Reference saveReference(Reference reference) {
		return referenceMetier.saveReference(reference);
	}

	public Reference updateReference(Reference reference) {
		return referenceMetier.updateReference(reference);
	}

	@RequestMapping(value="/references/{codeRef}",method=RequestMethod.DELETE)
	public void removeReference(@PathVariable("codeRef") Long id) {
		 referenceMetier.deleteReference(id);
	}

		
}
