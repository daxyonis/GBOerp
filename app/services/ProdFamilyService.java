package services;

import java.util.List;

import models.ProdFamily;

public interface ProdFamilyService {

	/**
	 * Find all the product families
	 * @return a java.util.List of ProdFamily objects
	 */
	public List<ProdFamily> findAll();
}
