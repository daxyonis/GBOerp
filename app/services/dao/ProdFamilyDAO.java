package services.dao;

import models.ProdFamily;
import java.util.List;

public interface ProdFamilyDAO {

	/**
	 * Find all the product families
	 * @return  a java.util.List of ProdFamily objects
	 */
	public List<ProdFamily> findAll();
}
