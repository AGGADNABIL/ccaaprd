package org.capvalue.recrute.metierImpl;

import org.capvalue.recrute.conf.services;
import org.capvalue.recrute.conf.smtpEmailSender;
import org.capvalue.recrute.dao.CandidatRepository;
import org.capvalue.recrute.dao.OffreRepository;
import org.capvalue.recrute.dao.PostulerRepository;
import org.capvalue.recrute.domaine.Offre;
import org.capvalue.recrute.domaine.Postuler;
import org.capvalue.recrute.metier.IPostulerMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class PostulerMetierImpl implements IPostulerMetier {
	@Autowired
	private PostulerRepository postulerRepository;
	@Autowired
	private smtpEmailSender emailSender;
	@Autowired
	CandidatRepository candidatRepository;
	@Autowired
	OffreRepository offreRepository;

	@Override
	public Postuler savePostuler(Postuler postuler) {
		Offre o=offreRepository.findOne(postuler.getOffre().getCodeOffre());
		postuler.setCandidat(candidatRepository.getCandidatByUsername(services.username));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calobj = Calendar.getInstance();
		df.format(calobj.getTime());
		postuler.setDatePostulation(calobj.getTime());
		
		try {
			String body="<html><body>"
            		+"<div bgcolor=\"#0087b4\" style=\"Margin:0;padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;min-width:100%;background-color:#0087b4;background-image:none;background-repeat:repeat;background-position:top left;display:block\">"
            	    +"<center style=\"width:100%;table-layout:fixed\"><div style=\"max-width:600px ;padding:40px;padding-bottom:40px;\">"
            	    + "  <table align=\"center\" style=\"border-spacing:0;Margin:20px auto;width:95%;max-width:600px padding-top:20px;\"><tbody><tr>"
            	    + "<td style=\"padding-top:0;padding-bottom:0;padding-right:0;padding-left:0;background-color:white;background-image:none;background-repeat:repeat;background-position:top left;border-radius:8px 8px 8px 8px\">"
                    +"<table width=\"100%\" style=\"border-spacing:0\">"
                    		+ "<tbody><tr><td style=\"padding-top:10px;padding-bottom:10px;padding-right:0;padding-left:0;text-align:center\">"
                               +" <div style=\"padding-top:0;padding-bottom:0;padding-right:45px;padding-left:45px\">"  
                                  + "<br> <p style=\"Margin:0;line-height:1.5;font-size:24px;Margin-bottom:20px;color:#0087b4;font-family:arial,sans-serif;text-align:center\">Bonjour "+postuler.getCandidat().getPrenom() +" ,!</p>"
                                 
                                      +"<br>"
                                      + "Nous avons bien reçu votre candidature au poste de <b>"+ o.getTitre()+"</b><br>"
                                      + " Nous reviendrons vers vous pour une"
                                      + " réponse dans les jours qui viennent"
                                      +" </div> <a href=\"\" style=\"color:#ee6a56;text-decoration:underline\"></a>"
                               
                                     +"<div style=\"padding-top:0;padding-bottom:0;padding-right:45px;padding-left:45px\"><br>"
                                  
                                     +"<p style=\"Margin:0;font-family:arial,sans-serif;line-height:1.5;Margin-bottom:10px;color:#000000;font-size:16px;text-align:center\">Bonne chance.<br>"
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
                                		+ "";
			
			emailSender.send(postuler.getCandidat().getUsername(), 
					"Votre Candidature sur notre site CapValue-Recrutement", body);
			
		
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}
		
		
		
		
		return postulerRepository.save(postuler);
	}

	@Override
	public void deleteListPustulerByOffre(Long codeOffre) {
		// TODO Auto-generated method stub
		postulerRepository.deleteLitPustulerByOffre(codeOffre);
	}

	@Override
	public void deleteListPustulerByCandidat(String username) {
		// TODO Auto-generated method stub
		postulerRepository.deleteListPostulerByCandidat(username);
	}

	@Override
	public int getNumberPosulerByOffre(Long codeOffre) {
		// TODO Auto-generated method stub
		return postulerRepository.getNumberPosulerByOffre(codeOffre);
	}

	@Override
	public Postuler getPosulerByOffre(String username, Long codeOffre) {
		// TODO Auto-generated method stub
		return postulerRepository.getPosulerByOffre(username,codeOffre).get(0);	}

	@Override
	public List<Postuler> getPosulerByCandidat(String username) {
		// TODO Auto-generated method stub
		return postulerRepository.getPosulerByCandidat(username);	
	}

//	@Override
//	public Postuler getPosulerOneByCandidat(String username) {
//		// TODO Auto-generated method stub
//		return  postulerRepository.getPosulerOneByCandidat(username);	
//	}

	@Override
	public List<Postuler> getOffre(Long codeOffre) {
		// TODO Auto-generated method stub
		return postulerRepository.getOffre(codeOffre);	
	}
	
	@Override
	public List<Postuler> getOffresByCode(Long codeOffre) {
		// TODO Auto-generated method stub
		return postulerRepository.getOffresByCode(codeOffre);	
	}
}
