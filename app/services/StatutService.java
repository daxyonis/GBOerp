package services;

import java.util.List;
import models.Statut;

public interface StatutService {
	
	/**
	 * Find all statuses
	 * @return  a java.util.List of Statut objects
	 */
	public List<Statut> findAll();

}
