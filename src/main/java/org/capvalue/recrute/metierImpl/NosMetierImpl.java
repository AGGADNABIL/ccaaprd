package org.capvalue.recrute.metierImpl;

import java.util.List;

import org.capvalue.recrute.dao.NosMetierRepository;
import org.capvalue.recrute.domaine.NosMetier;
import org.capvalue.recrute.metier.NosMetierMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NosMetierImpl implements NosMetierMetier {

	@Autowired
	NosMetierRepository metierRepository;

	public void delete(Long id) {
		metierRepository.delete(id);
	}

	public List<NosMetier> findAll() {
		return metierRepository.findAll();
	}

	public NosMetier findOne(Long id) {
		return metierRepository.findOne(id);
	}

	public NosMetier save(NosMetier nosMetier) {
		return metierRepository.save(nosMetier);
	}

	public NosMetier saveAndFlush(NosMetier nosMetier) {
		return metierRepository.saveAndFlush(nosMetier);
	}

	@Override
	public List<NosMetier> findAllMetier() {
		// TODO Auto-generated method stub
		return metierRepository.findAllMet();
	}


	

}
