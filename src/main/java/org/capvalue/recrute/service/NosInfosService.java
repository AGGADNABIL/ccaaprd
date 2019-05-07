package org.capvalue.recrute.service;

import java.util.List;

import org.capvalue.recrute.domaine.NosInfos;
import org.capvalue.recrute.metier.NosInfosMetier;
import org.capvalue.recrute.metier.NosInfosMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NosInfosService {

	@Autowired
	NosInfosMetier nosInfosMetier;

	@RequestMapping(value="/nosInfos/{codeNosInfos}",method=RequestMethod.DELETE)
	public void delete(@PathVariable("codeNosInfos") Long id) {
		nosInfosMetier.delete(id);
	}
	@RequestMapping(value="/nosInfos", method=RequestMethod.GET)
	public List<NosInfos> findAll() {
		return nosInfosMetier.findAll();
	}
 
	
	@RequestMapping(value="/ordered", method=RequestMethod.GET)
	public List<NosInfos> findAllOrd() {
		return nosInfosMetier.findAllOrdered();
	}
	
	@RequestMapping(value="/nosInfos/{id}",method=RequestMethod.GET)
	public NosInfos findOne(@PathVariable("id") Long id) {
		return nosInfosMetier.findOne(id);
	}

 
	@RequestMapping(value="/nosInfos", method=RequestMethod.POST)
	public NosInfos save(@RequestBody NosInfos nosInfos) {
		nosInfos.setImage(UploadService.imgInfos);
		System.out.println("imgInfos :"+UploadService.imgInfos);
		System.out.println("nosInfos show :"+nosInfos.getContenuShow());
		return nosInfosMetier.save(nosInfos);
	}
	@RequestMapping(value="/nosInfos/{codeNosInfos}", method=RequestMethod.PUT) 
	NosInfos saveAndFlush(@PathVariable("codeNosInfos") Long codeNosInfos,@RequestBody NosInfos nosInfos) {
		nosInfos.setId(codeNosInfos);
		nosInfos.setImage(UploadService.imgInfos);
		return nosInfosMetier.saveAndFlush(nosInfos);
	}
	
}
