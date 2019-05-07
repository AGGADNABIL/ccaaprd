package org.capvalue.recrute.metier;

import org.capvalue.recrute.domaine.Postuler;

import java.util.List;

public interface IPostulerMetier {
	public Postuler savePostuler(Postuler postuler);
	public void deleteListPustulerByOffre(Long codeOffre);
	public void deleteListPustulerByCandidat(String username);
	public int getNumberPosulerByOffre(Long codeOffre);
	
	public Postuler getPosulerByOffre(String username,Long codeOffre );
	public List<Postuler> getPosulerByCandidat(String username );
	//public List<Postuler> getPosulerOneByCandidat(String username );
	public List<Postuler> getOffre( Long codeOffre);
	public List<Postuler> getOffresByCode(Long codeOffre);

}
