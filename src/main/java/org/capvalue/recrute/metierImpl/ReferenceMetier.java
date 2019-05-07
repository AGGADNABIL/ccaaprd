package org.capvalue.recrute.metierImpl;

import org.capvalue.recrute.dao.ReferenceRepository;
import org.capvalue.recrute.domaine.Reference;
import org.capvalue.recrute.metier.IReferenceMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReferenceMetier implements IReferenceMetier{
	
	@Autowired
	ReferenceRepository referenceRepository;

	public void deleteReference(Long id) {
		referenceRepository.delete(id);
	}

	public List<Reference> findAllReferences() {
		return referenceRepository.findAll();
	}

	public Reference findOneReference(Long id) {
		return referenceRepository.findOne(id);
	}

	public Reference  saveReference(Reference reference) {
		return referenceRepository.save(reference);
	}

	public Reference updateReference(Reference reference) {
		return referenceRepository.saveAndFlush(reference);
	}
	

}
