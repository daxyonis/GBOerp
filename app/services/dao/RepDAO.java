/**
 * 
 */
package services.dao;

import java.util.List;

import models.Rep;
/**
 * @author Eva
 * DAO for getting the Rep list
 */
public interface RepDAO {
	
	/**
	 * Retrieve all active Reps
	 * @return	a java.util.list of all the active Rep objects
	 */
	public List<Rep> findAllActive();

	/**
	 * Retrieve one Rep given the salesman code
	 * @param code salesman code
	 * @return Rep object corresponding to the specified code
	 */
	public Rep findByCode(String code);
	
	/**
	 * Retrieve all Reps given the salesman email 
	 * @param email  salesman email
	 * @return java.util.List of Rep objects corresponding to the specified email
	 */
	public List<Rep> findByEmail(String email);
}
