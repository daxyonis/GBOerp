package services.dao;

import java.util.List;

import models.Laminage;
import models.LaminageMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LaminageDAOImpl implements LaminageDAO {
	
	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public LaminageDAOImpl(JdbcTemplate jdbcTempl){
		this.jdbcTemplateObject = jdbcTempl;
	}

	@Override
	public List<Laminage> findAllForCategory(String productCategory) {
	
		String SQL =  "SELECT L.* FROM Laminage L "
					+ "JOIN LienCategorieLaminage CL ON CL.LaminageID = L.Id "
					+ "WHERE CL.Categorie = ?";
		
		List<Laminage> laminages = jdbcTemplateObject.query(SQL, new Object[]{productCategory}, new LaminageMapper());
		
		return laminages;
	}

	@Override
	public Laminage findById(int id) {
		String SQL = "SELECT * FROM Laminage WHERE Id = ?";
		Laminage lam;
		try{
			lam = jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new LaminageMapper());
		}catch(EmptyResultDataAccessException e){
			lam = null;
		}
		
		return lam;
	}

}
