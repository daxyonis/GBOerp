/**
 * 
 */
package services.dao;

import java.util.List;
import models.Finition;

/**
 * @author Eva
 *
 */
public interface FinitionDAO {
	
	/**
	 * Find all Finition objects that are active
	 * @return a java.util.List of Finition objects
	 */
	public List<Finition> findAllActive();
	
	/**
	 * Find all Finition objects that exist
	 * @return  a java.util.List of Finition objects
	 */
	public List<Finition> findAll();

	/**
	 * Get the Finition corresponding to given id
	 * @param id  the id of Finition 
	 * @return a Finition object whose id is specified 
	 * (null if was not found)
	 */
	public Finition getById(int id);
	
	/**
	 * Update a particular Finition object
	 * @param finition the Finition to save
	 * @return true if was successful; false otherwise
	 */
	public boolean save(Finition finition);
	
	/**
	 * Add a particular Finition object
	 * @param finition the Finition to insert
	 * @return true if was successful; false otherwise
	 */
	public boolean add(Finition finition);
	
	/**
	 * Remove a particular Finition object
	 * @param finition the Finition to delete
	 * @return true if was successful; false otherwise
	 */
	public boolean remove(Finition finition);
}
