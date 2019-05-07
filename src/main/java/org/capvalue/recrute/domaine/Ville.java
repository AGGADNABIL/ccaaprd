package org.capvalue.recrute.domaine;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;
import java.util.Collection;
@Entity
public class Ville implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codeVille;
	private String nomVille;
	
	@OneToMany(mappedBy="ville")//
	private Collection<Offre> offres ;
	
	public Long getCodeVille() {
		return codeVille;
	}
	public void setCodeVille(Long codeVille) {
		this.codeVille = codeVille;
	}
	public String getNomVille() {
		return nomVille;
	}
	public void setNomVille(String nomVille) {
		this.nomVille = nomVille;
	}
	public Ville(String nomVille) {
		super();
		this.nomVille = nomVille;
	}
	public Ville() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@JsonIgnore
	public Collection<Offre> getOffres() {
		return offres;
	}
	
	@JsonSetter
	public void setOffres(Collection<Offre> offres) {
		this.offres = offres;
	}
	
  
}
