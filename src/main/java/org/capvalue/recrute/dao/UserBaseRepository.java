package org.capvalue.recrute.dao;
import org.capvalue.recrute.domaine.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
@NoRepositoryBean
public interface UserBaseRepository <U extends User> extends JpaRepository<U,String> {

}
