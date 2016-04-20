package services.app.dao;

import java.util.List;
import java.util.Optional;

import models.app.AppSoumission;

public interface AppSoumissionDAO {
	
	/**
	 * Find a given AppSoumission corresponding to its id
	 * @param noSoum	the AppSoumission id (noSoumission)
	 * @return	a java.util.Optional wrapping the found AppSoumission
	 */
	public Optional<AppSoumission> findByNo(int noSoum);
	
	/**
	 * Find an AppSoumission given its (suite, version)
	 * @param suite		the AppSoumission suite number
	 * @param version	the AppSoumission version (1-letter String)
	 * @return	a java.util.Optional wrapping the found AppSoumission
	 */
	public Optional<AppSoumission> findBySuiteVersion(int suite, String version);
	
	/**
	 * Returns a list of all AppSoumission that exist for a given suite number
	 * @param 	suite	the suite number
	 * @return	a list of AppSoumission  
	 */
	public List<AppSoumission> findBySuite(int suite);
	
	/**
	 * Creates a new empty AppSoumission
	 * @param soum	the AppSoumission to create
	 * @return	the id of newly created AppSoumission 
	 */
	public int create(AppSoumission soum);
	
	/**
	 * Creates a new version of same suite AppSoumission
	 * @param soum	the AppSoumission for which to create a new version (same suite) 
	 * @return	the id of newly created AppSoumission 
	 */
	public int createNewVersion(AppSoumission soum);
	
	/**
	 * Update an AppSoumission
	 * @param soum	the AppSoumission to update
	 * @return true of update succeeded; false otherwise
	 */
	public boolean update(AppSoumission soum);
	
	/**
	 * Delete an AppSoumission
	 * @param noSoum	the id of AppSoumission to delete
	 * @return true if delete succeeded; false otherwise
	 */
	public boolean delete(int noSoum);
	
	/**
	 * Returns a subset of all soumissions
	 * @param search	seach keyword(s)
	 * @param sort		field name by which to sort
	 * @param order		order by which the soumissions are returned (asc or desc)
	 * @param pageSize	num of records to return
	 * @param pageNumber  the number of the page wanted (min = 1)
	 * @param repNo		the rep id ("NA" for all)
	 * @param statusCode the status code (0 for all)
	 * @param numero	a search number for the soumission suite or dossier
	 * @param minDate   the most anterior date for the soumission (empty for all anterior dates)
	 * @param maxDate   the most posterior date for the soumission (empty for all posterior dates)
	 * @return	java.util.List of Soumission objects that respect the given criteria
	 */
	public List<AppSoumission> getPage(String search, String sort, String order, int pageSize, int pageNumber, 
								    String noRep, int statusCode, int numero,
								    String minDate, String maxDate);
	
	/**
	 * Get the count of all soumissions for the given filters (see getPage() for details)
	 * @return  the count of all soumissions filtered by keyword, rep, status, numero, minDate, maxDate
	 */
	public int countAll(String search, String noRep, int statusCode, int numero, String minDate, String maxDate);

}
