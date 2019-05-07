package org.capvalue.recrute.metierImpl;

import org.capvalue.recrute.dao.VilleRepository;
import org.capvalue.recrute.domaine.Ville;
import org.capvalue.recrute.metier.IVilleMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VilleMetier  implements IVilleMetier{
	@Autowired
	VilleRepository villeRepository;

	public void deleteVille(Long id) {
		villeRepository.delete(id);
	}

	public List<Ville> findAllVilles() {
		return villeRepository.findAll();
	}

	public Ville findOneVille(Long id) {
		return villeRepository.findOne(id);
	}

	public Ville saveVille(Ville ville) {
		return villeRepository.saveAndFlush(ville);
	}

	public Ville saveAndFlush(Ville ville) {
		return villeRepository.saveAndFlush(ville);
	}

}
