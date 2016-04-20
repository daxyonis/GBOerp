package services.app;

import java.util.List;
import java.util.Optional;

import models.app.AppSoumEntete;
import models.app.AppSoumission;

public interface AppSoumService {		
	
	/**
	 * Get the status string corresponding to status code
	 * @param statusCode	the status code
	 * @return	status as string
	 */
	public String getStatut(int statusCode);
	
	/**
	 * Returns the AppSoumission corresponding to the passed id
	 * @param noSoum	appSoumission id
	 * @param withItems	true if the items of the appSoumission are required; false otherwise
	 * @return		java.util.Optional wrapping the AppSoumission as requested by its id
	 */
	public Optional<AppSoumission> findByNo(int noSoum, boolean withItems);
	
	/**
	 * Returns a list of all existing versions appSoumissions for a given suite 
	 * @param suite		the suite number
	 * @return	a list of AppSoumission  
	 */	 
	public List<AppSoumission> findBySuite(int suite);
	
	/**
	 * Creates a new empty AppSoumission in the repository
	 * @param soum	the AppSoumission object from which the new AppSoumission will be created
	 * @return	the id of newly created AppSoumission
	 */
	public int create(AppSoumission soum);			
	
	/**
	 * Updates a given AppSoumission in the repository
	 * @param soum	the AppSoumission to update
	 * @return	true if succeeded; false otherwise
	 */
	public boolean update(AppSoumission soum);
	
	/**
	 * Deletes a given AppSoumission
	 * @param noSoum	the id of AppSoumission to delete
	 * @return	true if succeeded; false otherwise
	 */
	public boolean delete(int noSoum);
	
	/**
	 * Creates a new AppSoumission that is the copy of passed AppSoumission 
	 * @param soum		the AppSoumission to copy
	 * @param copyItems	whether or not to copy the items also
	 * @return	the id of newly created copy
	 */
	public int createCopy(AppSoumission soum, boolean copyItems);
	
	/**
	 * Creates a new AppSoumission that is a copy of passed AppSoumission
	 * @param suite		suite of AppSoumission to copy (entete is the same for all versions of a suite)
	 * @param version	version of AppSoumission to copy (the items will be copied from this version; 
	 * 				    pass empty string to avoid creating any item)
	 * @return	the id of newly created copy
	 */
	public int createCopyVersion(int suite, String version);
	
	/**
	 * Finds the entete of one AppSoumission
	 * @param noSoum	the id of AppSoumission
	 * @return	java.util.Optional wrapping the AppSoumEntete searched for
	 */
	public Optional<AppSoumEntete> findEnteteByNo(int noSoum);
	
	/**
	 * Updates the entete of an AppSoumission
	 * @param entete	the AppSoumEntete to be updated
	 * @return	true if succeeded; false otherwise
	 */
	public boolean updateEntete(AppSoumEntete entete);
	
	/**
	 * Returns a subset of all soumissions
	 * @param search	seach keyword(s)
	 * @param sort		field name by which to sort
	 * @param order		order by which the soumissions are returned (asc or desc)
	 * @param pageSize	num of records to return
	 * @param pageNumber  the number of the page wanted (min = 1)
	 * @param repNo		the rep id ("NA" for all)
	 * @param statusCode the status code (0 for all)
	 * @param numSoumOrDossier	a search number for the soumission suite or dossier
	 * @param minDate   the most anterior date for the soumission (empty for all anterior dates)
	 * @param maxDate   the most posterior date for the soumission (empty for all posterior dates)
	 * @return	java.util.List of AppSoumission objects that respect the given criteria
	 */
	public List<AppSoumission> getPage(String search, String sort, String order, int pageSize, int pageNumber,
									String repNo, int statusCode, int numSoumOrDossier,
									String minDate, String maxDate);
	
	/**
	 * Get the count of all soumissions for the given filters (see getPage() for details)
	 * @return  the count of all soumissions filtered by keyword, rep, status, minDate, maxDate
	 */
	public int countAll(String search, String repNo, int statusCode, int numero, String minDate, String maxDate);
}
