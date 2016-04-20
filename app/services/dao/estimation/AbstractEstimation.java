package services.dao.estimation;

/**
 * Abstract class that dictates common behavior between
 * all subclasses of estimation (such as AssemblageDAO, CNCDAO, etc)
 * @author Eva
 *
 */
public abstract class AbstractEstimation implements Estimation{
	
	public void copyEstimation(int[] oldNoItemArray, int[] newNoItemArray) {
		this.createCopyFromItem(oldNoItemArray);
		this.updateNoItem(oldNoItemArray, newNoItemArray);
	}

	/**
	 * This creates a copy of all the SoumXXX
	 * related to all the noItem in a list
	 * 
	 * @param oldNoItemArray	the list of Item.noItem for the items that we copy from
	 */
	protected abstract void createCopyFromItem(int[] oldNoItemArray);

	/**
	 * This updates the SoumXXX.noItem from old Item.noItem (the item that is copied)
	 * to new Item.noItem (the copy of the old item)
	 * 
	 * @param oldNoItemArray
	 * @param newNoItemArray
	 */
	protected abstract void updateNoItem(int[] oldNoItemArray, int[] newNoItemArray);
}
