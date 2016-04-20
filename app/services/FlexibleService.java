/**
 * 
 */
package services;

import java.util.List;

import models.Flexible;
import models.FlexibleDTO;

/**
 * @author Eva
 *
 */
public interface FlexibleService {
	
	/**
	 * Find all the Flexible objects that exist
	 * @return  java.util.List of all the Flexible objects
	 */
	public List<Flexible> findAll();
	
	/**
	 * Find all the FlexibleDTO objects
	 * @return  java.util.List of all FlexibleDTO objects
	 */
	public List<FlexibleDTO> findAllDTO();
	
	/**
	 * Get one Flexible based on its id
	 * @param id   the id of object
	 * @return     a Flexible object whose id is specified
	 */
	public Flexible getById(int id);

	/**
	 * Updates an existing Flexible object
	 * @param flex   Flexible object to persist
	 * @return   true if update was successful; false otherwise
	 */
	public boolean update(Flexible flex);
	
	/**
	 * Adds a new Flexible object
	 * @param flex   Flexible object to persist
	 * @return   true if add was successful; false otherwise
	 */
	public boolean add(Flexible flex);
	
	/**
	 * Removes a Flexible object based on its Id
	 * @param id   the id of object
	 * @return     true if delete was successful; false otherwise
	 */
	public boolean remove(int id);
	
	/**
	 * Removes a Flexible object
	 * @param flex   the Flexible object to remove
	 * @return     true if delete was successful; false otherwise
	 */
	public boolean remove(Flexible flex);
	
	/**
	 * Returns a list of all distinct categories for the flexibles
	 * @return
	 */
	public List<String> getAllCategories();	
}
