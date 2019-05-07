package org.capvalue.recrute.domaine;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
@Entity
public class TypeContrat implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codeTypeContrat;
	private String titre;
	public Long getCodeTypeContrat() {
		return codeTypeContrat;
	}
	public void setCodeTypeContrat(Long codeTypeContrat) {
		this.codeTypeContrat = codeTypeContrat;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public TypeContrat(String titre) {
		super();
		this.titre = titre;
	}
	public TypeContrat() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
