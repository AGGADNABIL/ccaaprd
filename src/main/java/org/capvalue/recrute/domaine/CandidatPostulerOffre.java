package org.capvalue.recrute.domaine;

import java.io.Serializable;
import java.util.Date;
//@Entity
public class CandidatPostulerOffre implements Serializable {
	//@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codeCandidatPostulerOffre;
	//@Temporal(TemporalType.TIMESTAMP)
	private Date datePostulation;
	private double pretentionSalarial;
	private String lettreMotivation;
	private String fichierLettreMotivation;
	/*@ManyToOne
    @JoinColumn(name = "candidat")
    private Candidat candidat;
	@ManyToOne
    @JoinColumn(name = "offre")
    private  Offre offre;*/
	
	public Long getCodeCandidatPostulerOffre() {
		return codeCandidatPostulerOffre;
	}
	public void setCodeCandidatPostulerOffre(Long codeCandidatPostulerOffre) {
		this.codeCandidatPostulerOffre = codeCandidatPostulerOffre;
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
	public String getLettreMotivation() {
		return lettreMotivation;
	}
	public void setLettreMotivation(String lettreMotivation) {
		this.lettreMotivation = lettreMotivation;
	}
	public String getFichierLettreMotivation() {
		return fichierLettreMotivation;
	}
	public void setFichierLettreMotivation(String fichierLettreMotivation) {
		this.fichierLettreMotivation = fichierLettreMotivation;
	}
	/*public Candidat getCandidat() {
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
	}*/
	public CandidatPostulerOffre(Date datePostulation, double pretentionSalarial, String lettreMotivation,
			String fichierLettreMotivation) {
		super();
		this.datePostulation = datePostulation;
		this.pretentionSalarial = pretentionSalarial;
		this.lettreMotivation = lettreMotivation;
		this.fichierLettreMotivation = fichierLettreMotivation;
	}
	public CandidatPostulerOffre() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
