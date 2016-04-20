/**
 * 
 */
package services;

import java.util.List;

import models.Rep;
/**
 * @author Eva
 * Rep Service interface
 */
public interface RepService {
	
	/**
	 * Get the list of all active vendors (reps)
	 * @return  a java.util.List of all Rep object that are active
	 */
	public List<Rep> findAllActive();
	
	/**
	 * Find one Rep given its code (Salesman field)
	 * @param code
	 * @return
	 */
	public Rep findByCode(String code);
	
	/**
	 * Get the list of all reps that have a given email
	 * @param email
	 * @return
	 */
	public List<Rep> findByEmail(String email);

}
