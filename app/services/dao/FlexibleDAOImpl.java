/**
 * 
 */
package services.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import models.Flexible;
import models.FlexibleMapper;
import models.Imprimante;

/**
 * @author Eva
 * Implementation of the FlexibleDAO interface (data access to Flexible objects)
 */
@Repository
public class FlexibleDAOImpl implements FlexibleDAO {
		
	// The name of Table in the DB
	private final String TableName = "MaitreFlexibles";
	@Autowired
	private JdbcTemplate jdbcTemplateObject;
	private LienProduitImprimanteDAO lienImprimanteDAO;
	
	// This is to insert and get back the id right away
	private SimpleJdbcInsert insertFlex;
	
	@Autowired
	public FlexibleDAOImpl(DataSource ds){
		this.insertFlex = new SimpleJdbcInsert(ds)
		.withTableName(TableName)
		.usingColumns("Categorie", "Utilite", "EnStock", "FicheTechniqueNom",
					  "FicheTechniqueLien", "Distributeur", "Produit", "NoItemGenius", "Epaisseur", "EpaisseurUnites",
					  "Format", "PrixCoutant1", "PrixCoutant2", "Description", "Caracteristiques", "InterieurExterieur",
					  "DureeSupport", "Impression", "CommentaireUtile","FormatMax")
		.usingGeneratedKeyColumns("Id");
	}
	
	@Autowired
	public void setLienProduitImprimanteDAO(LienProduitImprimanteDAO lpidao){
		this.lienImprimanteDAO = lpidao;
	}
		
	/* (non-Javadoc)
	 * @see services.simple.FlexibleDAO#findAll()
	 */
	public List<Flexible> findAll() {
		String SQL = "SELECT * FROM " + TableName + " ORDER BY Id";
	    List <Flexible> flexs = jdbcTemplateObject.query(SQL, new FlexibleMapper());
	    return flexs;
	}

	/* (non-Javadoc)
	 * @see services.simple.FlexibleDAO#getById(int)
	 */
	public Flexible getById(int id) {
		String SQL = "SELECT * FROM " + TableName + " WHERE Id = ?";
		Flexible flex = jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new FlexibleMapper());		
		return flex;
	}

	/* (non-Javadoc)
	 * @see services.simple.FlexibleDAO#update(models.Flexible)
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public boolean update(Flexible flex) {
		
		String SQL = "UPDATE " + TableName + " SET Categorie = ? , Utilite = ? , EnStock = ? , FicheTechniqueNom = ? , " +
		"FicheTechniqueLien = ? , Distributeur = ? , Produit = ? , 	NoItemGenius = ? , Epaisseur = ? , EpaisseurUnites = ? , " +
		"Format = ? , PrixCoutant1 = ? , PrixCoutant2 = ? , Description = ? , Caracteristiques = ? , InterieurExterieur = ? ," +
		"DureeSupport = ? , Impression = ? , CommentaireUtile = ?, FormatMax = ? WHERE Id = ?";
		
		// Update Impression 
		String impression = "";
		List<Imprimante> imprimantes = flex.getImprimantes();
		for(Imprimante imp : imprimantes){
			impression += imp.getCodeMachine().toLowerCase() + " ";
		}
				
		int numrows = jdbcTemplateObject.update(SQL, 
				flex.getCategorie(), flex.getUtilite(), flex.isEnStock(), flex.getFicheTechniqueNom(),
				flex.getFicheTechniqueLien(), flex.getDistributeur(), flex.getProduit(), flex.getNoItemGenius(), 
				flex.getEpaisseur(),flex.getEpaisseurUnites(),
				flex.getFormat(), flex.getPrixCoutant1(), flex.getPrixCoutant2(), flex.getDescription(), 
				flex.getCaracteristiques(), flex.getInterieurExterieur(),
				flex.getDureeSupport(), impression, flex.getCommentaireUtile(), flex.getFormatMax(),
				flex.getId()
				);				
		
		return ((numrows == 1) && lienImprimanteDAO.updateLien(flex));
	}

	/* (non-Javadoc)
	 * @see services.simple.FlexibleDAO#add(models.Flexible)
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public boolean add(Flexible flex) {	
		
		// Create Impression 
		String impression = "";
		List<Imprimante> imprimantes = flex.getImprimantes();
		for(Imprimante imp : imprimantes){
			impression += imp.getCodeMachine().toLowerCase() + " ";
		}
		
		// On fait un insert avec SimpleJdbcInsert.
		Map<String, Object> parameters = new HashMap<String, Object>(19);
		parameters.put("Categorie", flex.getCategorie());
		parameters.put("Utilite",flex.getUtilite());
		parameters.put("EnStock",flex.isEnStock());
		parameters.put("FicheTechniqueNom",flex.getFicheTechniqueNom());
		parameters.put("FicheTechniqueLien",flex.getFicheTechniqueLien());
		parameters.put("Distributeur",flex.getDistributeur());
		parameters.put("Produit",flex.getProduit()           );
		parameters.put("NoItemGenius",flex.getNoItemGenius()      );
		parameters.put("Epaisseur",flex.getEpaisseur()         );
		parameters.put("EpaisseurUnites",flex.getEpaisseurUnites()   );
		parameters.put("Format",flex.getFormat()            );
		parameters.put("PrixCoutant1",flex.getPrixCoutant1()      );
		parameters.put("PrixCoutant2",flex.getPrixCoutant2()      );
		parameters.put("Description",flex.getDescription()       );
		parameters.put("Caracteristiques",flex.getCaracteristiques()  );
		parameters.put("InterieurExterieur",flex.getInterieurExterieur());
		parameters.put("DureeSupport",flex.getDureeSupport()      );
		parameters.put("Impression",impression       );
		parameters.put("CommentaireUtile",flex.getCommentaireUtile()  );
		parameters.put("FormatMax", flex.getFormatMax());

		boolean success = true;
		try{
			Number newId = insertFlex.executeAndReturnKey(parameters);
			flex.setId(newId.shortValue());			
			success = success && lienImprimanteDAO.updateLien(flex);
		}
		catch(Exception e){
			success = false;
		}
		
		return success;
	}

	/* (non-Javadoc)
	 * @see services.simple.FlexibleDAO#remove(int)
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public boolean remove(int id) {
		lienImprimanteDAO.removeLienForProduct("Flexible", id);
		String SQL = "DELETE FROM " + TableName + " WHERE Id = ?";
		int numrows = jdbcTemplateObject.update(SQL, id);
		return (numrows == 1);
	}

	/* (non-Javadoc)
	 * @see services.simple.FlexibleDAO#remove(models.Flexible)
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public boolean remove(Flexible flex) {
		lienImprimanteDAO.removeLien(flex);
		String SQL = "DELETE FROM " + TableName + " WHERE Id = ?";
		int numrows = jdbcTemplateObject.update(SQL, flex.getId());
		return (numrows == 1);		
	}

	/* (non-Javadoc)
	 * @see services.simple.FlexibleDAO#getAllCategories()
	 */
	@Override
	public List<String> getAllCategories() {
		String SQL = "SELECT DISTINCT Categorie FROM " + TableName + " ORDER BY Categorie";
		List<String> categories = jdbcTemplateObject.queryForList(SQL, String.class);
		return categories;
	}

	
	

}
