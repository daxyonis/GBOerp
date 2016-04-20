/**
 * 
 */
package services;

import java.util.List;

import services.dao.SoumissionDAO;
import models.SoumFlex;
import models.Soumission;

/**
 * @author Eva
 *
 */
public interface SoumService {
	
	/**
	 * Returns a list of Soumission that have a keyword in one or more
	 * specified fields
	 * @param key		key representing the field to search
	 * @param word		keyword to search for
	 * @return			java.util.List of Soumission
	 */
	public List<Soumission>	findByKeyword(SoumissionDAO.KeyFindSoumEnum key, 
										  String word,
										  String minDate,
										  String maxDate,
										  String clientNo,
										  String repNo);
	
	/**
	 * Returns a limited list of Soumission in a certain order
	 * @param numrows	number of Soumission objects to return
	 * @param orderBy	field by which to order the results (for NO_SOUM and SUITE it is DESC, other fields ASC)
	 * @return			java.util.List of Soumission
	 */
	public List<Soumission> findTop(int numrows, SoumissionDAO.KeyFindSoumEnum orderBy);

	/**
	 * Returns one Soumission object as referenced by the identity column noSoumission
	 * @param noSoum	noSoumission
	 * @return			a Soumission object
	 */
	public Soumission findByNo(int noSoum);

	/**
	 * Create a new Soumission from the info contained inside
	 * the SoumFlex object
	 * Also creates the item
	 * @param soum   a SoumFlex object filled with correct values
	 * @return int	the noSoumission just created (0 if failed)
	 */
	public int create(SoumFlex soum);
	
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
	 * @return	java.util.List of Soumission objects that respect the given criteria
	 */
	public List<Soumission> getPage(String search, String sort, String order, int pageSize, int pageNumber,
									String repNo, int statusCode, int numSoumOrDossier,
									String minDate, String maxDate);
	
	/**
	 * Get the count of all soumissions for the given filters (see getPage() for details)
	 * @return  the count of all soumissions filtered by keyword, rep, status, minDate, maxDate
	 */
	public int countAll(String search, String repNo, int statusCode, int numero, String minDate, String maxDate);
	
}
