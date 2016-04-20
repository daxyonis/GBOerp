package services.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import models.ProdFamily;

/**
 * @author Eva
* This class does only READ operations on the Genius (BODB) database
 * to get a list of product families
 */
@Repository
@Transactional(readOnly=true)
public class ProdFamilyDAOImpl implements ProdFamilyDAO {

	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public ProdFamilyDAOImpl(JdbcTemplate jdbcTempl){
		this.jdbcTemplateObject = jdbcTempl;
	}
	
	private class ProdFamilyMapper implements RowMapper<ProdFamily>{
		public ProdFamily mapRow(ResultSet rs, int rowNum) throws SQLException{
			ProdFamily pf = new ProdFamily();
			pf.setProductCategory(rs.getString("ProductCategory"));
			pf.setFamily(rs.getString("Family"));
			pf.setDescription1(rs.getString("Description1"));
			return pf;
		}
	}
	
	@Override
	public List<ProdFamily> findAll() {
		String SQL = "SELECT ProductCategory, Family, Description1 "
					+ "FROM BODB.dbo.vgMfiFamilies "
					+ "WHERE ProductCategory='PF' "
					+ "ORDER BY Family ";
		
		List<ProdFamily> families = jdbcTemplateObject.query(SQL,  new ProdFamilyMapper());
		return families;
	}

}
