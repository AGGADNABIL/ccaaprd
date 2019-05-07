package org.capvalue.recrute.domaine;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name="POSTULER")
public class Postuler {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codePostuler;
	@Temporal(TemporalType.TIMESTAMP)
	private Date datePostulation;
	private double pretentionSalarial;
	private String fichierCV;
	private String fichierLettreMotivation;
	
	@ManyToOne
    @JoinColumn(name = "candidat")
    private Candidat candidat;
	
	@ManyToOne
    @JoinColumn(name = "offre")
    private  Offre offre;

	public Long getCodePostuler() {
		return codePostuler;
	}

	public void setCodePostuler(Long codePostuler) {
		this.codePostuler = codePostuler;
	}

	public Date getDatePostulation() {
		return datePostulation;
	}

	public void setDatePostulation(Date datePostulation) {
		this.datePostulation = datePostulation;
	}

	public double getPretentionSalarial() {
		return pretentionSalarial;
	}

	public void setPretentionSalarial(double pretentionSalarial) {
		this.pretentionSalarial = pretentionSalarial;
	}

	public String getFichierCV() {
		return fichierCV;
	}

	public void setFichierCV(String fichierCV) {
		this.fichierCV = fichierCV;
	}

	public String getFichierLettreMotivation() {
		return fichierLettreMotivation;
	}

	public void setFichierLettreMotivation(String fichierLettreMotivation) {
		this.fichierLettreMotivation = fichierLettreMotivation;
	}
    
	public Candidat getCandidat() {
		return candidat;
	}
	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}
    
	public Offre getOffre() {
		return offre;
	}
	public void setOffre(Offre offre) {
		this.offre = offre;
	}
	
	
	public Postuler() {
		// TODO Auto-generated constructor stub
	}

	public Postuler(Date datePostulation, double pretentionSalarial, String fichierCV, String fichierLettreMotivation) {
		super();
		this.datePostulation = datePostulation;
		this.pretentionSalarial = pretentionSalarial;
		this.fichierCV = fichierCV;
		this.fichierLettreMotivation = fichierLettreMotivation;
	}
	
	
	
	
}
