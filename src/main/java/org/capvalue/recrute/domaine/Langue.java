package org.capvalue.recrute.domaine;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
@Entity
public class Langue implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codeLangue;
	private String titre;
	public Long getCodeLangue() {
		return codeLangue;
	}
	public void setCodeLangue(Long codeLangue) {
		this.codeLangue = codeLangue;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public Langue(String titre) {
		super();
		this.titre = titre;
	}
	public Langue() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
