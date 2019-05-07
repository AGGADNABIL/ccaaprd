package org.capvalue.recrute.service;

import org.capvalue.recrute.domaine.TypeContrat;
import org.capvalue.recrute.metier.ITypeContratMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TypeContratService {
	@Autowired
	private ITypeContratMetier contratMetier;
	@RequestMapping(value="/contrats", method=RequestMethod.GET)
	public List<TypeContrat> listcontrat() {
		return contratMetier.listContrat();
	}
	

}
