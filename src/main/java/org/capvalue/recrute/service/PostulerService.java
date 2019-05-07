package org.capvalue.recrute.service;

import org.capvalue.recrute.domaine.Postuler;
import org.capvalue.recrute.metier.IPostulerMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/postulers")
public class PostulerService {
	@Autowired
	IPostulerMetier postulerMetier;
	
	//@Secured("ROLE_CANDIDAT")
	@RequestMapping(value="/postulers",method=RequestMethod.POST)
	public Postuler savePostuler(@RequestBody Postuler postuler){
		return postulerMetier.savePostuler(postuler);
	}
	@RequestMapping(value="/getPosulerByOffre/{username}/{codeOffre}",method = RequestMethod.GET)
	public Postuler getPosulerByOffre(@PathVariable(value="username") String username,@PathVariable(value="codeOffre")Long codeOffre){
		// TODO Auto-generated method stub
		return postulerMetier.getPosulerByOffre(username, codeOffre);
		}
	@RequestMapping(value="/getPosulerByCandidat/{username}/candidats",method = RequestMethod.GET)
	public List<Postuler> getPosulerByCandidat(@PathVariable(value="username") String username){
		// TODO Auto-generated method stub
		return postulerMetier.getPosulerByCandidat(username);
		}
//	@RequestMapping(value="/getPosulerOneByCandidat/{username}/candidats",method = RequestMethod.GET)
//	public Postuler getPosulerOneByCandidat(@PathVariable(value="username") String username){
//		// TODO Auto-generated method stub
//		return postulerMetier.getPosulerOneByCandidat(username);
//		}
	@RequestMapping(value="/getAllOffre/{codeOffre}",method = RequestMethod.GET)
	public List<Postuler> getOffre(@PathVariable(value="codeOffre")Long codeOffre){
		// TODO Auto-generated method stub
		return postulerMetier.getOffre(codeOffre);
		}
	@RequestMapping(value="/getAllOffres/{codeOffre}",method = RequestMethod.GET)
	public List<Postuler> getOffresByCode(@PathVariable(value="codeOffre")Long codeOffre){
		// TODO Auto-generated method stub
		return postulerMetier.getOffresByCode(codeOffre);
		}
	
}
