package org.capvalue.recrute.service;

import org.capvalue.recrute.conf.services;
import org.capvalue.recrute.domaine.User;
import org.capvalue.recrute.metier.IUserMetier;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserService {
	@Autowired
	private IUserMetier userMetier;
	
	@Secured(value={"ROLE_CANDIDAT","ROLE_ADMIN"})
	@RequestMapping(value = "/users/{username}/users", method = RequestMethod.GET)
	public User getUserByUsername(@PathVariable("username") String username) {
		return userMetier.getUserByUsername(username);
	}
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/allUsers", method = RequestMethod.GET)
	public Collection<User> getAllUsers() {
		return userMetier.getUsers();
	}
	
	@RequestMapping(value="/currentUser",method=RequestMethod.GET)
	public User userCurrent(){
		return userMetier.showUser(services.username);
	}
	
	
    @RequestMapping(value="/currentUser/{username}/changed",method=RequestMethod.PUT)
	public User changePasswrd(@PathVariable("username") String username,@RequestBody User user ){
		return userMetier.updatePasswrd(username,user);
	}
    
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/allUsers/{username}/{etat}", method = RequestMethod.PUT)
	public int changeEtatUser(@PathVariable("username") String username, @PathVariable("etat") boolean etat) {
		System.out.println("actived : "+ etat );
		userMetier.changeEtatUser(username , etat);
		return 1;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/allUsers/{username}/target", method = RequestMethod.DELETE)
	public void removeUser(@PathVariable("username") String username) {	
		userMetier.removeUser(username);
	}
	
	
	 /**
     * Service used to request the account recover when forgetting password
     * this service takes email as a parameter and sends an email
     * to the user email account contains the verification key
     * which will be used to recover the account
     * @param email
     * @return
     */
    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    ResponseEntity<String> forgotPassword(@RequestParam(value = "email") String email){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        String response = userMetier.forgotPassword(email);

        if(response.equals("EMAIL_BAD_REQUEST"))
            return new ResponseEntity<>("EMAIL_BAD_REQUEST",headers, HttpStatus.BAD_REQUEST);
        else if(response.equals("MAIL_SERVER_ERROR"))
            return new ResponseEntity<>("MAIL_SERVER_ERROR",headers, HttpStatus.INTERNAL_SERVER_ERROR);
        else if(response.equals("PASSWORD_REQUESTED_SUCCESSFULY"))
            return new ResponseEntity<>("PASSWORD_REQUESTED_SUCCESSFULY",headers, HttpStatus.OK);
        else
            return null;

    }

    /**
     * This service is used to redirect the user to the page where he can set
     * his new password after he has requested the account recovery
     *
     * @param email
     * @param verificationKey
     * @param httpServletResponse
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/recoverAccount", method = RequestMethod.GET)
    public ResponseEntity<String> recoverAccount(@RequestParam(value = "email") String email,
                                          @RequestParam(value = "verificationKey") String verificationKey,
                                          HttpServletResponse httpServletResponse) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        String response = userMetier.recoverAccount(email,verificationKey);

        if(response.equals("EMAIL_BAD_REQUEST"))
            return new ResponseEntity<>("EMAIL_BAD_REQUEST",headers, HttpStatus.BAD_REQUEST);
        else if(response.equals("VERIFICATION_KEY_SUCCEFUL")) {
            return new ResponseEntity<>("VERIFICATION_KEY_SUCCEFUL", headers, HttpStatus.OK);
        }
        else if(response.equals("VERIFICATION_KEY_BAD_REQUEST"))
            return new ResponseEntity<>("VERIFICATION_KEY_BAD_REQUEST",headers, HttpStatus.BAD_REQUEST);
        else
            return null;

    }

    /**
     * This service is used to set the new password after
     * the user has requeste to recover his account
     * @param email
     * @param verificationKey
     * @param password
     * @return
     */    
    @RequestMapping(value = "/recoverPassword/{email}/{verificationKey}", method = RequestMethod.PUT)
    public ResponseEntity<String> recoverPassword(@PathVariable(value = "email") String email,
    											  @PathVariable(value = "verificationKey") String verificationKey,
    											  @RequestBody String password) {
    	JSONObject object = new JSONObject(password);
    	String pass = object.getString("password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        String response = userMetier.recoverPassword(email,verificationKey,pass);

        if(response.equals("EMAIL_BAD_REQUEST"))
            return new ResponseEntity<>("EMAIL_BAD_REQUEST",headers, HttpStatus.BAD_REQUEST);
        else if(response.equals("PASSWORD_CHANGED_SUCCEFULLY")) {
            return new ResponseEntity<>("PASSWORD_CHANGED_SUCCEFULLY", headers, HttpStatus.OK);
        }
        else if(response.equals("VERIFICATION_KEY_BAD_REQUEST"))
            return new ResponseEntity<>("VERIFICATION_KEY_BAD_REQUEST",headers, HttpStatus.BAD_REQUEST);
        else
            return null;

    }

}
