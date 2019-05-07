package org.capvalue.recrute.domaine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
@Entity
public class Competence implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codeCompetence;
	private String titre;
	private Boolean activated = true;

	@OneToMany(mappedBy="competence",cascade = CascadeType.ALL)
	private Collection<CandidatCompetence> candidatCompetences;
	
	@OneToMany(mappedBy="competence",fetch = FetchType.EAGER)
	private Collection<OffreCompetence> offreCompetences;

	@ManyToOne   //(cascade=CascadeType.ALL)
	@JoinColumn(name = "groupCompetence")  // @JoinColumn(name = "categoryId", nullable = false, updatable = false)
	 //@JoinColumn(name = "codeGroupCompetence")
	private GroupCompetence groupCompetence;
	
	public Long getCodeCompetence() {
		return codeCompetence;
	}

	public void setCodeCompetence(Long codeCompetence) {
		this.codeCompetence = codeCompetence;
	}


	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}

	@JsonIgnore
	@XmlTransient
	public Collection<CandidatCompetence> getCandidatCompetences() {
		return candidatCompetences;
	}

	@JsonSetter
	public void setCandidatCompetences(Collection<CandidatCompetence> candidatCompetences) {
		this.candidatCompetences = candidatCompetences;
	}
	
	@JsonIgnore
	//@XmlTransient
	public Collection<OffreCompetence> getOffreCompetences() {
		return offreCompetences;
	}

	public void setOffreCompetences(Collection<OffreCompetence> offreCompetences) {
		this.offreCompetences = offreCompetences;
	}

	public Competence(String titre) {
		super();
		this.titre = titre;
	}

	public Competence() {
		super();
		// TODO Auto-generated constructor stub
	}

	@JsonIgnore
	@XmlTransient
	public GroupCompetence getGroupCompetence() {
		return groupCompetence;
	}

	@JsonSetter
	public void setGroupCompetence(GroupCompetence groupCompetence) {
		this.groupCompetence = groupCompetence;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}
}
