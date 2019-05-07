package org.capvalue.recrute.metierImpl;

import java.util.Collection;
import java.util.UUID;

import javax.mail.MessagingException;

import org.capvalue.recrute.conf.smtpEmailSender;
import org.capvalue.recrute.dao.UserRepository;
import org.capvalue.recrute.domaine.Competence;
import org.capvalue.recrute.domaine.User;
import org.capvalue.recrute.metier.IUserMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserMetierImpl implements IUserMetier {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private smtpEmailSender emailSender;
	
	
	@Override
	public User showUser(String username) {
		// TODO Auto-generated method stub
		return userRepository.findOne(username);
	}
	@Override
	public User getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.getUserByUsername(username);
	}
	
	@Override
	public Collection<User> getUsers() {
		return userRepository.allAdmin();
	}
	
	public void changeEtatUser(String username, boolean etat) {
		User user=userRepository.findOne(username);
		user.setActived(etat);
		userRepository.saveAndFlush(user);
	}
	
	@Override
	public User updatePasswrd(String username, User user) {
		// TODO Auto-generated method stub
		User newUser=userRepository.findOne(username);
		newUser.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));
		return userRepository.saveAndFlush(newUser);
	}

	/*public User addUser (User user){
		return userRepository.save(user);
	}*/
	
	@Override
    /**
     * If the user has forgotten his password, generate a new one for him
     * and send it to his email
     * The user can use this password to login to the application
     * and change it later
     *
     * @param username : the username of the user
     */
    public String forgotPassword(String email) {
        User user = userRepository.getUserByUsername(email);

        boolean mailException = false;
        String verificationKey;

        if (user == null)
            return "EMAIL_BAD_REQUEST";
        else {
            // A unique key used to verify the forgot password request
            verificationKey = UUID.randomUUID().toString();
            user.setKeyActivation(verificationKey);
            try {
            	String body="<html>"
                		+ "<body>"
            			
                		+"<div bgcolor=\"#0087b4\" style=\"Margin:0;padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;min-width:100%;background-color:#0087b4;background-image:none;background-repeat:repeat;background-position:top left;display:block\">"
                	    +"<center style=\"width:100%;table-layout:fixed\"><div style=\"max-width:600px ;padding-top:40px padding-bottom:40px;\">"
                	    + "  <table align=\"center\" style=\"border-spacing:0;Margin:20px auto;width:95%;max-width:600px padding-top:20px;\"><tbody><tr>"
                	    + "<td style=\"padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;background-color:white;background-image:none;background-repeat:repeat;background-position:top left;border-radius:8px 8px 8px 8px\">"
                        +"<table width=\"100%\" style=\"border-spacing:0\">"
                        		+ "<tbody><tr><td style=\"padding-top:10px;padding-bottom:10px;padding-right:0;padding-left:0;text-align:center\">"
                            
                                   +" <div style=\"padding-top:0;padding-bottom:0;padding-right:45px;padding-left:45px\">"
                                       
                                      + "<br> <p style=\"Margin:0;line-height:1.5;font-size:24px;Margin-bottom:20px;color:#0087b4;font-family:arial,sans-serif;text-align:center\">Bonjour "+ user.getNom() +", bienvenue sur le site CapRecrute!</p>"
                                        +" <p style=\"Margin:0;font-family:arial,sans-serif;line-height:1.5;Margin-bottom:10px;color:#000000;font-size:16px;text-align:center\">"
                                        + ""
                                        
                                        
                                        + "Nous avons reçu une demande de réinitialisation de votre mot de passe sur le site du CapRecrute.<br/>"

                                		+ "Cliquer sur le lien ci-dessous pour creer un nouveau mot de passe :<br><br>"
                                		+ "<a href='http://localhost:8080/app/index.html#/app/forgotPassword?"
                                		+ "email=" + email
                                		+ "&verificationKey=" + verificationKey
                                		+ "'>réinitialiser votre mot de passe</a>"
                                     
                                          +"<br>"
                                          +" </div> <a href=\"\" style=\"color:#ee6a56;text-decoration:underline\"></a>"
                                   
                                         +"<div style=\"padding-top:0;padding-bottom:0;padding-right:45px;padding-left:45px\"><br>"
                                      
                                         
                                         +" À bientôt,<br><br>"
                                         +"<span style=\"font-style:italic\">L'équipe de CapValue Votre partenir de recrutement.</span></p><br/>"
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
                		+ "";
            	
            	
                              /*"<strong>Bonjour " + user.getNom() + ",</strong><br><br>"
                                + "Nous avons reçu une demande de réinitialisation de votre mot de passe sur le site du CapRecrute.<br/>"
                               
                                + "Cliquer sur le lien ci-dessous pour creer un nouveau mot de passe :<br><br>"
                                + "<a href='http://localhost:8080/app/index.html#/app/forgotPassword?"
                                + "email=" + email
                                + "&verificationKey=" + verificationKey
                                + "'>réinitialiser votre mot de passe</a>"
                                + "<br><br>Si vous n'avez pas demendé à changer votre mot de passe, veuillez ignorer cet e-mail. (Ps: Votre mot de passe restera le même)."
                                + "bien cordialement"
                                + "<br><br><br>À très bientôt."
                                */            	
            	
                emailSender.send(email, "Demande de réinitialisation de votre mot de passe",body
                        );
            } catch (MessagingException e) {
                mailException = true;
                e.printStackTrace();
            }

        }

        if (!mailException) {
            userRepository.forgotPassword(verificationKey, email);
        }

        return mailException ? "MAIL_SERVER_ERROR" : "PASSWORD_REQUESTED_SUCCESSFULY";

    }

    /**
     * Send a link to the user to set his new password after account recovery
     *
     * @param email
     * @param verificationKey this key is used to verify weither the user request is real or no
     * @return status of the change password request
     */
    public String recoverAccount(String email, String verificationKey) {
        User user = userRepository.getUserByUsername(email);

        if (user == null)
            return "EMAIL_BAD_REQUEST";
        if (user.getKeyActivation()!= null && user.getKeyActivation().equals(verificationKey)) {
            return "VERIFICATION_KEY_SUCCEFUL";
        } else {
            return "VERIFICATION_KEY_BAD_REQUEST";
        }
    }

    /**
     * Change the user password with a new one after account recovery request
     *
     * @param email
     * @param password
     * @param verificationKey this key is used to verify weither the user request is real or no
     * @return status of the change password request
     */
    public String recoverPassword(String email, String verificationKey, String password) {
        User user = userRepository.getUserByUsername(email);

        if (user == null)
            return "EMAIL_BAD_REQUEST";
        if (user.getKeyActivation() != null) {
            if (user.getKeyActivation().equals(verificationKey)) {
                user.setPassword(new BCryptPasswordEncoder(12).encode(password));
                user.setKeyActivation(null);
                userRepository.saveAndFlush(user);
                return "PASSWORD_CHANGED_SUCCEFULLY";
            } else {
                return "VERIFICATION_KEY_BAD_REQUEST";
            }
        } else {
            return "VERIFICATION_KEY_BAD_REQUEST";
        }

    }

	@Override
	public void removeUser(String username) {
		 User user =userRepository.findOne(username);
		 userRepository.delete(user);
		//userBaseRepository
		
	}
	
}
