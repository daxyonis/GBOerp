/**
 * 
 */
package services.dao;

import java.util.List;

import models.Item;


/**
 * @author Eva
 * <h1>Interface to define DAO on Item object.</h1>
 */
public interface ItemDAO {
	
	/**
	 * This enum describes all possible keyword search mode
	 * ALL : look for keyword in all possible fields
	 * NO_ITEM : look for keyword in field NoItem
	 * NOM_ITEM : look for keyword in field NomItem
	 * DESCRIPTION : look for keyword in field Description
	 * FAMILLE : look for keyword in field Famille
	 */
	public static enum KeyFindItemEnum {ALL, NO_ITEM, NOM_ITEM, DESCRIPTION, FAMILLE}

	
	/**
	/* Create Item using the non-null mandatory fields
	 * 
	 * @param nom
	 * @param description
	 * @param quantite
	 * @param sourceProd
	 * @param fraisVariables
	 * @param fraisInstallation
	 * @param prixFab
	 * @param hauteur
	 * @param largeur
	 * @return	the NoItem of newly created item
	 */
	public int create(String nom, 
					   int noSoumission,
					   String description, 
					   int quantite, 
					   String sourceProd,
					   double fraisVariables,
					   double fraisInstallation,
					   double prixFab,
					   String hauteur,
					   String largeur, 
					   String rectoVerso  
					   );
	
	/**
	/* Add an item
	 *
	 * @param item to add
	 * @return	the NoItem of newly created item
	 */
	public int addItem(Item item);
	
	/**
	/* Read an item
	 * 
	 * @param noItem
	 * @return	Item object corresponding to given noItem
	 */
	public Item getItem(int noItem);
	
	/**
	/* Find all items (beware can return LOTS of data)
	 * 
	 * @return	List of all Item objects
	 */
	public List<Item> findAllItems();
	
	/**	
	 * Find all items that reference a given soumission
	 * 
	 * @param noSoumission : the Soumission number
	 * @return List of Item objects that reference a given soumission
	 */
	public List<Item> findAllItemsFromSoum(int noSoumission);
	
	/**
	 * Find all items that contain a given keyword in a specific key
	 * 
	 * @param key	key search mode
	 * @param word	keyword to search
	 * @return		List of Item objects that contain the keyword
	 */
	public List<Item> findByKeyword(KeyFindItemEnum key, String word);
	
	/**
	 * Delete item
	 * 
	 * @param noItem
	 * @return	number of rows affected
	 */
	public int delete(int noItem);
	
	/**
	 * Update item by specifying new values for mandatory fields.
	 * 
	 * @param noItem
	 * @param nom
	 * @param description
	 * @param quantite
	 * @param sourceProd
	 * @param fraisVariables
	 * @param fraisInstallation
	 * @param prixFab
	 * @param hauteur
	 * @param largeur
	 * @return	number of rows affected
	 */
	public int update(int noItem, 
					   String nom, 
					   String description, 
					   int quantite, 
					   String sourceProd,
					   double fraisVariables,
					   double fraisInstallation,
					   double prixFab,
					   String hauteur,
					   String largeur  
					   );
	
	/**
	 * Update item by passing an item copy object as parameter
	 * 
	 * @param item
	 * @return	number of rows affected
	 */
	public int update(Item item);
	
	/**
	 * Create new Item entries that are copies
	 * of all items in a list and set their NoSoumission
	 * to the specified target value
	 * @param oldNoItemArray		noItem of items to make a copy of
	 * @param noSoumissionCible		target value for NoSoumission
	 * NOTE: this only makes a new entry in the Item table (does not
	 * propagate to the SoumXXX tables that reference NoItem) -- see ItemService for that
	 */
	public int[] copyItemInfoToSoum(int[] oldNoItemArray, int noSoumissionCible);
}
