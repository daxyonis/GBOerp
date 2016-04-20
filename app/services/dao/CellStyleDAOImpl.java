/**
 * 
 */
package services.dao;

import java.sql.ResultSet;
import java.util.List;

import models.CellStyle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.DataAccessException;

/**
 * @author Eva
 *
 */
@Repository
public class CellStyleDAOImpl implements CellStyleDAO {

	// The name of Table in the DB
	private final String TableName = "CssMaitreFlexibles";

	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public CellStyleDAOImpl(JdbcTemplate jdbcTempl){
		this.jdbcTemplateObject = jdbcTempl;
	}
	
	private class CellStyleMapper implements RowMapper<CellStyle>{
		public CellStyle mapRow(ResultSet rs, int rowNum) throws SQLException{
			CellStyle cellStyle = new CellStyle();
			cellStyle.setRowId(rs.getShort("RowId"));
			cellStyle.setColIndex(rs.getByte("ColIndex"));
			cellStyle.setCssClass(rs.getNString("CssClass"));
			cellStyle.setCssColor(rs.getNString("CssColor"));
			cellStyle.setCssBgColor(rs.getNString("CssBgColor"));			
			return cellStyle;
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see services.simple.CellStyleDAO#findAll()
	 */
	public List<CellStyle> findAll() {
		String SQL = "SELECT * FROM " + TableName;
	    List <CellStyle> cellcss = jdbcTemplateObject.query(SQL, new CellStyleMapper());
	    return cellcss;
	}

	/* (non-Javadoc)
	 * @see services.simple.CellStyleDAO#update(models.CellStyle)
	 */
	public boolean update(CellStyle style) {				
										
		int numrows = 0;
		
		String SQL = "UPDATE " + TableName + " SET CssClass=?, CssColor=?, CssBgColor=?";
		SQL += " WHERE (RowId = ? AND ColIndex = ?)";
		numrows = jdbcTemplateObject.update(SQL,(style.hasCssClass() ? style.getCssClass() : null), 
												(style.hasCssColor() ? style.getCssColor() : null),
												(style.hasCssBgColor() ? style.getCssBgColor() : null),
												 style.getRowId(), style.getColIndex());
		
		return(numrows == 1);
	}

	/* (non-Javadoc)
	 * @see services.simple.CellStyleDAO#add(models.CellStyle)
	 */
	public boolean add(CellStyle style) {

		if(style.hasStyles()){
			try{
			String SQL = "INSERT INTO " + TableName + " (RowId, ColIndex, CssClass, CssColor, CssBgColor) " +
						" values (?, ?, ?, ?, ?)";
			int numrows = jdbcTemplateObject.update( SQL, style.getRowId(), style.getColIndex(), 
													(style.hasCssClass() ? style.getCssClass() : null), 
													(style.hasCssColor() ? style.getCssColor() : null),
													(style.hasCssBgColor() ? style.getCssBgColor() : null));
			
			return (numrows == 1);
			}
			catch(DataAccessException e){
				// no fuss if we can't add a style
				return false;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see services.simple.CellStyleDAO#remove(int, int)
	 */
	public boolean remove(short row, byte column) {
		String SQL = "DELETE FROM " + TableName + " WHERE (RowId = ? AND ColIndex = ?)";
		int numrows = jdbcTemplateObject.update(SQL, row, column);
		return (numrows == 1);
	}

	/* (non-Javadoc)
	 * @see services.simple.CellStyleDAO#getByRowCol(short, byte)
	 */
	public CellStyle getByRowCol(short rowID, byte columnIndex) {
		String SQL = "SELECT * FROM " + TableName + " WHERE (RowId = ? AND ColIndex = ?)";
		CellStyle style = null;
		try{
			style = jdbcTemplateObject.queryForObject(SQL, new Object[]{rowID, columnIndex}, new CellStyleMapper());
		} catch(IncorrectResultSizeDataAccessException e){
			return null;
		} catch(DataAccessException e){
			return null;
		}
		return style;
	}
	
	

}
