package org.capvalue.recrute.metierImpl;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;

import org.capvalue.recrute.conf.services;
import org.capvalue.recrute.conf.smtpEmailSender;
import org.capvalue.recrute.dao.CandidatRepository;
import org.capvalue.recrute.dao.OffreCompetenceRepository;
import org.capvalue.recrute.dao.OffreRepository;
import org.capvalue.recrute.dao.TypeContratRepository;
import org.capvalue.recrute.dao.UserRepository;
import org.capvalue.recrute.domaine.Candidat;
import org.capvalue.recrute.domaine.Offre;
import org.capvalue.recrute.domaine.OffreCompetence;
import org.capvalue.recrute.domaine.User;
import org.capvalue.recrute.metier.IPostulerMetier;
import org.capvalue.recrute.metier.IVilleMetier;
import org.capvalue.recrute.metier.OffreMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OffreMetierImpl implements OffreMetier {

	@Autowired
	OffreRepository offreRepository;
	@Autowired
	OffreCompetenceRepository offreCompetenceRepository;
	@Autowired
	TypeContratRepository contratRepository;
	@Autowired
	IVilleMetier villeMetier;
	@Autowired
	IPostulerMetier postulerMetier;

//	@Autowired
//	private SubscribeRepository subscribeRepository;
	@Autowired
	private smtpEmailSender emailSender; 
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CandidatRepository candidatRepository;
	
	public void deleteOffre(Long id) {
		postulerMetier.deleteListPustulerByOffre(id);
		offreRepository.delete(id);
	}

	public List<Offre> findOffreNotExpired(){
		return offreRepository.findNotExpired();
	}
	public List<Offre> findAllOffre() {
		List<Offre>offres=offreRepository.findAll();
		List<Offre>offres1 = new ArrayList<Offre>();
		if(services.username !=null && userRepository.getUserByUsername(services.username).getTypeUser().equals("UA")){
			for(Offre o:offres){
				o.setNombrePostulant(postulerMetier.getNumberPosulerByOffre(o.getCodeOffre()));
				offres1.add(o);
			}
		return offres1;
		}else{
			for(Offre o:offres){
				o.setNombrePostulant(postulerMetier.getNumberPosulerByOffre(o.getCodeOffre()));
				Calendar cal = Calendar.getInstance();
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(o.getDatePublication());
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(o.getDateExpiration());
				if(cal1.compareTo(cal) * cal.compareTo(cal2) >0){
					offres1.add(o);
				}
			}
			return offres1;
		}
	}

	public Offre findOneOffre(Long id) {
		return offreRepository.findOne(id);
	}
    
	

	//@Scheduled(fixedDelay = 3*60*1000)  //initialDelay = 1000000, fixedDelay = 604800000
	//@Transactional
	@Scheduled(cron = "0 0 10,14,18 * * ?")
	public void sauver(){
		
		List<Offre> offres=offreRepository.offresByStat();
		System.out.println("taille des offres :"+offres.size());
		//List<Subscribe>subscribes=subscribeRepository.findAll();
		//selectionner les candidat qui vont recevoir les email 
		Calendar cal= Calendar.getInstance(),cal1= Calendar.getInstance(),cal2= Calendar.getInstance();
		List<Candidat>users=candidatRepository.getAllByReceiver();
		System.out.println("nombre de users :"+users.size());
		List<Offre> offresNotExpired=offreRepository.findNotExpired();
		if (offres.size()>0)
			for(User s:users){
				try {
				
					String body = "<html>" + "<head>" 
							+ "	<style type=\"text/css\">"
							+ "table {border-bottom: 1px dotted #ccc;background-color: white;position: relative;font-family: Helvetica, Arial, sans-serif;width: 450px;font-size: 12px;margin-left:auto;margin-right:auto; padding-bottom: 40px; }"
							+ "#conteneur {background-color: #f3f4f4;width: 500px; max-width: 500px;padding-top: 40px;padding-bottom: 40px;padding-left:auto; padding-right: auto;position:bsolute;margin: 30px 40px;}"
							+ "h4 a{color: #037cd9;} tr , td {	padding-left: 20px; } #mission {color: #585858;font-size: 20px;} #foot {padding-top: 40px;	font-family: Helvetica, Arial, sans-serif;	font-size: 12px;width: 400px;} #entete {width:400px;padding-left: 10px ;padding-right: 10px;}"
							+ "</style>"
						    + "</head>" + "<body>"
						    + " <div id=\"conteneur\" >"
						    + "<p style='font-size: 16px;'>Bonjour "+s.getPrenom()+" ,</p> "
						    + "<table><tbody><tr><td><img src=\"http://www.supevents.info/fr/images/resized/images/stories/slideshow/sl-1_665_198.jpg\" style=\"width:400px;width:400px; height: 130px;border-width: 6px;border-style: solid; border-color: #C0C0C0;\"></td>"
						    + "</tr><td><p id=\"entete\">Et oui ! même quand vous êtes en vacances, nous continuons à vous accompagner et à vous proposer de nouveaux projets ! "
						    + "Postulez aux missions qui correspondent le plus à votre profil et à vos recherches, nous nous occupons du reste :</p></td>";
						  
						    for (Offre o : offres) {
								 cal1.setTime(o.getDatePublication());
								 cal2.setTime(o.getDateExpiration());
								if (cal1.compareTo(cal) * cal.compareTo(cal2) > 0) {
							
						body+="<tr><td><h4><a href=\"http://localhost:8080/app/index.html#/app/job/"+(o.getTitre()).replaceAll("\\s+","-")+"/"+o.getCodeOffre()+"\">"+o.getTitre()+" →</a></h4>"
						    + "<div>Expérience : "+o.getNiveauExperience() +" ans au minimum. Compétences :";
						     
						for (OffreCompetence oc : o.getOffreCompetences()) {
						    	    
									  body+=oc.getCompetence().getTitre() +" , ";
								}
						    body+="</div>"
						    + "<div>Mission de "+ o.getDureeMission()+"  , basée à "+ o.getVille().getNomVille() +".</div>"
						    + "<br> Profil : "+o.getProfilRecherche()
						    + "</td></tr>";
						  
								}
							}
						    body+="</tbody></table>";
						    System.out.println("offresNotExpired :"+offresNotExpired.size());
						    if(offresNotExpired.size()>0){
						    body+="<table><tbody><tr><td><p id=\"mission\">	Autres missions en cours :</p></td></tr>";
						    		
						    	for(Offre o : offresNotExpired){
						    			
						    	    body+="<tr><td><h4><a href=\"http://localhost:8080/app/index.html#/app/job/"+(o.getTitre()).replaceAll("\\s+","-")+"/"+o.getCodeOffre()+"\">"+o.getTitre()+" →</a></h4>"
									    + "<div>Expérience : "+o.getNiveauExperience() +" ans au minimum. Compétences :";
									     for (OffreCompetence oc : o.getOffreCompetences()) {
												  body+=oc.getCompetence().getTitre() +" , ";
											}
									    body+="</div>"
									    + "<div>Mission de "+ o.getDureeMission()+"  , basée à "+ o.getVille().getNomVille() +".</div>"
									    + "<br> Profil : "+o.getProfilRecherche()
									    + "</td></tr>";
						    			
						    				}
						    			
						    	    body+= "</tbody></table>";
						    	}
						    	    		
						    	     body+= "<table><tbody><tr >"
						    			+ "<td><div id=\"foot\" style=\"padding-top: 40px;font-family: Helvetica, Arial, sans-serif;font-size: 12px;width: 450px; \">Vous recevez cet email car vous êtes inscrits à <a href=\"\">Capvalue</a> avec <a href=\"\" style=\" color:blue;\">"+s.getUsername()+"</a> .Si vous ne souhaitez plus recevoir nos email, merci de "
						    			+ "vous <a href=\"http://localhost:8080/candidats/users/desinscrire/"+s.getUsername()+"/\">désinscrire</a> - © 2016 Capvalue, All rights reserved </div>"
						    			+ "<br/><div><a href=\"#\" ><img src=\"https://ci4.googleusercontent.com/proxy/0dRGAs98vbi_srvhlZEbWfPNKXVUT6hWT92956BoZX-LZgokDiAUzQYvHdoEbSQh4OkeF8gMHE0W51CjZcMos76I8ZlcEPcEVrC_I7W5fmLwWyYQdN_VI9kjC4xfj2iQhNZVM0s9dqcfTkL8_w=s0-d-e1-ft#http://beinnetwork.com/fr/wp-content/plugins/myMail/assets/img/share/share_linkedin.png\" >"
						    			+ "</a> <a href=\"#\" >"
						    			+ "<img src=\"https://ci4.googleusercontent.com/proxy/ZqMkRbc0C9rgQuKWJl3lW5ZpySIhYMawZp_bpFtVSJchluj5sSCyD2Mn1If1pzAQQxtr0bvuQO3Fa_O5kV7f9qqYGnqY_WqcA5_9QtVH7O07GJmI2IkPGF0gmvQqyhBSeAXQUb6XvkCfdFw=s0-d-e1-ft#http://beinnetwork.com/fr/wp-content/plugins/myMail/assets/img/share/share_google.png\"> "
						    			+ "</a> <a href=\"#\"><img src=\"https://ci3.googleusercontent.com/proxy/JvwwmuJAbQNNLq1xVYyal99czApmPzoYEbNf__aSqvKX0zZ5z7ODa1R76oMKC4gP9Zm_Z5dXf4mO51yp-5wK2XK_QHCo-whXjYvlfLNxmhfT-5pZznv0S1Ygsz4MzMLcqFt212cU0D39zcHFZg=s0-d-e1-ft#http://beinnetwork.com/fr/wp-content/plugins/myMail/assets/img/share/share_facebook.png\" "
						    			+ "></a> <a href=\"#\" ><img src=\"https://ci5.googleusercontent.com/proxy/A1Jjceev-T__ZeFpvF_w-uD24qC34Il6s1GMw3fIUAciwhUzqYo3R1JT11lh6kWikAIX0fs1PjvN5Y7TQ1rCgHykkK0f72XwrpUfp_voxOc_PWZiz31cAisSRm2KfRagpC2633V5fyj2CpZJ=s0-d-e1-ft#http://beinnetwork.com/fr/wp-content/plugins/myMail/assets/img/share/share_twitter.png\""
						    			+ "></a></div></td></tr></tbody></table></div>"
						    	
						    			/*+ "<script type=\"text/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js\"></script>"
						    			+ "<script> "
						    			+ "function disableEmail(str){"
						    			+ "$.ajax({"
						    			+ "url : \"http://localhost:8080/app/index.html#/users/desinscrire/\"+str+\"/\","     //  /recruteCapvalue
						    			+ "type : \"PUT\","
						    			+ "success : function() {},"
						    			+ "error : function() {}"
						    			+ "});"
						    			+ "}"
						    			+ "</script>"*/
						+ "</body>" 
				   		+ "</html>";
						    	     	
	                emailSender.send(s.getUsername(), "Nouvelles offres du jour",body);
	                
	                for (Offre offre : offres) 
					{
	                	offre.setStat(false);
	                	offreRepository.saveAndFlush(offre);
					}   
				//}   
            } catch (MessagingException e) {
               // return null;
            }
	}
		 
		/*Offre offre =OffreService.offreCurrent;
		System.out.println("Sav new offer...");
		List<Subscribe>subscribes=subscribeRepository.findAll();
		
		offre.setVille(villeMetier.findOneVille(offre.getVille().getCodeVille()));
			offre.setTypeContrat(contratRepository.findOne(offre.getTypeContrat().getCodeTypeContrat()));
		if(offre.getVille()!= null && offre.getTypeContrat()!= null){
			for(Subscribe s:subscribes){
				try {
                    emailSender.send(s.getEmail(), "Nouvelles offres du jour",
                    		"<html>"
                            		+ "<body>"
                            		+ "<p style='font-size: 1.8rem;'>Bonjour  <b><i>"+s.getNom()+"</i></b>,</p> "
                            		+ "<div style='background-color:#FDF8F4;font-size: .9rem;'>"
                            		+ "<p>Vos offres d'emploi du jour sur CapRecrute</p>"
                            		+ "<p><a href='http://localhost:8080/app/index.html#/app/index.html?#/app/jobs-list'>"+offre.getTitre()+"</a></p>"
                            		+ "<p>À très bientôt.</p>"
                            		+ "</div>"
                            		+ "<p style='color:gray;font-size:18px;'><i>C@apRecrute<i></p>"
                            		+ "</body>"
                            		+ "</html>"
                            		+ "");
                } catch (MessagingException e) {
                   // return null;
                }
			}
			
			for(OffreCompetence offreCompetence : offre.getOffreCompetences()) {
				offreCompetence.setOffre(offre);
			}
			 offreRepository.save(offre);
		}		*/
		 
		 
	}
	
	@Transactional
	public Offre saveOffre(Offre offre) {
       
/*		System.out.println("Saving new offer...");
		List<Subscribe>subscribes=subscribeRepository.findAll();
		
		offre.setVille(villeMetier.findOneVille(offre.getVille().getCodeVille()));
			offre.setTypeContrat(contratRepository.findOne(offre.getTypeContrat().getCodeTypeContrat()));
		if(offre.getVille()!= null && offre.getTypeContrat()!= null){
			for(Subscribe s:subscribes){
				//senderMail( s,offre);
				try {
                    emailSender.send(s.getEmail(), "Nouvelles offres du jour",
                    		"<html>"
                            		+ "<body>"
                            		+ "<p style='font-size: 1.8rem;'>Bonjour  <b><i>"+s.getNom()+"</i></b>,</p> "
                            		+ "<div style='background-color:#FDF8F4;font-size: .9rem;'>"
                            		+ "<p>Vos offres d'emploi du jour sur CapRecrute</p>"
                            		+ "<p><a href='http://localhost:8080/app/index.html#/app/index.html?#/app/jobs-list'>"+offre.getTitre()+"</a></p>"
                            		+ "<p>À très bientôt.</p>"
                            		+ "</div>"
                            		+ "<p style='color:gray;font-size:18px;'><i>C@apRecrute<i></p>"
                            		+ "</body>"
                            		+ "</html>"
                            		+ "");
                } catch (MessagingException e) {
                    return null;
                }
			}*/
			
		Collection<OffreCompetence> offreCompetences=offre.getOffreCompetences();
		
			for(OffreCompetence offreCompetence : offre.getOffreCompetences()) {
				offreCompetence.setOffre(offre);
			}
			 return offreRepository.save(offre);
		
	}
	public Offre updateOffre(Offre offre) {
		
		Offre managedOffre=offreRepository.findOne(offre.getCodeOffre());
		for(Iterator<OffreCompetence> iter = managedOffre.getOffreCompetences().iterator(); iter.hasNext();){
			OffreCompetence managedOffreCompetence = iter.next();
			if(offre.getOffreCompetences().contains(managedOffreCompetence)){
				offre.getOffreCompetences().remove(managedOffreCompetence);
			}else{
//				System.out.println("ttttt :"+offre.getOffreCompetences().size());
//				if(offre.getOffreCompetences().size()>0){
//				iter.remove();
//				offreCompetenceRepository.delete(managedOffreCompetence.getCodeOffreCompetence());
//				offreCompetenceRepository.flush();
				}
			//}
		}
		
	
			for(OffreCompetence offreCompetence : offre.getOffreCompetences())
			{
				offreCompetence.setOffre(offre);
			}

		 offreRepository.saveAndFlush(offre);
		 List<OffreCompetence> offreCompetences =offreCompetenceRepository.findAll();
		 for (OffreCompetence offreCompetence : offreCompetences) {
			if(offreCompetence.getCompetence()==null){
				offreCompetenceRepository.delete(offreCompetence);
			}
		}
		return offre;
	}

	public void changeEtatOffre(Long id, boolean etat) {
		Offre offre=findOneOffre(id);
		offre.setEtat(etat);
		offreRepository.saveAndFlush(offre);
	}

	public List<Offre> RechercheOffres() {
		return null;
	}

	public List<Offre> OffreByCompetence(Long id) {
		List<Offre>offres=offreRepository.OffreByCompetence(id);
		List<Offre>offres1 = new ArrayList<Offre>();
		if(services.username !=null && userRepository.getUserByUsername(services.username).getTypeUser().equals("UA")){
			for(Offre o:offres){
				offres1.add(o);
			}
		return offres1;
		}else{
			for(Offre o:offres){
				Calendar cal = Calendar.getInstance();
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(o.getDatePublication());
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(o.getDateExpiration());
				if(cal1.compareTo(cal) * cal.compareTo(cal2) >0){
					offres1.add(o);
				}
			}
			return offres1;
		}
	}

	public List<Offre> OffreByVille(Long id) {
		List<Offre>offres=offreRepository.OffreByVille(id);
		List<Offre>offres1 = new ArrayList<Offre>();
		if(services.username !=null && userRepository.getUserByUsername(services.username).getTypeUser().equals("UA")){
			for(Offre o:offres){
				offres1.add(o);
			}
		return offres1;
		}else{
			for(Offre o:offres){
				Calendar cal = Calendar.getInstance();
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(o.getDatePublication());
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(o.getDateExpiration());
				if(cal1.compareTo(cal) * cal.compareTo(cal2) >0){
					offres1.add(o);
				}
			}
			return offres1;
		}
	}

	public List<Offre> OffreByTypeContrat(Long id) {
		List<Offre>offres=offreRepository.OffreByTypeContrat(id);
		List<Offre>offres1 = new ArrayList<Offre>();
		if(services.username !=null && userRepository.getUserByUsername(services.username).getTypeUser().equals("UA")){
			for(Offre o:offres){
				offres1.add(o);
			}
		return offres1;
		}else{
			for(Offre o:offres){
				Calendar cal = Calendar.getInstance();
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(o.getDatePublication());
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(o.getDateExpiration());
				if(cal1.compareTo(cal) * cal.compareTo(cal2) >0){
					offres1.add(o);
				}
			}
			return offres1;
		}
	}


	@Override
	public List<Offre> offresByIdCandidat(String username) {
		return offreRepository.offresByIdCandidat(username);
	}
    
	

	 @Override
		public List<Offre> offresSearch(String typeContrat, String ville, String competence) {
		 System.out.println("SearchCompetence : " + competence);
		 System.out.println("SearchVille : " + ville);
		 List<Offre>offres=offreRepository.offresSearch(typeContrat,ville, competence);
			List<Offre>offres1 = new ArrayList<Offre>();
			if(services.username !=null && userRepository.getUserByUsername(services.username).getTypeUser().equals("UA")){
				for(Offre o:offres){
					offres1.add(o);
				}
			return offres1;
			}else{
				for(Offre o:offres){
					Calendar cal = Calendar.getInstance();
					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(o.getDatePublication());
					Calendar cal2 = Calendar.getInstance();
					cal2.setTime(o.getDateExpiration());
					if(cal1.compareTo(cal) * cal.compareTo(cal2) >0){
						offres1.add(o);
					}
				}
				return offres1;
			}
		}

	@Override
	public void incrementNbVue(Long codeOffre) {
		// TODO Auto-generated method stub
		Offre o=findOneOffre(codeOffre);
		System.out.println("code offre :"+o.getCodeOffre());
		o.setNombreVue(o.getNombreVue()+1);
		offreRepository.saveAndFlush(o);
		//updateOffre(o);
	}

	@Override
	public List<Offre> offresNotSended() {
		
		return offreRepository.offresByIsNotSended();
	}

	@Override
	public List<Offre> findAllVilleByOffre() {
		return offreRepository.findAllNotDuplicateVille();
	}

	@Override
	public List<Offre> findAllContratByOffre() {
		return offreRepository.findAllNotDuplicateContrat();
	}
}
