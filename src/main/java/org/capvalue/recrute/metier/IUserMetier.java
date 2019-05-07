package org.capvalue.recrute.metier;

import java.util.Collection;

import org.capvalue.recrute.domaine.User;

public interface IUserMetier {
	public Collection<User> getUsers();
	public void removeUser(String username);
	public void changeEtatUser(String username, boolean etat);
	public User getUserByUsername(String username);
	public String forgotPassword(String email);
	public String recoverAccount(String email, String verificationKey);
    public String recoverPassword(String email, String verificationKey, String password);
    public User showUser (String username);
	public User updatePasswrd(String username, User user);

}
