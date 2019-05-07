package org.capvalue.recrute.domaine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
@Entity
@DiscriminatorValue("UC")
@XmlType(name = "UC")
public class Candidat extends User implements Serializable{
	private String niveauEtude;
	private String profil;
	private String niveauExperience;
	private String situationActuelle;
	private String cv;
	
	private String adresse;
	private String telephone;
	private Double salaire;
	@Temporal(TemporalType.DATE)
	private Date dateDisponibilite;
	private String typeSalaire;
	@Temporal(TemporalType.DATE)
	private Date dateNaiss;
	private String nationalite;
	
	private boolean notification=false;
	
    @OneToMany(mappedBy="candidat")
	private Collection<CandidatCompetence> candidatCompetences;
	@ManyToMany
	private Collection<Experience>experiences;
	@ManyToMany
	private Collection<Langue>langues;
	@ManyToMany
	private Collection<Formation>formations;
	
	@OneToMany(mappedBy="candidat")
	private Collection<Postuler> postulers;
	
	public String getNiveauEtude() {
		return niveauEtude;
	}
	public void setNiveauEtude(String niveauEtude) {
		this.niveauEtude = niveauEtude;
	}
	public String getNiveauExperience() {
		return niveauExperience;
	}
	public void setNiveauExperience(String niveauExperience) {
		this.niveauExperience = niveauExperience;
	}
	public String getSituationActuelle() {
		return situationActuelle;
	}
	public void setSituationActuelle(String situationActuelle) {
		this.situationActuelle = situationActuelle;
	}
	public String getCv() {
		return cv;
	}
	public void setCv(String cv) {
		this.cv = cv;
	}
	
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Double getSalaire() {
		return salaire;
	}
	public void setSalaire(Double salaire) {
		this.salaire = salaire;
	}
	public Date getDateDisponibilite() {
		return dateDisponibilite;
	}
	public void setDateDisponibilite(Date dateDisponibilite) {
		this.dateDisponibilite = dateDisponibilite;
	}
	public String getTypeSalaire() {
		return typeSalaire;
	}
	public void setTypeSalaire(String typeSalaire) {
		this.typeSalaire = typeSalaire;
	}
	@JsonIgnore
	public Collection<CandidatCompetence> getCandidatCompetences() {
		return candidatCompetences;
	}
	@JsonSetter
	public void setCandidatCompetences(Collection<CandidatCompetence> candidatCompetences) {
		this.candidatCompetences = candidatCompetences;
	}
	
	@JsonIgnore
	@XmlTransient
	public Collection<Experience> getExperiences() {
		return experiences;
	}
	
	@JsonSetter
	public void setExperiences(Collection<Experience> experiences) {
		this.experiences = experiences;
	}
	@JsonIgnore
	@XmlTransient
	public Collection<Langue> getLangues() {
		return langues;
	}
	
	@JsonSetter
	public void setLangues(Collection<Langue> langues) {
		this.langues = langues;
	}
	
	@JsonIgnore
	 @XmlTransient
	public Collection<Formation> getFormations() {
		return formations;
	}
	
	@JsonSetter
	public void setFormations(Collection<Formation> formations) {
		this.formations = formations;
	}
	
	
	public boolean isNotification() {
		return notification;
	}
	public void setNotification(boolean notification) {
		this.notification = notification;
	}
	@JsonIgnore
    @XmlTransient
	public Collection<Postuler> getPostulers() {
		return postulers;
	}
	
	@JsonSetter
	public void setPostulers(Collection<Postuler> postulers) {
		this.postulers = postulers;
	}
	
	public String getProfil() {
		return profil;
	}
	public void setProfil(String profil) {
		this.profil = profil;
	}
	
	public Candidat(String typeUser, String username, String password, String nom, String prenom, String keyActivation,
			boolean actived,String photo, String niveauEtude, String profil, String niveauExperience, String situationActuelle,
			String cv, String adresse, String telephone, Double salaire, Date dateDisponibilite,
			String typeSalaire, Date dateNaiss, String nationalite, boolean notification) {
		super(typeUser, username, password, nom, prenom, keyActivation, actived,photo);
		this.niveauEtude = niveauEtude;
		this.profil = profil;
		this.niveauExperience = niveauExperience;
		this.situationActuelle = situationActuelle;
		this.cv = cv;
		this.adresse = adresse;
		this.telephone = telephone;
		this.salaire = salaire;
		this.dateDisponibilite = dateDisponibilite;
		this.typeSalaire = typeSalaire;
		this.dateNaiss = dateNaiss;
		this.nationalite = nationalite;
		this.notification = notification;
	}
	
	public Candidat(String typeUser,String username, String password, String nom, String prenom, String keyActivation, boolean actived,
			String niveauEtude, String profil, String niveauExperience, String situationActuelle, String cv,
			String photo, String adresse, String telephone, double salaire, Date dateDisponibilite,
			String typeSalaire) {
		super(typeUser,username, password, nom, prenom, keyActivation, actived,photo);
		this.niveauEtude = niveauEtude;
		this.profil = profil;
		this.niveauExperience = niveauExperience;
		this.situationActuelle = situationActuelle;
		this.cv = cv;
	
		this.adresse = adresse;
		this.telephone = telephone;
		this.salaire = salaire;
		this.dateDisponibilite = dateDisponibilite;
		this.typeSalaire = typeSalaire;
	}
	public Candidat() {
		super();
		
	}
	public Date getDateNaiss() {
		return dateNaiss;
	}
	public void setDateNaiss(Date dateNaiss) {
		this.dateNaiss = dateNaiss;
	}
	public String getNationalite() {
		return nationalite;
	}
	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}
	
}
