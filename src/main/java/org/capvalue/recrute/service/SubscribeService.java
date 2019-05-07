package org.capvalue.recrute.service;

import org.capvalue.recrute.domaine.Subscribe;
import org.capvalue.recrute.metier.ISubscribeMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value="/subscribes")
public class SubscribeService {
	@Autowired
	private ISubscribeMetier subscribeMetier;
	
	@RequestMapping(value="/subscribes",method=RequestMethod.POST)
	public ResponseEntity<String> saveSubscribe(@RequestBody Subscribe subscribe)throws Exception{
		
									// avec  emailSended=false
		String response = subscribeMetier.saveSubscribe(subscribe);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		if (response.equals("username_already_exists"))
			return new ResponseEntity<>("USERNAME_CONFLICT", headers, HttpStatus.CONFLICT);
		else if (response.equals("mailer_error"))
			return new ResponseEntity<>("EMAIL_SERVICE_UNAVAILABLE", headers, HttpStatus.SERVICE_UNAVAILABLE);
		else
			return new ResponseEntity<>("SUBSCRIPTION_SUCCESSFUL", headers, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/subscribes",method=RequestMethod.PUT)
	public Subscribe updateSubscribe(@RequestBody Subscribe subscribe){
		return subscribeMetier.updateSubscribe(subscribe);
	}
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/subscribes/{username}",method=RequestMethod.DELETE)
	public void deleteSubscribe(@PathVariable("username") String username){
		subscribeMetier.deleteSubscribe(username);
	}
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/subscribes",method=RequestMethod.GET)
	public List<Subscribe>getAllSubscribe(){
		return subscribeMetier.getAllSubscribe();
	}
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/getOneSubscribe/{username}/",method=RequestMethod.GET)
	public Subscribe getOneSubscribe(@PathVariable("username")String username){
		return subscribeMetier.getOneSubscribe(username);
	}
	@RequestMapping(value="/activedSubscribe/{username}/",method=RequestMethod.GET)
	public void activedSubscribe(@PathVariable("username")String username){
		subscribeMetier.activedSubscribe(username);
	}
	@RequestMapping(value="/desactivedSubscribe/{username}/",method=RequestMethod.GET)
	public void desactivedSubscribe(@PathVariable("username")String username){
		subscribeMetier.desactivedSubscribe(username);
	}
	
	@RequestMapping(value="/desinscrire/{username}/",method=RequestMethod.GET)
	public void desinscrireSubscribe(@PathVariable("username") String username,HttpServletResponse httpServletResponse) throws IOException{
		// avec desinscrire=false
		subscribeMetier.desinscrireSubscribe(username);
		httpServletResponse.sendRedirect("http://localhost:8080/app/index.html#/app/home");
		
	}
}
