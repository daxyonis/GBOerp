/**
 * 
 */
package services;

import java.util.List;
import models.CellStyle;

/**
 * Provides services for the CellStyle
 * @author Eva
 *
 */
public interface CellStyleService {
	
	/**
	 * Find all the CellStyle objects that exist
	 * @return  java.util.List of all the CellStyle objects
	 */
	public List<CellStyle> findAll();
	
	/**
	 * Updates an existing CellStyle object
	 * @param cellStyle   CellStyle object to persist
	 * @return   true if update was successful; false otherwise
	 */
	public boolean update(CellStyle cellStyle);
	
	/**
	 * Adds a new CellStyle object
	 * @param  cellStyle   CellStyle object to persist
	 * @return   true if add was successful; false otherwise
	 */
	public boolean add(CellStyle cellStyle);
	
	/**
	 * Removes a CellStyle object
	 * @param rowId		the row id of the cell
	 * @param colIndex	the column index of the cell
	 * @return	true if removal was successful; false otherwise
	 */
	public boolean remove(short rowId, byte colIndex);
	
	/**
	 * Find a CellStyle object as specified by its row and column
	 * @param rowId		the row id of the cell
	 * @param colIndex	the column index of the cell
	 * @return			the CellStyle object
	 */
	public CellStyle getByRowCol(short rowId, byte colIndex);
}
