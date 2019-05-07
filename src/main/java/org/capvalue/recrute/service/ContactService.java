package org.capvalue.recrute.service;

import org.capvalue.recrute.metier.ICandidatMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
    
@RestController
public class ContactService {
	
	@Autowired
	ICandidatMetier candidatMetier;
	
	@RequestMapping(value="/contacter/{username}/{subject}/{message}", method=RequestMethod.GET)
	public String contacter(@PathVariable("username") String username,@PathVariable("subject") String subject,@PathVariable("message") String message){
		System.out.println("username : "+username + "  "+ subject+"  "+ message);
		return candidatMetier.contacter(username, subject, message);
	}
    
}
