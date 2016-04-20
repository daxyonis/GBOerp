/**
 * 
 */
package services.dao;

import java.util.List;

import models.Finition;
import models.FinitionMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author Eva
 * DAO implementation for Finition 
 */
@Repository
public class FinitionDAOImpl implements FinitionDAO {
	
	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public FinitionDAOImpl(JdbcTemplate jdbcTempl){
		this.jdbcTemplateObject = jdbcTempl;
	}

	/* (non-Javadoc)
	 * @see services.simple.FinitionDAO#findAll()
	 */
	public List<Finition> findAllActive() {
		String SQL = "SELECT * FROM Finition WHERE Active=1 ORDER BY Id";
		List<Finition> finitions = jdbcTemplateObject.query(SQL, new FinitionMapper());
		return finitions;
	}
	
	public List<Finition> findAll() {
		String SQL = "SELECT * FROM Finition ORDER BY Id";
		List<Finition> finitions = jdbcTemplateObject.query(SQL, new FinitionMapper());
		return finitions;
	}

	/* (non-Javadoc)
	 * @see services.simple.FinitionDAO#getById(int)
	 */
	public Finition getById(int id) {
		String SQL = "SELECT * FROM Finition WHERE Id=?";
		Finition fini;
		try{
			fini = jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new FinitionMapper());
		} catch(EmptyResultDataAccessException e){
			fini = null;
		}
		return fini;
	}

	@Override
	public boolean save(Finition fini) {
		String SQL = "UPDATE Finition SET "
				+ "CodeGBC = ?, Type = ?, Taille = ?, Cotes1 = ?, Cotes2 = ?, Prix = ?, Unite = ?, Active = ?, CoutFixe = ? "
				+ "WHERE Id = ?";
		int numrows = jdbcTemplateObject.update(SQL, fini.getCodeGBC(), fini.getType(), fini.getTaille(),
				fini.getCotes1(), fini.getCotes2(), fini.getPrix(), fini.getUnite(), fini.isActive(), fini.getCoutFixe(),
				fini.getId());
		
		return (numrows == 1);		
	}

	@Override
	public boolean add(Finition fini) {
		String SQL = "INSERT INTO Finition (CodeGBC, Type, Taille, Cotes1, Cotes2, Prix, Unite, Active, CoutFixe) "
					+ "VALUES (?,?,?,?,?,?,?,?,?)";
		int numrows = jdbcTemplateObject.update(SQL, fini.getCodeGBC(), fini.getType(), fini.getTaille(),
				fini.getCotes1(), fini.getCotes2(), fini.getPrix(), fini.getUnite(), fini.isActive(), fini.getCoutFixe());
		return (numrows == 1);
	}

	@Override
	public boolean remove(Finition fini) {
		String SQL = "DELETE FROM Finition WHERE Id = ?";
		int numrows = jdbcTemplateObject.update(SQL, fini.getId());
		return (numrows == 1);
	}
	
	

}
