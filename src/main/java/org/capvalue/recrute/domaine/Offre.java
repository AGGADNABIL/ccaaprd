package org.capvalue.recrute.domaine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
@Entity
public class Offre implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codeOffre;

	private String titre;

	private String dureeMission;
	
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Date dateCreation;
	
	@Temporal(TemporalType.TIMESTAMP)
	//@Column(name="dateDebutMission")
	private Date dateDebutMission;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date datePublication;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateExpiration;
	
	//@Column(columnDefinition = "TEXT")
	@Lob
	private String poste;
	//@Column(columnDefinition = "TEXT")
	@Lob
	private String profilRecherche;
	private int nombreVue;
	private boolean etat=true;  // à verifier 
	private boolean emailSended;
	private String niveauExperience;
	@XmlTransient
	private int nombrePostulant;
	//@Column(columnDefinition="tinyint(1) default 1")
	private boolean stat=true;
	
	public boolean isStat() {
		return stat;
	}
	public void setStat(boolean stat) {
		this.stat = stat;
	}

	@OneToMany(mappedBy="offre")
	private Collection<Postuler> postulers;
    
	@OneToMany(mappedBy="offre",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Collection<OffreCompetence> offreCompetences;

	
	@ManyToOne
	@JoinColumn(name="codeVille")
	private Ville  ville;
	
	@ManyToOne
	@JoinColumn(name="codeTypeContrat")
	private TypeContrat  typeContrat;
	
	
	public Date getDateDebutMission() {
		return dateDebutMission;
	}
	public void setDateDebutMission(Date dateDebutMission) {
		this.dateDebutMission = dateDebutMission;
	}
	public Long getCodeOffre() {
		return codeOffre;
	}
	public void setCodeOffre(Long codeOffre) {
		this.codeOffre = codeOffre;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Date getDateExpiration() {
		return dateExpiration;
	}
	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public Date getDatePublication() {
		return datePublication;
	}
	public void setDatePublication(Date datePublication) {
		this.datePublication = datePublication;
	}
	public int getNombreVue() {
		return nombreVue;
	}
	public void setNombreVue(int nombreVue) {
		this.nombreVue = nombreVue;
	}
	public boolean isEtat() {
		return etat;
	}
	public void setEtat(boolean etat) {
		this.etat = etat;
	}
	public String getNiveauExperience() {
		return niveauExperience;
	}
	public void setNiveauExperience(String niveauExperience) {
		this.niveauExperience = niveauExperience;
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
	public Ville getVille() {
		return ville;
	}
	
	public void setVille(Ville ville) {
		this.ville = ville;
	}
	public TypeContrat getTypeContrat() {
		return typeContrat;
	}
	public void setTypeContrat(TypeContrat typeContrat) {
		this.typeContrat = typeContrat;
	}
	
	
	public boolean isEmailSended() {
		return emailSended;
	}
	public void setEmailSended(boolean emailSended) {
		this.emailSended = emailSended;
	}
	public Offre(String titre, String dureeMission, Date dateCreation, Date datePublication, Date dateExpiration,
			String poste, String profilRecherche, int nombreVue, boolean etat, String niveauExperience,
			int nombrePostulant) {
		super();
		this.titre = titre;
		this.dureeMission = dureeMission;
		this.dateCreation = dateCreation;
		this.datePublication = datePublication;
		this.dateExpiration = dateExpiration;
		this.poste = poste;
		this.profilRecherche = profilRecherche;
		this.nombreVue = nombreVue;
		this.etat = etat;
		this.niveauExperience = niveauExperience;
		this.nombrePostulant = nombrePostulant;
	}
	public Offre() {
		super();
	}


	public String getDureeMission() {
		return dureeMission;
	}

	public void setDureeMission(String dureeMission) {
		this.dureeMission = dureeMission;
	}

	public String getPoste() {
		return poste;
	}

	public void setPoste(String poste) {
		this.poste = poste;
	}

	public String getProfilRecherche() {
		return profilRecherche;
	}

	public void setProfilRecherche(String profilRecherche) {
		this.profilRecherche = profilRecherche;
	}
	public int getNombrePostulant() {
		return nombrePostulant;
	}
	public void setNombrePostulant(int nombrePostulant) {
		this.nombrePostulant = nombrePostulant;
	}

	@JsonIgnore
	public Collection<OffreCompetence> getOffreCompetences() {
		return offreCompetences;
	}

	@JsonSetter
	public void setOffreCompetences(Collection<OffreCompetence> offreCompetences) {
		this.offreCompetences = offreCompetences;
	}
}
