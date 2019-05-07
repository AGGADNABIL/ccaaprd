package org.capvalue.recrute.service;

import org.capvalue.recrute.domaine.GroupCompetence;
import org.capvalue.recrute.metier.IGroupCompetenceMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Aimad MAJDOU on July 14, 2016
 */
@RestController
public class GroupCompetenceService {
    
    @Autowired
    private IGroupCompetenceMetier groupCompetenceMetier;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/group-competence/{codeGroupCompetence}",method= RequestMethod.DELETE)
    public void deleteGroupCompetence(@PathVariable Long codeGroupCompetence) {
        groupCompetenceMetier.delete(codeGroupCompetence);
    }

    @RequestMapping(value="/group-competence", method=RequestMethod.GET)
    public List<GroupCompetence> findAllGroupCompetences(HttpServletRequest req) {
    	System.out.println("search :"+req.getLocalAddr()+" "+req.getContextPath()+" "+req.getPathInfo());
        return groupCompetenceMetier.findAll();
    }

    @RequestMapping(value="/group-competence/{id}", method=RequestMethod.GET)
    public GroupCompetence findOneGroupCompetence(@PathVariable Long id) {
        return groupCompetenceMetier.findOne(id);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/group-competence", method=RequestMethod.POST)
    public GroupCompetence saveGroupCompetence(@RequestBody GroupCompetence groupCompetence) {
    	
        return groupCompetenceMetier.save(groupCompetence);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/group-competence/{id}", method=RequestMethod.PUT)
    public @ResponseBody GroupCompetence saveAndFlush(@PathVariable Long id,@RequestBody GroupCompetence groupCompetence) {
        return groupCompetenceMetier.update(id, groupCompetence);
    }
    
}
