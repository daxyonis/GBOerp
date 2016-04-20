/**
 * 
 */
package services.dao;

import java.util.List;
import models.CellStyle;

/**
 * @author Eva
 * Interface to provide data access to CellStyle objects
 */
public interface CellStyleDAO {
	
	/**
	 * Get all the cell styles that exist
	 * @return	a java.util.List of CellStyle objects
	 */
	public List<CellStyle> findAll();
	
	/**
	 * Updates an existing CellStyle object
	 * @param style		CellStyle object to persist
	 * @return			true if update was successful; false otherwise
	 */
	public boolean update(CellStyle style);
	
	/**
	 * Adds a new CellStyle object
	 * @param style		CellStyle object to persist
	 * @return			true if add was successful; false otherwise
	 */
	public boolean add(CellStyle style);
	
	/**
	 * Removes a cell style
	 * @param rowId			the row id (unsigned short value)
	 * @param columnIndex	the column number (unsigned byte value)
	 * @return			true if removal was successful; false otherwise
	 */
	public boolean remove(short rowId, byte columnIndex);

	/**
	 * Gets a cell style, as specified by its row and column
	 * @param rowID			the row id of the cell (unsigned short value)
	 * @param columnIndex	the column index of the cell (unsigned byte value)
	 * @return				the CellStyle object corresponding to this row and column
	 */
	public CellStyle getByRowCol(short rowID, byte columnIndex);
}
