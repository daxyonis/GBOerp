package services.app.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import models.app.AppItem;
import models.app.AppItemMapper;

@Repository
public class AppItemDAOImpl implements AppItemDAO {
	
	private JdbcTemplate jdbcTemplateObject;
	private SimpleJdbcInsert insertItem;
	
	@Autowired
	public AppItemDAOImpl(JdbcTemplate jdbcTempl, DataSource ds){
		this.jdbcTemplateObject = jdbcTempl;
		this.insertItem = new SimpleJdbcInsert(ds)
		.withTableName("AppItem")
		.usingColumns("NomItem","NoSoumission","Description","Quantité",
			      "SourceProd","FraisVariables","FraisInstallation",
			      "PrixFab","Hauteur","Largeur", "RectoVerso", "NoOrdre")
		.usingGeneratedKeyColumns("NoItem");
	}

	@Override
	public Optional<AppItem> findByNo(int noItem) {
		String SQL = "SELECT * FROM AppItem WHERE NoItem=?";
		AppItem item;
		try{
			item = jdbcTemplateObject.queryForObject(SQL, new Object[]{noItem}, new AppItemMapper());
		}
		catch(EmptyResultDataAccessException e){
			// if not found, return null
			item = null;
		}		
		return Optional.ofNullable(item);
		
	}

	@Override
	public List<AppItem> findAllForSoum(int noSoum) {
		String SQL = "SELECT * FROM AppItem WHERE NoSoumission = ? ORDER BY NoOrdre";
		List <AppItem> items = jdbcTemplateObject.query(SQL,  new Object[]{noSoum}, new AppItemMapper());
	    return items;
	}

	@Override
	@Transactional
	public boolean save(AppItem item) {
		String SQL = "UPDATE AppItem SET " + 
					 "NomItem = ?, Description = ?, Quantité = ?, RectoVerso = ?, " +
					 "SourceProd = ?, FichierSource = ?, FraisVariables = ?, " +
				 	 "FraisInstallation = ?, PrixFab = ?, Hauteur = ?, Largeur = ?, " +
				 	 "Notes = ?, NotesInternes = ?, SousCategorie = ?, Famille = ?, Facturation=?, " +
				 	 "CodeCatalogue = ?, Catalogue = ?, NoOrdre = ? " +
				 	 "WHERE NoItem = ?";
		
		int numrows = jdbcTemplateObject.update(SQL, 
					item.getNomItem(), item.getDescription(), item.getQuantite(), item.getRectoVerso(),
					item.getSourceProd(), item.getFichierSource(), item.getFraisVariables(),
					item.getFraisInstallation(), item.getPrixFab(), item.getHauteur(), item.getLargeur(),
					item.getNotes(), item.getNotesInternes(), item.getSousCategorie(), item.getFamille(),
					item.getFacturation(), item.getCodeCatalogue(), item.isCatalogue(), item.getNoOrdre(),
					item.getNoItem());
   
		return(numrows==1);
	}

	@Override	
	@Transactional(propagation=Propagation.MANDATORY, readOnly=false)
	public int add(AppItem item) {
		Map<String, Object> parameters = new HashMap<String, Object>(10);
		parameters.put("NomItem", item.getNomItem());		
		parameters.put("NoSoumission", item.getNoSoumission());
		parameters.put("Description", item.getDescription());
		parameters.put("Quantité", item.getQuantite());
		parameters.put("SourceProd", item.getSourceProd());
		parameters.put("FraisVariables", new BigDecimal(item.getFraisVariables()).setScale(4, BigDecimal.ROUND_HALF_UP));
		parameters.put("FraisInstallation", new BigDecimal(item.getFraisInstallation()).setScale(4, BigDecimal.ROUND_HALF_UP));
		parameters.put("PrixFab", new BigDecimal(item.getPrixFab()).setScale(4, BigDecimal.ROUND_HALF_UP));
		parameters.put("Hauteur", item.getHauteur());
		parameters.put("Largeur", item.getLargeur());
		parameters.put("RectoVerso", item.getRectoVerso());
		parameters.put("NoOrdre", item.getNoOrdre());
		  
		Number newNoItem = insertItem.executeAndReturnKey(parameters);
			
	    return newNoItem.intValue();
	}

	@Override
	@Transactional
	public boolean remove(AppItem item) {
		String SQL = "DELETE FROM AppItem WHERE NoItem = ?";
        int numrows = jdbcTemplateObject.update(SQL, item.getNoItem());        
        return (numrows == 1);
	}

	
}
