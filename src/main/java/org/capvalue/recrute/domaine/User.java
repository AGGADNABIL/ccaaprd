package org.capvalue.recrute.domaine;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="users")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE_USER",discriminatorType=DiscriminatorType.STRING,length=2)
public class User implements Serializable {
	@Column(name = "TYPE_USER", insertable = false, updatable = false)
    private String typeUser;
	
	@Column(unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long codeUser;
	
	@Id
	protected String username;
	protected String photo;
	@NotEmpty
	protected String password;
	protected String nom;
	protected String prenom;
	protected String keyActivation;
	protected boolean actived;
	@ManyToMany
	protected Collection<Role>roles;
	
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getTypeUser() {
		return typeUser;
	}
	public void setTypeUser(String typeUser) {
		this.typeUser = typeUser;
	}
	public Long getCodeUser() {
		return codeUser;
	}
	public void setCodeUser(Long codeUser) {
		this.codeUser = codeUser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getKeyActivation() {
		return keyActivation;
	}
	public void setKeyActivation(String keyActivation) {
		this.keyActivation = keyActivation;
	}
	public boolean isActived() {
		return actived;
	}
	public void setActived(boolean actived) {
		this.actived = actived;
	}
	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
	public User(String typeUser, String username, String password, String nom, String prenom, String keyActivation,
			boolean actived,String photo) {
		super();
		this.typeUser = typeUser;
		this.username = username;
		this.password = password;
		this.nom = nom;
		this.prenom = prenom;
		this.keyActivation = keyActivation;
		this.actived = actived;
		this.photo=photo;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
 }
