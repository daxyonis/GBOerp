/**
 * 
 */
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.dao.CellStyleDAO;
import models.CellStyle;

/**
 * @author Eva
 *
 */
@Service
public class CellStyleServiceImpl implements CellStyleService {

	private CellStyleDAO cellStyleDAO;
	
	@Autowired
	public CellStyleServiceImpl(CellStyleDAO cellDAO){
		this.cellStyleDAO = cellDAO;
	}
	
	/* (non-Javadoc)
	 * @see services.CellStyleService#findAll()
	 */
	public List<CellStyle> findAll() {
		return cellStyleDAO.findAll();
	}

	/* (non-Javadoc)
	 * @see services.CellStyleService#update(models.CellStyle)
	 */
	public boolean update(CellStyle cellStyle) {
		return cellStyleDAO.update(cellStyle);
	}

	/* (non-Javadoc)
	 * @see services.CellStyleService#add(models.CellStyle)
	 */
	public boolean add(CellStyle cellStyle) {
		return cellStyleDAO.add(cellStyle);
	}

	/* (non-Javadoc)
	 * @see services.CellStyleService#remove(short, byte)
	 */
	public boolean remove(short rowId, byte colIndex) {
		return cellStyleDAO.remove(rowId, colIndex);
	}

	/* (non-Javadoc)
	 * @see services.CellStyleService#getByRowCol(short, byte)
	 */
	public CellStyle getByRowCol(short rowId, byte colIndex) {
		return cellStyleDAO.getByRowCol(rowId, colIndex);
	}

	
}
