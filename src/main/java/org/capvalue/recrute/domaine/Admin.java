package org.capvalue.recrute.domaine;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlType;

@Entity
@DiscriminatorValue("UA")
@XmlType(name = "UA")
public class Admin extends User {

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Admin(String typeUser,String username, String password, String nom, String prenom, String keyActivation, boolean actived,String photo) {
		super(typeUser,username, password, nom, prenom, keyActivation, actived,photo);
		// TODO Auto-generated constructor stub
	}
	

}
