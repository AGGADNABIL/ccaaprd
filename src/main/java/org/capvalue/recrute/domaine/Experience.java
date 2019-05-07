package org.capvalue.recrute.domaine;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@Entity
public class Experience implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codeExperience;
	private String descrption;
	private Date dateDebut;
	private Date dateFin;
	private String societe;
	private String poste;
	public Long getCodeExperience() {
		return codeExperience;
	}
	public void setCodeExperience(Long codeExperience) {
		this.codeExperience = codeExperience;
	}
	public String getDescrption() {
		return descrption;
	}
	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public String getSociete() {
		return societe;
	}
	public void setSociete(String societe) {
		this.societe = societe;
	}
	public String getPoste() {
		return poste;
	}
	public void setPoste(String poste) {
		this.poste = poste;
	}
	public Experience(String descrption, Date dateDebut, Date dateFin, String societe, String poste) {
		super();
		this.descrption = descrption;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.societe = societe;
		this.poste = poste;
	}
	public Experience() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
