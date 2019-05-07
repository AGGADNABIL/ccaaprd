package org.capvalue.recrute.domaine;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Reference implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codeReference;
	
	private String reference;
	
    @Temporal(TemporalType.TIMESTAMP)
	private Date datePub;
	
	
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public Long getCodeReference() {
		return codeReference;
	}
	public void setCodeReference(Long codeReference) {
		this.codeReference = codeReference;
	}
	public Reference(String reference) {
		super();
		this.reference = reference;
	}
	
	public Reference(String reference, Date datePub) {
		super();
		this.reference = reference;
		this.datePub = datePub;
	}
	public Reference() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Date getDatePub() {
		return datePub;
	}
	public void setDatePub(Date datePub) {
		this.datePub = datePub;
	}
	
}
