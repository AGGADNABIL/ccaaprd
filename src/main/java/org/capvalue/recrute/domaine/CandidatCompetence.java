package org.capvalue.recrute.domaine;

import javax.persistence.*;
import java.io.Serializable;
@Entity
public class CandidatCompetence implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codeCandidatCompetence;
	private String niveauExperience;
	@ManyToOne
    @JoinColumn(name = "candidat")
    private Candidat candidat;
	@ManyToOne
    @JoinColumn(name = "competence")
    private  Competence competence;
	
	public Long getCodeCandidatCompetence() {
		return codeCandidatCompetence;
	}
	public void setCodeCandidatCompetence(Long codeCandidatCompetence) {
		this.codeCandidatCompetence = codeCandidatCompetence;
	}
	public String getNiveauExperience() {
		return niveauExperience;
	}
	public void setNiveauExperience(String niveauExperience) {
		this.niveauExperience = niveauExperience;
	}
	public Candidat getCandidat() {
		return candidat;
	}
	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}
	public Competence getCompetence() {
		return competence;
	}
	public void setCompetence(Competence competence) {
		this.competence = competence;
	}
	public CandidatCompetence(String niveauExperience) {
		super();
		this.niveauExperience = niveauExperience;
	}
	public CandidatCompetence() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
