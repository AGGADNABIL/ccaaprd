package org.capvalue.recrute.service;

import org.capvalue.recrute.conf.services;
import org.capvalue.recrute.domaine.Candidat;
import org.capvalue.recrute.domaine.User;
import org.capvalue.recrute.metier.ICandidatMetier;
import org.capvalue.recrute.metier.IUserMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/candidats")
public class CandidatService {
	@Autowired
	private ICandidatMetier candidatMetier;
	@Autowired
	private IUserMetier userMetier;

	@RequestMapping(value = "/candidats", method = RequestMethod.POST)
	public ResponseEntity<String> saveCandidat(@RequestBody Candidat candidat) throws Exception {
		String response = candidatMetier.saveCandidat(candidat);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		if (response.equals("username_already_exists"))
			return new ResponseEntity<>("USERNAME_CONFLICT", headers, HttpStatus.CONFLICT);
		else if (response.equals("mailer_error"))
			return new ResponseEntity<>("EMAIL_SERVICE_UNAVAILABLE", headers, HttpStatus.SERVICE_UNAVAILABLE);
		else
			return new ResponseEntity<>("SIGNUP_SUCCESSFUL", headers, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/candidats", method = RequestMethod.GET)
	public List<Candidat> getAllCandidat() {
		return candidatMetier.getAllCandidat();
	}

/*  old 
 @RequestMapping(value = "/activateAccount")
	public ResponseEntity<String> activationMail(@RequestParam(value = "username") String username,
			@RequestParam(value = "activationKey") String keyActivation, HttpServletResponse httpServletResponse)
			throws IOException {
 * */	
	
	
	 @RequestMapping(value = "/activateAccount/{username}/{activationKey}/" , method = RequestMethod.GET)
		public ResponseEntity<String> activationMail(@PathVariable("username") String username,
				@PathVariable("activationKey") String keyActivation, HttpServletResponse httpServletResponse)
				throws IOException {
		 
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		
		  //avec actived=true
		String response = candidatMetier.activateAccount(username, keyActivation);
		if (response.equals("EMAIL_SERVICE_UNAVAILABLE")) {
			return new ResponseEntity<>("EMAIL_SERVICE_UNAVAILABLE", headers, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (response.equals("ACCOUNT_ACTIVATED")) {
			//old
			httpServletResponse.sendRedirect("http://localhost:8080/app/index.html#/app/login?action=account-activated");
			
			//httpServletResponse.sendRedirect("http://localhost:8080/app/index.html#/app/login");
			return new ResponseEntity<>("ACCOUNT_ACTIVATED", headers, HttpStatus.OK);
		} else if (response.equals("ACTIVIATION_KEY_BAD_REQUEST")) {
			return new ResponseEntity<>("ACTIVIATION_KEY_BAD_REQUEST", headers, HttpStatus.BAD_REQUEST);
		} else if (response.equals("USERNAME_BAD_REQUEST")) {
			return new ResponseEntity<>("USERNAME_BAD_REQUEST", headers, HttpStatus.BAD_REQUEST);
		} else if (response.equals("USERNAME_NOT_FOUND")) {
			return new ResponseEntity<>("USERNAME_NOT_FOUND", headers, HttpStatus.NOT_FOUND);
		} else {
			return null;
		}

	}
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/candidats/{username}/candidats", method = RequestMethod.DELETE)
	public void deleteCandidat(@PathVariable("username") String username) {
		candidatMetier.deleteCandidat(username);
	}
	@Secured("ROLE_CANDIDAT")
	@RequestMapping(value = "/candidats", method = RequestMethod.PUT)
	public Candidat updateCandidat(@RequestBody Candidat candidat) {
		candidat.setUsername(services.username);
		return candidatMetier.updateCandidat(candidat);
	}

	@Secured(value={"ROLE_CANDIDAT","ROLE_ADMIN"})
	@RequestMapping(value = "/findOneCandidat/{username}/candidats", method = RequestMethod.GET)
	public Candidat findOneCandidat(@PathVariable("username") String username) {
		User u=userMetier.getUserByUsername(services.username);
		//if(u.getTypeUser().equals("UC"))username=services.username;
		return candidatMetier.getCandidatByUsername(username);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/contactCandidat/{username}/{message}")
	public ResponseEntity<String> contactCandidat(@PathVariable("username") String username, @PathVariable("message") String message) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		String response = candidatMetier.contactCandidat(username, message);
		if (response.equals("Message bien envoyer")) {
			return new ResponseEntity<>("MESSAGE_SENDED", headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("EMAIL_SERVICE_UNAVAILABLE", headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Secured(value={"ROLE_CANDIDAT","ROLE_ADMIN"})
	@RequestMapping(value="/getCandidatById/{codeCandidat}",method = RequestMethod.GET)
	public Candidat getCandidatById(@PathVariable("codeCandidat") Long codeCandidat){
		User u=userMetier.getUserByUsername(services.username);
		if(u.getTypeUser().equals("UC")){
			Candidat c=candidatMetier.findOneCandidat(services.username);
			codeCandidat=c.getCodeUser();
		}
		return candidatMetier.getCandidatById(codeCandidat);	
	}
	@Secured(value={"ROLE_ADMIN"})
	@RequestMapping(value="/candidatsPostulersByOffre/{codeOffre}",method=RequestMethod.GET)
	public List<Candidat>getCandidatPosulerByOffre(@PathVariable(value="codeOffre")Long codeOffre){
		return candidatMetier.getCandidatPosulerByOffre(codeOffre);
	}
	
	@RequestMapping(value = "/users/desinscrire/{username}/", method = RequestMethod.GET)
	public void updateUser(@PathVariable("username") String username,HttpServletResponse httpServletResponse) throws IOException {
		 candidatMetier.updateNotNotifiedUsers(username);
		 httpServletResponse.sendRedirect("http://localhost:8080/app/index.html#/app/home");
	}
}
