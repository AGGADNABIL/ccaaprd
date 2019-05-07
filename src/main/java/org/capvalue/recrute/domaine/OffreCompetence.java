package org.capvalue.recrute.domaine;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class OffreCompetence implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codeOffreCompetence;
	private String niveauRequis;
	
	@ManyToOne
    @JoinColumn(name = "competence")
    private Competence competence;
	
	@ManyToOne
	@JoinColumn(name="offre")
    //@JsonIgnore
    private Offre offre;
	
	
	public Long getCodeOffreCompetence() {
		return codeOffreCompetence;
	}
	public void setCodeOffreCompetence(Long codeOffreCompetence) {
		this.codeOffreCompetence = codeOffreCompetence;
	}
	public String getNiveauRequis() {
		return niveauRequis;
	}
	public void setNiveauRequis(String niveauRequis) {
		this.niveauRequis = niveauRequis;
	}

	public Competence getCompetence() {
		return competence;
	}

	public void setCompetence(Competence competence) {
		this.competence = competence;
	}
	//@JsonIgnore
	public Offre getOffre() {
		return offre;
	}
	//@JsonSetter
	public void setOffre(Offre offre) {
		this.offre = offre;
	}
	public OffreCompetence(String niveauRequis) {
		super();
		this.niveauRequis = niveauRequis;
	}
	public OffreCompetence() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
