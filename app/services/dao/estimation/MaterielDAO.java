/**
 * 
 */
package services.dao.estimation;

/**
 * @author Eva
 *
 */
public interface MaterielDAO {	

	/**
	 * Adds a new entry into SoumMateriel	
	 * @param noItem	the NoItem this Materiel belongs to 
	 * @param codeProduit the product code (empty string if none)
	 * @param materiel	the name of Materiel
	 * @param cout		the unit cost
	 * @param unites	the units in which the cost is provided
	 * @param perte		the material loss as % (eg: 20 means 20%)
	 * @param marge		the profit margin as % (40 means 40%)
	 * @param dimension if units are 2d or 3d, you can enter the 1+nD measure here
	 * @param quantite  the 1-d measure
	 * @return  true if the add succeeded; false otherwise
	 */
	public boolean addNew(int noItem, String codeProduit, String materiel, double cout, 
						  String unites, double perte, double marge, double dimension, double quantite);

}
