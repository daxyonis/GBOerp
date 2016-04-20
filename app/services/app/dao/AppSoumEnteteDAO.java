package services.app.dao;

import java.util.Optional;

import models.app.AppSoumEntete;

public interface AppSoumEnteteDAO {

	public Optional<AppSoumEntete> findByNo(int noSoum);
	
	public boolean update(AppSoumEntete entete);
		
}
