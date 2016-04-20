/**
 * 
 */
package services.dao;

import java.util.List;
import models.FlexibleDTO;

/**
 * @author Eva
 * To handle FlexibleDTO DAO
 */
public interface FlexibleDTODAO {

	/**
	 * Find all Flexible DTOs EXCEPT those Flexibles that have Categorie=LAMINATION
	 * @return  java.util.List of FlexibleDTO objects
	 */
	public List<FlexibleDTO> findAll();
	
	public FlexibleDTO findById(int flexId);
	
}
