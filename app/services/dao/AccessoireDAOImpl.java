/**
 * 
 */
package services.dao;

import java.util.List;
import models.Accessoire;
import models.AccessoireMapper;
import models.Type;
import models.TypeMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author Eva
 *
 */
@Repository
public class AccessoireDAOImpl implements AccessoireDAO {
	
	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public AccessoireDAOImpl(JdbcTemplate jdbcTempl){
		this.jdbcTemplateObject = jdbcTempl;
	}

	/* (non-Javadoc)
	 * @see services.simple.AccessoireDAO#findAll()
	 */
	public List<Accessoire> findByType(String pType) {
		String SQL = "SELECT * FROM Accessoire WHERE Type = ? ORDER BY Id";
		List<Accessoire> accessoires = jdbcTemplateObject.query(SQL, new Object[]{pType}, new AccessoireMapper());
		return accessoires;
	}
	
	public List<Type> findTypes(){
		String SQL = "SELECT DISTINCT type FROM Accessoire ORDER BY type";
		List<Type> types = jdbcTemplateObject.query(SQL, new TypeMapper());
		return types;
	}

	/* (non-Javadoc)
	 * @see services.simple.AccessoireDAO#findById(int)
	 */
	public Accessoire findById(int id) {
		String SQL = "SELECT * FROM Accessoire WHERE Id = ?";
		Accessoire acc;
		try{
			acc = jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new AccessoireMapper());
		}
		catch(EmptyResultDataAccessException e){
			acc = null;
		}
		
		return acc;
	}

}
