package org.capvalue.recrute.conf;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
@RestController
public class services {
	public static String username;
	
	@RequestMapping("/login")
	public String  login(HttpServletResponse httpServletResponse) throws IOException {
		httpServletResponse.sendRedirect("http://localhost:8080/app/index.html#/app/login");
		return "loginPage";
	}
	
	@RequestMapping("/")
	public String  home(HttpServletResponse httpServletResponse) throws IOException {
		httpServletResponse.sendRedirect("http://localhost:8080/app/index.html#/app/home");
		return "home";
	}
	
	@RequestMapping(value="/user")
	public Principal user(Principal user) {
		username=user.getName(); 
		return user;
	}
	
	@RequestMapping("/accessDenied")
	public String  accessDenied(HttpServletResponse httpServletResponse) throws IOException {
		//httpServletResponse.sendRedirect("http://localhost:8080/app/index.html#/app/login");
		return "Accès non autorisé";
	}
	
}
