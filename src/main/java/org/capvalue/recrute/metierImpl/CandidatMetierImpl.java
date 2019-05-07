package org.capvalue.recrute.metierImpl;

import org.capvalue.recrute.conf.smtpEmailSender;
import org.capvalue.recrute.dao.CandidatRepository;
import org.capvalue.recrute.dao.PostulerRepository;
import org.capvalue.recrute.dao.RoleRepository;
import org.capvalue.recrute.dao.UserRepository;
import org.capvalue.recrute.domaine.Candidat;
import org.capvalue.recrute.domaine.Role;
import org.capvalue.recrute.domaine.User;
import org.capvalue.recrute.metier.ICandidatMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class CandidatMetierImpl implements ICandidatMetier {
	@Autowired
	CandidatRepository candidatRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	private smtpEmailSender emailSender;
	@Autowired
	PostulerRepository postulerRepository;

	@Override
	public String saveCandidat(Candidat candidat) {
		User u=candidat;
		String password=candidat.getPassword();
		boolean existException = false;
        if (userRepository.getUserByUsername(u.getUsername()) != null){
        	System.out.println("username_already_exists");
        	return "username_already_exists";
        }
        
        String activationKey = UUID.randomUUID().toString();
        candidat.setPassword(new BCryptPasswordEncoder(12).encode(u.getPassword()));
        candidat.setUsername(u.getUsername());
        candidat.setActived(false);
        candidat.setKeyActivation(activationKey);
        Role role = roleRepository.findOne("CANDIDAT");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        candidat.setRoles(roles);
        System.out.println("email sended to "+candidat.getUsername());
        try {
        	//System.out.println("email sended to "+candidat.getUsername());
        	String body="<html>"
            		+ "<body>"
            		+"<div bgcolor=\"#0087b4\" style=\"Margin:0;padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;min-width:100%;background-color:#0087b4;background-image:none;background-repeat:repeat;background-position:top left;display:block\">"
            	    +"<center style=\"width:100%;table-layout:fixed\"><div style=\"max-width:600px ;padding-top:40px padding-bottom:40px;\">"
            	    + "  <table align=\"center\" style=\"border-spacing:0;Margin:20px auto;width:95%;max-width:600px padding-top:20px;\"><tbody><tr>"
            	    + "<td style=\"padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;background-color:white;background-image:none;background-repeat:repeat;background-position:top left;border-radius:8px 8px 8px 8px\">"
                    +"<table width=\"100%\" style=\"border-spacing:0\">"
                    		+ "<tbody><tr><td style=\"padding-top:10px;padding-bottom:10px;padding-right:0;padding-left:0;text-align:center\">"
                        
                               +" <div style=\"padding-top:0;padding-bottom:0;padding-right:45px;padding-left:45px\">"
                                   
                                  + "<br> <p style=\"Margin:0;line-height:1.5;font-size:24px;Margin-bottom:20px;color:#0087b4;font-family:arial,sans-serif;text-align:center\">Bonjour "+candidat.getNom() +", bienvenue sur le site CapRecrute!</p>"
                                    +" <p style=\"Margin:0;font-family:arial,sans-serif;line-height:1.5;Margin-bottom:10px;color:#000000;font-size:16px;text-align:center\">Votre compte a été créé. Il vous sera désormais plus facile que jamais de recevoir nos deniers offres d'emplois selon votre profile.<br>"
                                       +"<p><b>Veuillez activer votre compte en cliquant sur le lien ci-dessous :</b></p>"
                                      +"<br>"
                                      + "<p>Votre login est : <span style:\"color:blue;\">"+candidat.getUsername()+"</span></p>"
                                      + "<p>Votre mot de passe est: <span style:\"color:blue;\">"+password+"</span></p>"
                                      //+ "<a href = 'http://localhost:8080/app/index.html#/candidats/activateAccount?username="
                                      //+ candidat.getUsername() + "&activationKey=" + activationKey + "'>Activer mon compte</a></p>"
                                     
                                      //+ "<a href = 'http://localhost:8080/app/index.html#/candidats/activateAccount/"
                                      //+ candidat.getUsername() + "/" + activationKey + "/'>Activer mon compte</a></p>"
                                      
                                      + "<p><a href =\"http://localhost:8080/candidats/activateAccount/"+candidat.getUsername() +"/ "+activationKey +"/\">Activer mon compte</a></p>"
            
                                      
                                     +"<div style=\"padding-top:0;padding-bottom:0;padding-right:45px;padding-left:45px\"><br>"
                                  
                                     +"<p style=\"Margin:0;font-family:arial,sans-serif;line-height:1.5;Margin-bottom:10px;color:#000000;font-size:16px;text-align:center\">La chance est avec vous.<br>"
                                     +" À bientôt,<br><br>"
                                     +"<span style=\"font-style:italic\">L'équipe de CapValue Votre partenir de recrutement.</span></p>"
                                     +"</div><div style=\"border-width:1px;border-style:dashed;border-color:#000000\"></div>"
                                     +"<div style=\"padding-top:0;padding-bottom:0;padding-right:45px;padding-left:45px\">"
                                     +"<br><p style=\"Margin:0;font-family:arial,sans-serif;line-height:1.5;font-size:10px;color:#999999;text-align:center\">Rejoignez-nous sur</p><br>"
                                     +"</div>"
                                    
                                
                                +"<table width=\"187px\" style=\"margin-top:0;margin-bottom:0;margin-right:auto;margin-left:auto;border-spacing:0;display:inline-table\"><tbody><tr>"
                                    
                                        +" <td style=\"padding-top:0;padding-bottom:0;padding-right:0;padding-left:0\"><a href=\"\" style=\"color:#ee6a56;text-decoration:underline\" target=\"_blank\" ><img src=\"https://ci5.googleusercontent.com/proxy/P0NtRoz5mS6UQHr3GTql31TODALhyoiqst6F2_JpD9lmYMi1ULBhOvrngQ-qn4s2K_BKyKOvpcGd3wl8pDRgCPsNGsJOEvK1iKSdkgWNGKQutVmJ5S-RmY_qts_-DmWwdwfo=s0-d-e1-ft#https://happn-mail.s3-eu-west-1.amazonaws.com/mails/welcome/facebook_mail.jpg\" style=\"width:32px;margin-right:21px;border-width:0;max-width:600px\" ></a></td>"
                                        +"<td style=\"padding-top:0;padding-bottom:0;padding-right:0;padding-left:0\"><a href=\"\" style=\"color:#ee6a56;text-decoration:underline\" target=\"_blank\" ><img src=\"https://ci4.googleusercontent.com/proxy/VVAGNvK8qY0mx0dzp1U5nbd968Bo74uGmiF2map5z78S_Ij6Sqv4D4aEeslHkXBN8vJGNGRC_DVMZfEOZBinkxYfj4ue6xKDfMN8oXU_MKIklLIqMj68eVxrR_ufMCMARjA=s0-d-e1-ft#https://happn-mail.s3-eu-west-1.amazonaws.com/mails/welcome/twitter_mail.jpg\" style=\"width:32px;margin-right:21px;border-width:0;max-width:600px\"></a></td>"
                                        +"<td style=\"padding-top:0;padding-bottom:0;padding-right:0;padding-left:0\"><a href=\"\" style=\"color:#ee6a56;text-decoration:underline\" target=\"_blank\"><img src=\"https://ci5.googleusercontent.com/proxy/i4B2Zb8U3abMi7jDfBT61UuPWbDu4O9YlGeVWDgZaeVps5li1mQ2T8nLGGbWUu-viIrB_mL0eNssaQvkbjQqMsipSyvffH7a_Dz5L3KIN1wm07-k2AHft7gg_f2MXXZ_qTA=s0-d-e1-ft#https://happn-mail.s3-eu-west-1.amazonaws.com/mails/welcome/youtube_mail.jpg\"  style=\"width:32px;margin-right:21px;border-width:0;max-width:600px\"></a></td>"
                                        +"</tr> </tbody></table> <br></td></tr> </tbody></table> </td> </tr> </tbody></table> </div></center><br>"
                                      //-------------
                                      + "<script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js\"></script>"
						    			+ "<script> "
						    			+ "function activedAccount(username,key){"
						    			+ "$.ajax({"
						    			+ "url : \"http://localhost:8080/candidats/activateAccount/\"+username+\"/\"+key+\"/\","     //  /recruteCapvalue
						    			+ "type : \"GET\","
						    			+ "success : function() {},"
						    			+ "error : function() {}"
						    			+ "});"
						    			+ "}"
						    			+ "</script>"
          					//------------
          		
            	
            		+ "</body>"
            		+ "</html>"
            		+ "";
        	
            emailSender.send(candidat.getUsername(), "Email d'activation du compte",body);
                
        } catch (MessagingException e) {
        	existException = true;
            e.printStackTrace();
        }

        if (!existException) {
            candidatRepository.save(candidat);
        }
        return existException ? "mailer_error" : "You have succefully signed up as a user";
	}

	@Override
	public void deleteCandidat(String username) {
		Candidat c=getCandidatByUsername(username);
		c.setRoles(null);
		candidatRepository.saveAndFlush(c);
		candidatRepository.delete(c);
		
	}

	@Override
	public Candidat updateCandidat(Candidat candidat) {
		Candidat c=findOneCandidat(candidat.getUsername());
		candidat.setRoles(c.getRoles());
		candidat.setActived(c.isActived());
		candidat.setPassword(c.getPassword());
		if(candidat.getCv()==null && c.getCv()!=null){
			candidat.setCv(c.getCv());
		}
		candidat.setCodeUser(c.getCodeUser());
		return candidatRepository.saveAndFlush(candidat);
	}

	@Override
	public Candidat findOneCandidat(String username) {
		return candidatRepository.findOne(username);
	}

	@Override
	public String contactCandidat(String username,String message) {
		Candidat cand=findOneCandidat(username);
		try {
            emailSender.send(cand.getUsername(),"CapRecrute",message);
        } catch (MessagingException e) {
            return "EMAIL_SERVICE_UNAVAILABLE";
        }
		return "Message bien envoyer";
	}

	@Override
	public List<Candidat> getAllCandidat() {
		List<Candidat>candidats=candidatRepository.findAll();
		for(Candidat cand:candidats){
			cand.setPassword(null);
		}
		return candidats;
	}

	@Override
	public Candidat getCandidatById(Long codeCandidat) {
		// TODO Auto-generated method stub
		return candidatRepository.getCandidatById(codeCandidat);
	}

	@Override
	public String activateAccount(String username, String activationKey) {
		Candidat cand =findOneCandidat(username);
        if (cand != null) {
        	
            if (cand.getKeyActivation() != null) {
            	
                if (cand.getKeyActivation().equals(activationKey)) {
                    cand.setActived(true);
                    cand.setKeyActivation(null);
                    updateCandidat(cand);
                    try {
                        emailSender.send(cand.getUsername(), "Confirmation d'activation",
                        		"<html>"
                                		+ "<body>"	
                                		+"<div bgcolor=\"#0087b4\" style=\"Margin:0;padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;min-width:100%;background-color:#0087b4;background-image:none;background-repeat:repeat;background-position:top left;display:block\">"
                                	    +"<center style=\"width:100%;table-layout:fixed\"><div style=\"max-width:600px ;padding-top:40px padding-bottom:40px;\">"
                                	    + "  <table align=\"center\" style=\"border-spacing:0;Margin:20px auto;width:95%;max-width:600px padding-top:20px;\"><tbody><tr>"
                                	    + "<td style=\"padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;background-color:white;background-image:none;background-repeat:repeat;background-position:top left;border-radius:8px 8px 8px 8px\">"
                                        +"<table width=\"100%\" style=\"border-spacing:0\">"
                                        		+ "<tbody><tr><td style=\"padding-top:10px;padding-bottom:10px;padding-right:0;padding-left:0;text-align:center\">"
                                            
                                                   +" <div style=\"padding-top:0;padding-bottom:0;padding-right:45px;padding-left:45px\">"
                                                       
                                                      + "<br> <p style=\"Margin:0;line-height:1.5;font-size:24px;Margin-bottom:20px;color:#0087b4;font-family:arial,sans-serif;text-align:center\">Bonjour "+cand.getNom() +", bienvenue sur le site CapRecrute!</p>"
                                                        +" <p style=\"Margin:0;font-family:arial,sans-serif;line-height:1.5;Margin-bottom:10px;color:#000000;font-size:16px;text-align:center\">Votre compte est activé. Il vous sera désormais plus facile que jamais de recevoir nos dernieres offres d'emploi.<br>"
                                                     
                                                          +"<br>"
                                                          + "<img src=\"http://www.naswdc.org/images/joblink/peopleShot.jpg\" alt=\"emploi \" width=\"300\" style=\"border-width:0;width:100%;max-width:300px\"></img>"
                                                          +" </div> <a href=\"\" style=\"color:#ee6a56;text-decoration:underline\"></a>"
                                                   
                                                         +"<div style=\"padding-top:0;padding-bottom:0;padding-right:45px;padding-left:45px\"><br>"
                                                      
                                                         +"<p style=\"Margin:0;font-family:arial,sans-serif;line-height:1.5;Margin-bottom:10px;color:#000000;font-size:16px;text-align:center\">La chance est avec vous.<br>"
                                                         +" À bientôt,<br><br>"
                                                         +"<span style=\"font-style:italic\">L'équipe de CapValue Votre partenir de recrutement.</span></p>"
                                                         +"</div><div style=\"border-width:1px;border-style:dashed;border-color:#000000\"></div>"
                                                         +"<div style=\"padding-top:0;padding-bottom:0;padding-right:45px;padding-left:45px\">"
                                                         +"<br><p style=\"Margin:0;font-family:arial,sans-serif;line-height:1.5;font-size:10px;color:#999999;text-align:center\">Rejoignez-nous sur</p><br>"
                                                         +"</div>"
                                                        
                                                    
                                                    +"<table width=\"187px\" style=\"margin-top:0;margin-bottom:0;margin-right:auto;margin-left:auto;border-spacing:0;display:inline-table\"><tbody><tr>"
                                                        
                                                            +" <td style=\"padding-top:0;padding-bottom:0;padding-right:0;padding-left:0\"><a href=\"\" style=\"color:#ee6a56;text-decoration:underline\" target=\"_blank\" ><img src=\"https://ci5.googleusercontent.com/proxy/P0NtRoz5mS6UQHr3GTql31TODALhyoiqst6F2_JpD9lmYMi1ULBhOvrngQ-qn4s2K_BKyKOvpcGd3wl8pDRgCPsNGsJOEvK1iKSdkgWNGKQutVmJ5S-RmY_qts_-DmWwdwfo=s0-d-e1-ft#https://happn-mail.s3-eu-west-1.amazonaws.com/mails/welcome/facebook_mail.jpg\" style=\"width:32px;margin-right:21px;border-width:0;max-width:600px\" ></a></td>"
                                                            +"<td style=\"padding-top:0;padding-bottom:0;padding-right:0;padding-left:0\"><a href=\"\" style=\"color:#ee6a56;text-decoration:underline\" target=\"_blank\" ><img src=\"https://ci4.googleusercontent.com/proxy/VVAGNvK8qY0mx0dzp1U5nbd968Bo74uGmiF2map5z78S_Ij6Sqv4D4aEeslHkXBN8vJGNGRC_DVMZfEOZBinkxYfj4ue6xKDfMN8oXU_MKIklLIqMj68eVxrR_ufMCMARjA=s0-d-e1-ft#https://happn-mail.s3-eu-west-1.amazonaws.com/mails/welcome/twitter_mail.jpg\" style=\"width:32px;margin-right:21px;border-width:0;max-width:600px\"></a></td>"
                                                            +"<td style=\"padding-top:0;padding-bottom:0;padding-right:0;padding-left:0\"><a href=\"\" style=\"color:#ee6a56;text-decoration:underline\" target=\"_blank\"><img src=\"https://ci5.googleusercontent.com/proxy/i4B2Zb8U3abMi7jDfBT61UuPWbDu4O9YlGeVWDgZaeVps5li1mQ2T8nLGGbWUu-viIrB_mL0eNssaQvkbjQqMsipSyvffH7a_Dz5L3KIN1wm07-k2AHft7gg_f2MXXZ_qTA=s0-d-e1-ft#https://happn-mail.s3-eu-west-1.amazonaws.com/mails/welcome/youtube_mail.jpg\"  style=\"width:32px;margin-right:21px;border-width:0;max-width:600px\"></a></td>"
                                                            +"</tr> </tbody></table> <br></td></tr> </tbody></table> </td> </tr> </tbody></table> </div></center><br>"
                                                            	
                          
          
                                		+ "</body>"
                                		+ "</html>"
                                		+ ""
                        		/*
                        		"<html>"
                                		+ "<body>"
                                		+ "<p style='font-size: 1.8rem;'>Bonjour  <b><i>"+cand.getNom()+"</i></b>,</p> "
                                		+ "<div style='background-color:#FDF8F4;font-size: .9rem;'>"
                                		+ "<p>Votre compte est  bien activé !</p>"
                                		+ "<p>À très bientôt.</p>"
                                		+ "</div>"
                                		+ "<p style='color:gray;font-size:18px;'><i>C@apRecrute<i></p>"
                                		+ "</body>"
                                		+ "</html>"
                                		+ ""*/
                                		);
                    } catch (MessagingException e) {
                        return "EMAIL_SERVICE_UNAVAILABLE";
                    }
                    return "ACCOUNT_ACTIVATED";
                } else {
                    return "ACTIVIATION_KEY_BAD_REQUEST";
                }
            } else {
                return "USERNAME_BAD_REQUEST";
            }
        } else {
            return "USERNAME_NOT_FOUND";
        }
	}

	@Override
	public Candidat getCandidatByUsername(String username) {
		// TODO Auto-generated method stub
		return candidatRepository.getCandidatByUsername(username);
	}

	@Override
	public List<Candidat> getCandidatPosulerByOffre(Long codeOffre) {
		List<Candidat>candidats=new ArrayList<Candidat>();
		List<String>usernamesCandidat=new ArrayList<String>();
		usernamesCandidat=postulerRepository.getCandidatPosulerByOffre(codeOffre);
		for(int i=0;i<usernamesCandidat.size();i++){
			Candidat c=findOneCandidat(usernamesCandidat.get(i));
			c.setPassword(null);
			c.setRoles(null);
			candidats.add(c);
		}
		return candidats;
	}

	@Override
	public String contacter(String username,String subject,String message) {
		boolean existException=false;
		try {
            emailSender.send("aggad.nabil@gmail.com", "Contact CapValue objet: "+subject,
            		"<html>"
            		+ "<body>"
            		+ message
            		+ "<br/>envoyer par <strong> "+ username+"</strong>"
            		+ "</body>"
            		+ "</html>"
            		+ "");
        } catch (MessagingException e) {
        	existException = true;
            e.printStackTrace();
        }
		 return existException ? "mailer_error" : "succes";
		
	}

	@Override
	public void updateNotNotifiedUsers(String username) {
	Candidat c=	candidatRepository.findOne(username);
	c.setNotification(true);
	candidatRepository.saveAndFlush(c);
		
	}
}
