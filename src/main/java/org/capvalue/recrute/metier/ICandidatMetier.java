package org.capvalue.recrute.metier;

import org.capvalue.recrute.domaine.Candidat;

import java.util.List;

public interface ICandidatMetier {
	public String saveCandidat(Candidat candidat);
	public void deleteCandidat(String username);
	public Candidat updateCandidat(Candidat candidat);
	public Candidat findOneCandidat(String username);
	public String contactCandidat(String username,String message);
	public List<Candidat>getAllCandidat();
	public Candidat getCandidatById(Long codeCandidat);
	public String activateAccount(String username,String activationKey);
	public Candidat getCandidatByUsername(String username);
	public List<Candidat>getCandidatPosulerByOffre(Long codeOffre);
	public String contacter(String username,String subject,String message);
	public void updateNotNotifiedUsers(String username);

}
