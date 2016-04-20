/**
 * 
 */
package services.dao.estimation;

/**
 * @author Eva
 *
 */
public interface AssemblageDAO {
	
	/**
	 * Adds a new entry in SoumAssemblage
	 * @param noItem		the item no that this assemblage belongs to
	 * @param activite		the activity name
	 * @param tauxChoix		the fee
	 * @param nbrHeure		the time in hours
	 * @param marge			the profit margin in % (40 = 40%)
	 * @param description   a short description if needed
	 * @return				true if entry was successfully added; false otherwise
	 */
	public boolean addNew(int noItem, String activite, double tauxChoix, 
						  double nbrHeure, double marge, String description);
}
