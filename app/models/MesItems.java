package models;

/**
 * Classe MesItems
 * Représente la table tmpMesItems pour ajouter des items
 * choisis par un usager
 * @author Eva
 *
 */
public class MesItems {
	
	private String Usager;
	private int NoItem;
	
	/**
	 * @return the usager
	 */
	public String getUsager() {
		return Usager;
	}
	/**
	 * @param usager the usager to set
	 */
	public void setUsager(String usager) {
		Usager = usager;
	}
	/**
	 * @return the noItem
	 */
	public int getNoItem() {
		return NoItem;
	}
	/**
	 * @param noItem the noItem to set
	 */
	public void setNoItem(int noItem) {
		NoItem = noItem;
	}
	
	
}