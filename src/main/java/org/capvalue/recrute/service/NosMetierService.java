package org.capvalue.recrute.service;

import java.util.List;

import org.capvalue.recrute.domaine.NosMetier;
import org.capvalue.recrute.metier.NosMetierMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NosMetierService {

	@Autowired
	NosMetierMetier nosMetierMetier;

	@RequestMapping(value="/nosMetier/{codeNosMetier}",method=RequestMethod.DELETE)
	public void delete(@PathVariable("codeNosMetier") Long id) {
		nosMetierMetier.delete(id);
	}
	@RequestMapping(value="/nosMetier", method=RequestMethod.GET)
	public List<NosMetier> findAll() {
		return nosMetierMetier.findAll();
	}
	
	//orderedMetier
	
	@RequestMapping(value="/orderedMetier", method=RequestMethod.GET)
	public List<NosMetier> findAllOrder() {
	return nosMetierMetier.findAllMetier();
	}

	@RequestMapping(value="/nosMetier/{id}",method=RequestMethod.GET)
	public NosMetier findOne(@PathVariable("id") Long id) {
		return nosMetierMetier.findOne(id);
	}

 
	@RequestMapping(value="/nosMetier", method=RequestMethod.POST)
	public NosMetier save(@RequestBody NosMetier nosMetier) {
		nosMetier.setImage(UploadService.imgInfos);
		System.out.println("imgMeier :"+UploadService.imgInfos);
		System.out.println("nosMetier show :"+nosMetier.getContenuShow());
		return nosMetierMetier.save(nosMetier);
	}
	
	@RequestMapping(value="/nosMetier/{id}", method=RequestMethod.PUT) 
	NosMetier saveAndFlush(@PathVariable("id") Long id,@RequestBody NosMetier nosMetier) {
		nosMetier.setId(id);
		nosMetier.setImage(UploadService.imgInfos);
		return nosMetierMetier.saveAndFlush(nosMetier);
	}
	
	
}
