package services.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import models.Flexible;
import models.Imprimante;

@Repository
public class LienProduitImprimanteDAOImpl implements LienProduitImprimanteDAO {
	
	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public LienProduitImprimanteDAOImpl(JdbcTemplate jdbcTemplate){
		this.jdbcTemplateObject = jdbcTemplate;
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public boolean updateLien(Flexible flex) {
		boolean success = true;
		
		// First delete all links
		success = success && removeLien(flex);
		
		// Then add the relationships
		List<Imprimante> printers = flex.getImprimantes();
		if(success && !printers.isEmpty()){
			for(Imprimante printer : printers){
				String SQL = "INSERT INTO LienProduitImprimante (Type, ProdId, NoItemGenius, CodeSelection)"
						+ "VALUES (?,?,?,?)";
				int addedRow = jdbcTemplateObject.update( SQL, "Flexible", flex.getId(), 
														flex.getNoItemGenius(), 
														printer.getCodeSelection());
				success = success && (addedRow == 1);				
			}
		}
		
		return success;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=false)
	public boolean removeLien(Flexible flex) {
		String SQL = "DELETE FROM LienProduitImprimante WHERE Type = ? AND ProdId = ?";
		jdbcTemplateObject.update(SQL, "Flexible", flex.getId());
		return true;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=false)
	public boolean removeLienForProduct(String type, int id) {
		String SQL = "DELETE FROM LienProduitImprimante WHERE Type = ? AND ProdId = ?";
		jdbcTemplateObject.update(SQL, type, id);
		return true;
	}

}
