package org.capvalue.recrute.dao;

import org.capvalue.recrute.domaine.Admin;

import javax.transaction.Transactional;
@Transactional
public interface AdminRepository extends UserBaseRepository<Admin> {

}
