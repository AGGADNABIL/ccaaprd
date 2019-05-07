package org.capvalue.recrute.metierImpl;

import org.capvalue.recrute.dao.CompetenceRepository;
import org.capvalue.recrute.dao.GroupCompetenceRepository;
import org.capvalue.recrute.domaine.Competence;
import org.capvalue.recrute.domaine.GroupCompetence;
import org.capvalue.recrute.metier.IGroupCompetenceMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Created by Aimad MAJDOU on July 14, 2016
 */
@Service
public class GroupCompetenceMetierImpl implements IGroupCompetenceMetier{

    @Autowired
    private GroupCompetenceRepository groupCompetenceRepository;
    
   @Autowired
   private CompetenceRepository competenceReposistory;

    @Override
    public void delete(Long id) {
        groupCompetenceRepository.delete(id);
    }
    @Override
    public List<GroupCompetence> findAll() {
        return groupCompetenceRepository.findAll();
    }

    @Override
    public GroupCompetence findOne(Long id) {
        return groupCompetenceRepository.findOne(id);
    }

    @Override
    public GroupCompetence save(GroupCompetence groupCompetence) {
  	    Collection<Competence> cpts=groupCompetence.getCompetences();
    	GroupCompetence gc=groupCompetenceRepository.save(groupCompetence);
    	System.out.println("code GC=="+gc.getCodeGroupCompetence());
    	
	        if (cpts.size()>0){
 	        	for (Competence competence : cpts) {
						competence.setGroupCompetence(gc);
						competenceReposistory.saveAndFlush(competence);
					}
    	        }
      return gc;
    }

    @Override
    public GroupCompetence update(Long id, GroupCompetence groupCompetence) {
        GroupCompetence newGroupCompetence = groupCompetenceRepository.findOne(id);
        newGroupCompetence.setTitre(groupCompetence.getTitre());
        newGroupCompetence.setActivated(groupCompetence.getActivated());
        return groupCompetenceRepository.saveAndFlush(newGroupCompetence);
    }
}
