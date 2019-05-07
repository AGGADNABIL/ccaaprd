package org.capvalue.recrute.domaine;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@Entity
public class Formation implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codeFormation;
	private String titre;
	private Date dateDebut;
	private Date dateFin;
	private String ecole;
	public Long getCodeFormation() {
		return codeFormation;
	}
	public void setCodeFormation(Long codeFormation) {
		this.codeFormation = codeFormation;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
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
	public String getEcole() {
		return ecole;
	}
	public void setEcole(String ecole) {
		this.ecole = ecole;
	}
	public Formation(String titre, Date dateDebut, Date dateFin, String ecole) {
		super();
		this.titre = titre;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.ecole = ecole;
	}
	public Formation() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
