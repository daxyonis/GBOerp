/**
 * 
 */
package services;

import java.util.List;

import models.Item;
import models.SoumFlex;
import models.SoumFlexItem;

/**
 * @author Eva
 *
 */
public interface ItemService{	

	/**
	 * Makes a copy of all items in the list and reference them to the
	 * specified Soumission.
	 * (Copy includes propagation to SoumXXDAOs that reference these Item objects).
	 */
	public int[] copyItemsToSoum(List<Item> items, int noSoumissionCible);
	
	/**
	 * Creates an item from a SoumFlex object
	 * Includes calls to estimation DAOs to write estimation data where needed
	 * @param noSoumission   the no of soumission where the item belongs
	 * @param soumflex       the soumission rapide flexible object 
	 * @param soumflexItem   the soumission rapide flexible item object that needs to be added
	 * @return	true if item was created; false otherwise
	 */
	public boolean create(int noSoumission, SoumFlex soumflex, SoumFlexItem soumflexItem);
	
	/**
	 * Find all items of a given Soumission
	 * @param noSoumission   the no of the Soumission
	 * @return  java.util.List of Item
	 */
	public List<Item> findForSoum(int noSoumission);
}
