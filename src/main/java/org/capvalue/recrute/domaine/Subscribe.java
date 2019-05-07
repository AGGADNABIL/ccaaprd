package org.capvalue.recrute.domaine;

import javax.persistence.*;
import java.io.Serializable;
@Entity
public class Subscribe implements Serializable {
	@Column(unique=true)
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long codeSubscribe;
	@Id
	private String email;
	private boolean activated;
	private String nom;
	private boolean desinscrire=true;
	
	
	public boolean isDesinscrire() {
		return desinscrire;
	}
	public void setDesinscrire(boolean desinscrire) {
		this.desinscrire = desinscrire;
	}
	public Long getCodeSubscribe() {
		return codeSubscribe;
	}
	public void setCodeSubscribe(Long codeSubscribe) {
		this.codeSubscribe = codeSubscribe;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Subscribe(String email, boolean activated, String nom) {
		super();
		this.email = email;
		this.activated = activated;
		this.nom = nom;
	}
	public Subscribe() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
