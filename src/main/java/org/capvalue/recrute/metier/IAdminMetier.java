package org.capvalue.recrute.metier;

import org.capvalue.recrute.domaine.Admin;

public interface IAdminMetier {
	public Admin saveAdmin(Admin admin);
	public void deleteAdmin(String username);
	public Admin updateAdmin(Admin admin);
	public Admin findOneAdmin(String username);
	
	

}
