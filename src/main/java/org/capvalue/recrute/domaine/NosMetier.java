package org.capvalue.recrute.domaine;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
@Entity
public class NosMetier implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
//	@Column(unique=true)
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer priorite;
	private String titre;
	//@Column(columnDefinition="TEXT")
	@Lob
	private String contenu;
	@Lob
	private String contenuShow;

	private String image;

	
	
	public String getContenuShow() {
		return contenuShow;
	}
	public void setContenuShow(String contenuShow) {
		this.contenuShow = contenuShow;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getPriorite() {
		return priorite;
	}
	public void setPriorite(Integer priorite) {
		this.priorite = priorite;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public NosMetier(Integer priorite, String titre, String contenu, String image) {
		super();
		this.priorite = priorite;
		this.titre = titre;
		this.contenu = contenu;
		this.image = image;
	}
	public NosMetier() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
