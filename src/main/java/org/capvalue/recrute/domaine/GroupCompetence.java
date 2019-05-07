package org.capvalue.recrute.domaine;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Aimad MAJDOU on July 13, 2016
 */
@Entity
public class GroupCompetence implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long codeGroupCompetence;
    private String titre;
    private Boolean activated = true;

    @OneToMany(mappedBy="groupCompetence",fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    private Collection<Competence> competences;

    public Long getCodeGroupCompetence() {
        return codeGroupCompetence;
    }

    public void setCodeGroupCompetence(Long codeGroupCompetence) {
        this.codeGroupCompetence = codeGroupCompetence;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public GroupCompetence(String titre) {
        this.titre = titre;
    }

    public GroupCompetence() {
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }
    
    //@JsonIgnore
    public Collection<Competence> getCompetences() {
        return competences;
    }

   // @JsonSetter
    public void setCompetences(Collection<Competence> competences) {
        this.competences = competences;
    }
       
   public Competence addCompetence(Competence competence)
    {
		getCompetences().add(competence);
     	competence.setGroupCompetence(this);
		return competence;
	}
   
}
