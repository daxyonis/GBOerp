/**
 * 
 */
package services.dao;

import java.util.List;

import models.Rep;
import models.RepMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author Eva
* This class does only READ operations on the Genius (BODB) database
 * to get a list of reps (vendors)
 */
@Repository
@Transactional(readOnly=true)
public class RepDAOImpl implements RepDAO {
	
	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public RepDAOImpl(JdbcTemplate jdbcTempl){
		this.jdbcTemplateObject = jdbcTempl;
	}

	/* (non-Javadoc)
	 * @see services.simple.RepDAO#findAllActive()
	 */
	public List<Rep> findAllActive() {
		String SQL ="SELECT S.Salesman, S.Description1, S.Email " +
					"FROM BODB.dbo.vgMfiSalesmen S " +
					"WHERE (S.Description1 Is Not Null AND S.isActive=1) " +
					"ORDER BY S.Description1";
		List<Rep> reps = jdbcTemplateObject.query(SQL, new RepMapper());
		
		return reps;
	}

	/* (non-Javadoc)
	 * @see services.simple.RepDAO#findByCode(java.lang.String)
	 */
	public Rep findByCode(String code) {

		String SQL = "SELECT S.Salesman, S.Description1, S.Email " +
				"FROM BODB.dbo.vgMfiSalesmen S " +
				"WHERE S.Salesman = ?";
		
		Rep rep;
		
		try{
			rep = jdbcTemplateObject.queryForObject(SQL, new Object[]{code}, new RepMapper());
		}
		catch(EmptyResultDataAccessException e){
			rep = null;
		}
		
		return rep;
	}

	@Override
	public List<Rep> findByEmail(String email) {
		String SQL = "SELECT * FROM BODB.dbo.vgMfiSalesmen WHERE Email = ?";
		List<Rep> reps = jdbcTemplateObject.query(SQL, new Object[]{email},new RepMapper());
		
		return reps;
	}

	
}
