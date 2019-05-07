package org.capvalue.recrute.metierImpl;

import org.capvalue.recrute.dao.TypeContratRepository;
import org.capvalue.recrute.domaine.TypeContrat;
import org.capvalue.recrute.metier.ITypeContratMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TypeContratMetierImpl implements ITypeContratMetier {
	@Autowired
	TypeContratRepository contratRepository;
	@Override
	public List<TypeContrat> listContrat() {
		// TODO Auto-generated method stub
		return contratRepository.findAll();
	}

}
