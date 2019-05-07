package org.capvalue.recrute.metier;

import org.capvalue.recrute.domaine.Reference;

import java.util.List;

public interface IReferenceMetier {
	
	public void deleteReference(Long id) ;

	public List<Reference> findAllReferences() ;

	public Reference findOneReference(Long id) ;

	public Reference  saveReference(Reference reference) ;

	public Reference updateReference(Reference reference) ;

}
