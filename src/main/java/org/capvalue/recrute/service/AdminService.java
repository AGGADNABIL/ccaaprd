package org.capvalue.recrute.service;

import java.util.UUID;

import org.capvalue.recrute.domaine.Admin;
import org.capvalue.recrute.domaine.User;
import org.capvalue.recrute.metier.IAdminMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminService {
	
	@Autowired
	private IAdminMetier adminMetier;
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/users/addAdmin", method = RequestMethod.POST)
	public Admin saveAdmin(@RequestBody Admin admin) {
		Admin adm=admin;
		String keyActivation=UUID.randomUUID().toString();
	    
		adm.setPassword(new BCryptPasswordEncoder(12).encode(adm.getPassword()));
		adm.setKeyActivation(keyActivation);
		
			return adminMetier.saveAdmin(adm);
		}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/users/updateAdmin", method = RequestMethod.PUT)
	public Admin updateAdmin(@RequestBody Admin admin) {
		Admin adm=adminMetier.findOneAdmin(admin.getUsername());
	    adm.setNom(admin.getNom());
	    adm.setPrenom(admin.getPrenom());
	    adm.setUsername(admin.getUsername());
		return adminMetier.updateAdmin(adm);
		}
}
