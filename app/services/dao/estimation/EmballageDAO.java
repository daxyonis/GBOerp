/**
 * 
 */
package services.dao.estimation;

/**
 * @author Eva
 *
 */
public interface EmballageDAO {

	/**
	 * Adds a new entry into SoumEmballage	
	 * @param noItem	the NoItem this Emballage belongs to 
	 * @param codeProduit the product code (empty string if none)
	 * @param emballage	the name of Emballage
	 * @param cout		the unit cost
	 * @param unites	the units in which the cost is provided
	 * @param marge		the profit margin as % (40 means 40%)
	 * @param dimension if units are 2d or 3d, you can enter the 1+nD measure here
	 * @param quantite  the 1-d measure
	 * @return  true if the add succeeded; false otherwise
	 */
	public boolean addNew(int noItem, String codeProduit, String emballage, double cout, 
						  String unites, double marge, double dimension, double quantite);
}
