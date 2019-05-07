package org.capvalue.recrute.metierImpl;

import java.util.List;

import org.capvalue.recrute.dao.NosInfosRepository;
import org.capvalue.recrute.domaine.NosInfos;
import org.capvalue.recrute.metier.NosInfosMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NosInfosImpl implements NosInfosMetier {

	@Autowired
	NosInfosRepository nosInfosRepository;

	public void delete(Long id) {
		nosInfosRepository.delete(id);
	}

	public List<NosInfos> findAll() {
		System.out.println("taille est :"+nosInfosRepository.findAll().size());
		return nosInfosRepository.findAll();
	}

	public NosInfos findOne(Long id) {
		return nosInfosRepository.findOne(id);
	}

	public NosInfos save(NosInfos nosInfos) {
		return nosInfosRepository.save(nosInfos);
	}

	public NosInfos saveAndFlush(NosInfos nosInfos) {
		return nosInfosRepository.saveAndFlush(nosInfos);
	}

	@Override
	public List<NosInfos> findAllOrdered() {
		// TODO Auto-generated method stub
		return nosInfosRepository.findAllOrdered();
	}


	

}
