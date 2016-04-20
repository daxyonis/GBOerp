/**
 * 
 */
package services.dao;

import java.util.List;
import java.util.Date;

import models.Soumission;
import models.Client;

/**
 * <h1>Interface to define DAO on Soumission object.</h1>
 * @author Eva
 *
 */
public interface SoumissionDAO {
	
	/**
	 * This enum describes all possible keyword search mode
	 * ALL : look for keyword in all possible fields
	 * NO_SOUM : look for keyword in field NoSoumission
	 * SUITE  : look for keyword in field Suite
	 * PROJET : look for keyword in field Projet
	 * CLIENT : look for keyword in field Client
	 */
	public static enum KeyFindSoumEnum {ALL, NO_SOUM, SUITE, PROJET, CLIENT}
	
	/**
	 * Returns a limited list of Soumission that have a keyword in one or more
	 * specified fields
	 * @param key		key representing the field to search
	 * @param word		keyword to search for
	 * @return			java.util.List of Soumission
	 */
	public List<Soumission>	findByKeyword(KeyFindSoumEnum key, 
										  String word,
										  String minDate,
										  String maxDate,
										  String clientNo,
										  String repNo);
	
	/**
	 * Returns a limited list of Soumission in a certain order
	 * @param numrows	number of Soumission objects to return
	 * @param orderBy	field by which to order the results
	 * @return			java.util.List of Soumission
	 */
	public List<Soumission> findTop(int numrows, KeyFindSoumEnum orderBy);

	/**
	 * Returns one Soumission object as referenced by the identity column noSoumission
	 * @param noSoum	noSoumission
	 * @return			a Soumission object
	 */
	public Soumission findByNo(int noSoum);
	
	/**
	 * Creates one new Soumission from the given parameters:
	 * @param projet	name of project
	 * @param NoVendeur genius salesman code
	 * @param geniusNoClient genius client code
	 * @param contact   name of contact
	 * @param clientTelephoneContact  contact phone number
	 * @param clientEmail  contact email
	 * @param clientAdresse client adresse
	 * @param clientTelephone client phone number
	 * @param clientFax    client fax number
	 * @param clientVille  client city
	 * @param clientCP     client postal code (or zipcode)
	 * @param clientProvince  client province (or state)
	 * @param clientPays   client country
	 * @param dateEntre    estimation date
	 * @param dateLivraison required date for client
	 * @param commission   commission (0-100)
	 * @param prix         sale price (>0)
	 * @return   the NoSoumission of the soumission just created
	 */
	public int create(String projet, String NoVendeur, String geniusNoClient,  
					  String contact, String clientTelephoneContact, String clientEmail,					  
					  Date dateEntre, Date dateLivraison, float commission, double prix);
	
	/**
	 * Copies the client info into the relevant Soumission fields
	 * (same functionality as a button "Copy from database")
	 * @param client  the client object associated with the Soumission
	 * @return
	 */
	public int copyClientInfo(int noSoum, Client client);
	
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
	public List<Soumission> getPage(String search, String sort, String order, int pageSize, int pageNumber, 
								    String noRep, int statusCode, int numero,
								    String minDate, String maxDate);
	
	/**
	 * Get the count of all soumissions for the given filters (see getPage() for details)
	 * @return  the count of all soumissions filtered by keyword, rep, status, numero, minDate, maxDate
	 */
	public int countAll(String search, String noRep, int statusCode, int numero, String minDate, String maxDate);
}
