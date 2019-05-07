package org.capvalue.recrute.metierImpl;

import org.capvalue.recrute.conf.smtpEmailSender;
import org.capvalue.recrute.dao.SubscribeRepository;
import org.capvalue.recrute.domaine.Subscribe;
import org.capvalue.recrute.metier.ISubscribeMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
@Service
public class SubscribeMetierImpl implements ISubscribeMetier {
	@Autowired
	private SubscribeRepository subscribeRepository;
	@Autowired
	private smtpEmailSender emailSender; 

	@Override
	public String saveSubscribe(Subscribe subscribe) {
		// TODO Auto-generated method stub
		subscribe.setActivated(true);
		Subscribe s=subscribe;
		boolean existException = false;
        if (subscribeRepository.getSubscribeByUsername(subscribe.getEmail()) != null){
        	System.out.println("username_already_exists");
        	return "username_already_exists";
        }

        try {
        	emailSender.send(subscribe.getEmail(), "Subscription-Email",
            		
            		"<html>"
            		+ "<body>"
            		
            		+"<div bgcolor=\"#0087b4\" style=\"Margin:0;padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;min-width:100%;background-color:#0087b4;background-image:none;background-repeat:repeat;background-position:top left;display:block\">"
            	    +"<center style=\"width:100%;table-layout:fixed\"><div style=\"max-width:600px ;padding-top:40px padding-bottom:40px;\">"
            	    + "  <table align=\"center\" style=\"border-spacing:0;Margin:20px auto;width:95%;max-width:600px padding-top:20px;\"><tbody><tr>"
            	    + "<td style=\"padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;background-color:white;background-image:none;background-repeat:repeat;background-position:top left;border-radius:8px 8px 8px 8px\">"
                    +"<table width=\"100%\" style=\"border-spacing:0\">"
                    		+ "<tbody><tr><td style=\"padding-top:10px;padding-bottom:10px;padding-right:0;padding-left:0;text-align:center\">"
                        
                               +" <div style=\"padding-top:0;padding-bottom:0;padding-right:45px;padding-left:45px\">"
                                   
                                  + "<br> <p style=\"Margin:0;line-height:1.5;font-size:24px;Margin-bottom:20px;color:#0087b4;font-family:arial,sans-serif;text-align:center\">Bonjour "+subscribe.getNom() +", bienvenue sur le site CapRecrute!</p>"
                                    +" <p style=\"Margin:0;font-family:arial,sans-serif;line-height:1.5;Margin-bottom:10px;color:#000000;font-size:16px;text-align:center\">Votre nouveau compte a été créé avec succès. Il vous sera désormais plus facile que jamais de recevoir nos dernieres offres d'emploi.<br>"
                                 
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

            	/*	
            		+ "<table  style=\"min-width: 332px; max-width: 600px;border: 1px solid #e0e0e0;border-bottom: 0; border-top-left-radius: 3px;border-top-right-radius: 3px;\">"
            		+ "<tr><td style='font-family: Roboto-Regular,Helvetica,Arial,sans-serif;font-size: 13px;color: #202020;line-height: 1.5;'>"
            		+ ""
            		+ "</td>"
            		+ "</tr>"
            		
            		+ "<div style='background-color:#FDF8F4;font-size: .9rem;'>"
            		+ "<p>Merci pour votre subscription vous etes la bienvenue !!</p>"
            		+ "</div>"
            		+ "<div >"
            		+ "<p style='color:gray;font-size:18px;'><i>C@pRecrute<i></p>"
            		+ "</div>"*/
            		+ "</body>"
            		+ "</html>"
            		+ "");
        } catch (MessagingException e) {
        	existException = true;
            e.printStackTrace();
        }

        if (!existException) {
            subscribeRepository.save(subscribe);
        }
        return existException ? "mailer_error" : "You have succefully subscribed";
	}

	@Override
	public Subscribe updateSubscribe(Subscribe subscribe) {
		// TODO Auto-generated method stub
		return subscribeRepository.saveAndFlush(subscribe);
	}

	@Override
	public List<Subscribe> getAllSubscribe() {
		// TODO Auto-generated method stub
		return subscribeRepository.findAll();
	}

	@Override
	public Subscribe getOneSubscribe(String username) {
		// TODO Auto-generated method stub
		return subscribeRepository.getSubscribeByUsername(username);
	}

	@Override
	public void activedSubscribe(String username) {
		Subscribe sbsc=getOneSubscribe(username);
		sbsc.setActivated(true);
		updateSubscribe(sbsc);
		
	}

	@Override
	public void desactivedSubscribe(String username) {
		Subscribe sbsc=getOneSubscribe(username);
		sbsc.setActivated(false);
		updateSubscribe(sbsc);
		
	}

	@Override
	public void deleteSubscribe(String username) {
		Subscribe sbsc=getOneSubscribe(username);
		subscribeRepository.delete(sbsc);
		
	}

	@Override
	public void desinscrireSubscribe(String username) {
		Subscribe s=subscribeRepository.findOne(username);
		s.setDesinscrire(false);
		subscribeRepository.saveAndFlush(s);
	}

}
