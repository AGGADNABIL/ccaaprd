package org.capvalue.recrute.domaine;

import javax.persistence.*;
import java.io.Serializable;
@Entity
public class Role implements Serializable {
	
	@Column(unique=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codeRole;
	@Id
	private String role;
	private String description;
	
	public Long getCodeRole() {
		return codeRole;
	}
	public void setCodeRole(Long codeRole) {
		this.codeRole = codeRole;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Role(String role, String description) {
		super();
		this.role = role;
		this.description = description;
	}
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
