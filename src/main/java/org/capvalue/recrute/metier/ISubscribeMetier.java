package org.capvalue.recrute.metier;

import org.capvalue.recrute.domaine.Subscribe;

import java.util.List;

public interface ISubscribeMetier {
	public String saveSubscribe(Subscribe subscribe);
	public Subscribe updateSubscribe(Subscribe subscribe);
	public List<Subscribe>getAllSubscribe();
	public Subscribe getOneSubscribe(String username);
	public void activedSubscribe(String username);
	public void desactivedSubscribe(String username);
	public void deleteSubscribe(String username);
    public void desinscrireSubscribe(String username);
}
