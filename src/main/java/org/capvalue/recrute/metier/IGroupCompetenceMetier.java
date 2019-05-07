package org.capvalue.recrute.metier;

import org.capvalue.recrute.domaine.GroupCompetence;

import java.util.List;

/**
 * Created by Aimad MAJDOU on July 14, 2016
 */
public interface IGroupCompetenceMetier  {

    void delete(Long id) ;

    List<GroupCompetence> findAll() ;

    GroupCompetence findOne(Long id);

    GroupCompetence save(GroupCompetence groupCompetence);

    GroupCompetence update(Long id, GroupCompetence groupCompetence) ;
    
}
