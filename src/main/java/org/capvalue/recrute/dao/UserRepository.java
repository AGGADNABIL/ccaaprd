package org.capvalue.recrute.dao;

import org.capvalue.recrute.domaine.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
public interface UserRepository extends UserBaseRepository<User> {
	
	@Query("select u from User u where u.username =:email")
	List<User> getUserByEmail( @Param("email") String email);
	
	@Query("select u from User u where u.nom =:nom")
	List<User> getUserByNom( @Param("nom") String nom);
	
	@Query("select u from User u where u.username =:x")
	User getUserByUsername( @Param("x") String username);
	
	@Modifying
    @Query("UPDATE User u set u.keyActivation = :x where u.username = :y")
    int forgotPassword(@Param("x") String verificationKey, @Param("y") String email);

    @Modifying
    @Query("UPDATE User u set u.password = :x where u.username = :y")
    int setPassword(@Param("x") String password, @Param("y") String username);
    
    /*@Query("DELETE FROM User u WHERE u.username = :y")
    void deleteUser(@Param("y") String username);*/
    
    @Query("select u from User u where u.typeUser='UA'")
    List<User> allAdmin();

}
