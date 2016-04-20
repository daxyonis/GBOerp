package services.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import models.Statut;
import models.StatutMapper;

@Repository
@Transactional
public class StatutDAOImpl implements StatutDAO {
	
	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public StatutDAOImpl(JdbcTemplate jdbcTempl){
		this.jdbcTemplateObject = jdbcTempl;		
	}

	@Override
	public List<Statut> findAll() {
		String SQL = "SELECT * FROM Statut ORDER BY Code";
		List<Statut> statuts = jdbcTemplateObject.query(SQL, new StatutMapper());
		return statuts;
	}

}
