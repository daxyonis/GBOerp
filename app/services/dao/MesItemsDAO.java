/**
 * 
 */
package services.dao;

import models.Item;
import models.MesItems;
import java.util.List;
/**
 * @author Eva
 *
 */
public interface MesItemsDAO {
	
	/**
	 * Crée une nouvelle entrée dans la table de MesItems
	 * @param usager	: nom d'usager
	 * @param noItem	: numéro d'item
	 * @return			: nombre de rangees ajoutees (on s'attend a 1)
	 */	
	public int create(String usager, int noItem);
	
	/**
	 * Ajoute une nouvelle entrée dans la table de MesItems
	 * @param mesitems	: objet MesItems
	 * @return			: nombre de rangees ajoutees (on s'attend a 1)
	 */
	public int add(MesItems mesItems);
	
	/**
	 * Retourne la liste des MesItems pour un usager spécifique
	 * @param usager	: nom d'usager
	 * @return			: liste de MesItems
	 */
	public List<MesItems> getAll(String usager);
	
	/**
	 * Efface une entrée dans la table de MesItems
	 * selon les paramètres fournis.
	 * @param usager	: nom d'usager
	 * @param noItem	: numéro d'item
	 * @return			: nombre de rangees affectees (on s'attend a 1)
	 */
	public int delete(String usager, int noItem);
	
	/**
	 * Efface une entrée dans la table de MesItems
	 * @param mesitems	: objet MesItems
	 * @return			: nombre de rangees affectees (on s'attend a 1)
	 */
	public int delete(MesItems mesItems);		
	
	/**
	 * Efface toutes les entrées liées à un usager
	 * @param usager	: nom d'usager
	 * @return			: nombre de rangees affectees
	 */
	public int deleteAll(String usager);

	/**
	 * Retourne la liste des items référencés par MesItems pour un usager donné.
	 * @param usager 	: nom d'usager
	 * @return 			:Liste des objets Item
	 */
	public List<Item> getAllItemsByUser(String usager);
}
