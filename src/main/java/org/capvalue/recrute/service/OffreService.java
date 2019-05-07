package org.capvalue.recrute.service;

import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.capvalue.recrute.conf.services;
import org.capvalue.recrute.conf.smtpEmailSender;
import org.capvalue.recrute.dao.SubscribeRepository;
import org.capvalue.recrute.domaine.Offre;
import org.capvalue.recrute.domaine.OffreCompetence;
import org.capvalue.recrute.domaine.Subscribe;
import org.capvalue.recrute.domaine.User;
import org.capvalue.recrute.metier.ICandidatMetier;
import org.capvalue.recrute.metier.IUserMetier;
import org.capvalue.recrute.metier.OffreMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableAsync
// @EnableScheduling
@RestController
@Service
public class OffreService {

	@Autowired
	private OffreMetier offreMetier;
	@Autowired
	private IUserMetier userMetier;
//	@Autowired
//	private ICandidatMetier candidatMetier;
	@Autowired
	private SubscribeRepository subscribeRepository;
	@Autowired
	private smtpEmailSender emailSender;

	public static Offre offreCurrent;

	/*
	 * @Secured("ROLE_ADMIN")
	 * 
	 * @RequestMapping(value = "/offre/offresssss", method = RequestMethod.POST)
	 * public Offre saveOffre(@RequestBody Offre offre) { System.out.println(
	 * "Saving Offer service started..."); return offreMetier.saveOffre(offre);
	 * }
	 */

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/offre/offres", method = RequestMethod.POST)
	public Offre save(@RequestBody Offre offre) {
		System.out.println("Savi Offer service started...");
		offreCurrent = offreMetier.saveOffre(offre);
		// offreMetier.sauver();
		return offreCurrent;
	}

	//@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/offre/offres", method = RequestMethod.PUT)
	public Offre updateOffre(@RequestBody Offre offre) {
		System.out.println("b1");
		return offreMetier.updateOffre(offre);
		
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/offre/changeEtatOffre/{id}/{etat}", method = RequestMethod.PUT)
	public int changeEtatOffre(@PathVariable("id") Long id, @PathVariable("etat") boolean etat) {
		offreMetier.changeEtatOffre(id, etat);
		return 1;
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/offre/offres/{id}", method = RequestMethod.DELETE)
	public void deleteOffre(@PathVariable Long id) {
		offreMetier.deleteOffre(id);
	}

	
	@RequestMapping(value = "/offre/offres", method = RequestMethod.GET)
	public List<Offre> findAllOffre(ServletRequest req) {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession(false);
		if (session == null || !request.isRequestedSessionIdValid()) {
			services.username = null;
		} else {
			//services.username = services.username;
			System.out.println("la session est valide");
		}
		return offreMetier.findAllOffre();
	}
   
	@RequestMapping(value = "/offre/offres/{id}", method = RequestMethod.GET)
	public Offre findOneOffre(@PathVariable Long id) {
		return offreMetier.findOneOffre(id);
	}

	// @RequestMapping(value="/offres/{id}",method=RequestMethod.GET)
	public List<Offre> RechercheOffres() {
		return offreMetier.RechercheOffres();
	}

	@RequestMapping(value = "/offresSearch", method = RequestMethod.GET)
	public List<Offre> offresSearch(@RequestParam String typeContrat, @RequestParam String ville,
			@RequestParam String competence) {
		System.out.println("SearchCompetence : " + competence);
		System.out.println("SearchVille : " + ville);
		return offreMetier.offresSearch(typeContrat, ville, competence);
	}

	@RequestMapping(value = "/offre/offresByCompetence/{id}", method = RequestMethod.GET)
	public List<Offre> OffreByCompetence(@PathVariable Long id) {
		return offreMetier.OffreByCompetence(id);
	}

	@RequestMapping(value = "/offre/offresByVille/{id}")
	public List<Offre> OffreByVille(@PathVariable Long id) {
		return offreMetier.OffreByVille(id);
	}

	@RequestMapping(value = "/offre/offresByContract/{id}", method = RequestMethod.GET)
	public List<Offre> OffreByTypeContrat(@PathVariable Long id) {
		return offreMetier.OffreByTypeContrat(id);
	}
	
	@RequestMapping(value = "/offre/offres/ville", method = RequestMethod.GET)
	public List<Offre> villeByOffre() {
		return offreMetier.findAllVilleByOffre();
	}
	
	@RequestMapping(value = "/offre/offres/contrat", method = RequestMethod.GET)
	public List<Offre> contratByOffre() {
		return offreMetier.findAllContratByOffre();
	}
	

	@Secured(value = { "ROLE_CANDIDAT", "ROLE_ADMIN" })
	@RequestMapping(value = "/offre/offres/{username}/offres", method = RequestMethod.GET)
	public List<Offre> OffresByUserNameCandidat(@PathVariable String username) {
		User u = userMetier.getUserByUsername(services.username);
		if (u.getTypeUser().equals("UC"))
			username = services.username;
		return offreMetier.offresByIdCandidat(username);
	}

	@RequestMapping(value = "/offre/offres/incrementNbVueOffre/{codeOffre}", method = RequestMethod.GET)
	public void incrementNbVueOffre(@PathVariable("codeOffre") Long codeOffre) {
		offreMetier.incrementNbVue(codeOffre);
	}

	@Scheduled(cron = "0 0 20 ? * SUN")
	public void sendNotification() {
		Calendar cal= Calendar.getInstance(),cal1= Calendar.getInstance(),cal2= Calendar.getInstance();
		System.out.println("demarrage de l'application");
		List<Offre> offres = offreMetier.offresNotSended();
		System.out.println("offres not Sended :" + offres.size());
		List<Subscribe> subscribes = subscribeRepository.findAllNotDisabled();
		List<Offre> offresNotExpired=offreMetier.findOffreNotExpired();
		if(offres.size()>0)
		for (Subscribe s : subscribes) {
			try {

				String body = "<html>" + "<head>" 
							+ "	<style type=\"text/css\">"
							+ "table {border-bottom: 1px dotted #ccc;background-color: white;position: relative;font-family: Helvetica, Arial, sans-serif;width: 450px;font-size: 12px;margin-left:auto;margin-right:auto; padding-bottom: 40px; }"
							+ "#conteneur {background-color: #f3f4f4;width: 500px; max-width: 500px;padding-top: 40px;padding-bottom: 40px;padding-left:auto; padding-right: auto;position:bsolute;margin: 30px 40px;}"
							+ "h4 a{color: #037cd9;} tr , td {	padding-left: 20px; } #mission {color: #585858;font-size: 20px;} #foot {padding-top: 40px;	font-family: Helvetica, Arial, sans-serif;	font-size: 12px;width: 400px;} #entete {width:400px;padding-left: 10px ;padding-right: 10px;}"
							+ "</style>"
						    + "</head>" + "<body>"
						    + " <div id=\"conteneur\" >"
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
						    			
						    	    body+="<tr><td><h4><a href=\"http://localhost:8080/app/index.html#/app/index.html?#/app/job/"+(o.getTitre()).replaceAll("\\s+","-")+"/"+o.getCodeOffre()+"\">"+o.getTitre()+" →</a></h4>"
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
						    			+ "<td><div id=\"foot\" style=\"padding-top: 40px;font-family: Helvetica, Arial, sans-serif;font-size: 12px;width: 450px; \">Vous recevez cet email car vous êtes inscrits à <a href=\"\">Capvalue</a> avec <a href=\"\" style=\" color:blue;\">"+s.getEmail()+"</a> .Si vous ne souhaitez plus recevoir nos email, merci de"
						    			+ "vous <a href=\"http://localhost:8080/subscribes/desinscrire/"+s.getEmail()+"/\">désinscrire</a>- © 2016 Capvalue, All rights reserved </div>"
						    			+ "<br/><div><a href=\"#\" ><img src=\"https://ci4.googleusercontent.com/proxy/0dRGAs98vbi_srvhlZEbWfPNKXVUT6hWT92956BoZX-LZgokDiAUzQYvHdoEbSQh4OkeF8gMHE0W51CjZcMos76I8ZlcEPcEVrC_I7W5fmLwWyYQdN_VI9kjC4xfj2iQhNZVM0s9dqcfTkL8_w=s0-d-e1-ft#http://beinnetwork.com/fr/wp-content/plugins/myMail/assets/img/share/share_linkedin.png\" >"
						    			+ "</a> <a href=\"#\" >"
						    			+ "<img src=\"https://ci4.googleusercontent.com/proxy/ZqMkRbc0C9rgQuKWJl3lW5ZpySIhYMawZp_bpFtVSJchluj5sSCyD2Mn1If1pzAQQxtr0bvuQO3Fa_O5kV7f9qqYGnqY_WqcA5_9QtVH7O07GJmI2IkPGF0gmvQqyhBSeAXQUb6XvkCfdFw=s0-d-e1-ft#http://beinnetwork.com/fr/wp-content/plugins/myMail/assets/img/share/share_google.png\"> "
						    			+ "</a> <a href=\"#\"><img src=\"https://ci3.googleusercontent.com/proxy/JvwwmuJAbQNNLq1xVYyal99czApmPzoYEbNf__aSqvKX0zZ5z7ODa1R76oMKC4gP9Zm_Z5dXf4mO51yp-5wK2XK_QHCo-whXjYvlfLNxmhfT-5pZznv0S1Ygsz4MzMLcqFt212cU0D39zcHFZg=s0-d-e1-ft#http://beinnetwork.com/fr/wp-content/plugins/myMail/assets/img/share/share_facebook.png\" "
						    			+ "></a> <a href=\"#\" ><img src=\"https://ci5.googleusercontent.com/proxy/A1Jjceev-T__ZeFpvF_w-uD24qC34Il6s1GMw3fIUAciwhUzqYo3R1JT11lh6kWikAIX0fs1PjvN5Y7TQ1rCgHykkK0f72XwrpUfp_voxOc_PWZiz31cAisSRm2KfRagpC2633V5fyj2CpZJ=s0-d-e1-ft#http://beinnetwork.com/fr/wp-content/plugins/myMail/assets/img/share/share_twitter.png\""
						    			+ "></a></div></td></tr></tbody></table></div>"  			
						
						+ "</body>" 
				   		+ "</html>";
				emailSender.send(s.getEmail(), "Nouvelles offres de la semaine", body);

						for (Offre o : offres) {
							o.setEmailSended(true);
							offreMetier.updateOffre(o);
						}

			} catch (MessagingException e) {
			}
		}
	}

}
