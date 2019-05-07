package org.capvalue.recrute.metierImpl;

import org.capvalue.recrute.dao.AdminRepository;
import org.capvalue.recrute.domaine.Admin;
import org.capvalue.recrute.metier.IAdminMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AdminMetierImpl implements IAdminMetier {
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public Admin saveAdmin(Admin admin) {
		return adminRepository.save(admin);
	}

	@Override
	public void deleteAdmin(String username) {
		Admin a=adminRepository.findOne(username);
		adminRepository.delete(a);
		
	}

	@Override
	public Admin updateAdmin(Admin admin) {
		Admin a=adminRepository.findOne(admin.getUsername());
		return adminRepository.saveAndFlush(a);
	}

	@Override
	public Admin findOneAdmin(String username) {
		return adminRepository.findOne(username);
	}

}
