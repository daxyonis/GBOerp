/**
 * 
 */
package services.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;

import models.Item;
import models.ItemMapper;

/**
 * @author Eva
 *
 */
@Repository	// registers this DAO as a bean in Spring IoC container
public class ItemDAOImpl implements ItemDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplateObject;	
	
	private SimpleJdbcInsert insertItem;
	
	@Autowired
	public ItemDAOImpl(DataSource ds){
		this.insertItem = new SimpleJdbcInsert(ds)
		.withTableName("Items")
		.usingColumns("NomItem","NoSoumission","Description","Quantité",
				  "SourceProd","FraisVariables","FraisInstallation",
				  "PrixFab","Hauteur","Largeur", "RectoVerso")
		.usingGeneratedKeyColumns("NoItem");
	}

	/* (non-Javadoc)
	 * @see services.simple.ItemDAO#create(int, java.lang.String, java.lang.String, int, java.lang.String, double, double, double, java.lang.String, java.lang.String)
	 */
	@Transactional
	public int create(String nom, int noSoumission, String description,
			int quantite, String sourceProd, double fraisVariables,
			double fraisInstallation, double prixFab, String hauteur,
			String largeur, String rectoVerso) {
		
		  Map<String, Object> parameters = new HashMap<String, Object>(10);
		  parameters.put("NomItem", nom);
		  parameters.put("NoSoumission", noSoumission);
		  parameters.put("Description", description);
		  parameters.put("Quantité", quantite);
		  parameters.put("SourceProd", sourceProd);
		  parameters.put("FraisVariables", new BigDecimal(fraisVariables).setScale(2, BigDecimal.ROUND_HALF_UP));
		  parameters.put("FraisInstallation", new BigDecimal(fraisInstallation).setScale(2, BigDecimal.ROUND_HALF_UP));
		  parameters.put("PrixFab", new BigDecimal(prixFab).setScale(2, BigDecimal.ROUND_HALF_UP));
		  parameters.put("Hauteur", hauteur);
		  parameters.put("Largeur", largeur);
		  parameters.put("RectoVerso", rectoVerso);
		
	    //****************************************************************
        // NB: if I don't set this to false, call to
        // executeAndReturnKey() throws a DataIntegrityViolationException
        // (whereas the insert with JdbcTemplate.update() works fine ! but we don't get the
        // generated NoSoumission... maybe I should use the OUTPUT CLAUSE
        //insertItem.setAccessTableColumnMetaData(false);
        //****************************************************************
        
    	Number newNoItem = insertItem.executeAndReturnKey(parameters);
		
    	return newNoItem.intValue();
	}

	/* (non-Javadoc)
	 * @see services.simple.ItemDAO#addItem(models.Item)
	 */
	@Transactional
	public int addItem(Item item) {		
		return this.create(item.getNomItem(), item.getNoSoumission(), item.getDescription(), 
				    item.getQuantite(), item.getSourceProd(), item.getFraisVariables(), 
				    item.getFraisInstallation(), item.getPrixFab(), item.getHauteur(), item.getLargeur(),
				    item.getRectoVerso());		
	}

	/* (non-Javadoc)
	 * @see services.simple.ItemDAO#getItem(int)
	 */
	public Item getItem(int noItem) {
		String SQL = "SELECT * FROM Items WHERE NoItem = ?";
	     Item item = jdbcTemplateObject.queryForObject(SQL, 
	                        new Object[]{noItem}, new ItemMapper());
	     return item;
	}

	/* (non-Javadoc)
	 * @see services.simple.ItemDAO#findAllItems()
	 */
	public List<Item> findAllItems() {
		String SQL = "SELECT * FROM Items ORDER BY NoItem";
	    List <Item> items = jdbcTemplateObject.query(SQL, new ItemMapper());
	    return items;
	}

	/* (non-Javadoc)
	 * @see services.simple.ItemDAO#findAllItemsFromSoum(int)
	 */
	public List<Item> findAllItemsFromSoum(int noSoumission) {
		String SQL = "SELECT * FROM Items WHERE NoSoumission = ? ORDER by NoItem";
	    List <Item> items = jdbcTemplateObject.query(SQL,  new Object[]{noSoumission}, new ItemMapper());
	    return items;
	}
	
	public List<Item> findByKeyword(KeyFindItemEnum key, String word) {		
		Object[] objArray;
		String SQL = "SELECT TOP 100 Items.* FROM Items";
		switch(key){		
		case NO_ITEM:
			// NoItem starts with word parameter
			SQL += " WHERE NoItem LIKE ?";
			objArray = new Object[]{word + "%"};
			break;
		case NOM_ITEM:
			SQL += " WHERE NomItem LIKE ? COLLATE Latin1_General_CI_AI ";
			objArray = new Object[]{"%" + word + "%"};
			break;			
		case DESCRIPTION:
			SQL += " WHERE Description LIKE ? COLLATE Latin1_General_CI_AI ";
			objArray = new Object[]{"%" + word + "%"};
			break;
		case FAMILLE:
			SQL += " WHERE Famille LIKE ? COLLATE Latin1_General_CI_AI ";
			objArray = new Object[]{"%" + word + "%"};
			break;
		case ALL:
		default:
			SQL +=" WHERE NoItem LIKE ?"
				+ " OR NomItem LIKE ? COLLATE Latin1_General_CI_AI "
				+ " OR Description LIKE ? COLLATE Latin1_General_CI_AI "
				+ " OR Famille LIKE ? COLLATE Latin1_General_CI_AI ";
			objArray = new Object[]{word + "%", "%" + word + "%", "%" + word + "%", "%" + word + "%"};			
		}
		SQL += " ORDER BY NoItem DESC";
		
		List <Item> items = jdbcTemplateObject.query(SQL, objArray, new ItemMapper());				
	    return items;
	}

	/* (non-Javadoc)
	 * @see services.simple.ItemDAO#delete(int)
	 */
	public int delete(int noItem) {
		String SQL = "DELETE FROM Items WHERE NoItem = ?";
        int numrows = jdbcTemplateObject.update(SQL, noItem);        
        return numrows;
		
	}

	/* (non-Javadoc)
	 * @see services.simple.ItemDAO#update(int, java.lang.String, java.lang.String, int, java.lang.String, double, double, double, java.lang.String, java.lang.String)
	 */
	public int update(int noItem, String nom, String description,
			int quantite, String sourceProd, double fraisVariables,
			double fraisInstallation, double prixFab, String hauteur,
			String largeur) {
		
		String SQL = "UPDATE Items SET NomItem = ?, Description = ?, " + 
					 "Quantité = ?, SourceProd = ?, FraisVariables = ?, " +
					 "FraisInstallation = ?, PrixFab = ?, Hauteur = ?, Largeur = ? " +
					 "where NoItem = ?";
        int numrows = jdbcTemplateObject.update(SQL, nom, description, 
        						quantite, sourceProd, fraisVariables, 
        						fraisInstallation, prixFab, hauteur, largeur, 
        						noItem);
        
        return numrows;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see services.simple.ItemDAO#update(models.Item)
	 */
	public int update(Item item) {
		return this.update(item.getNoItem(), item.getNomItem(), item.getDescription(), item.getQuantite(), 
				    item.getSourceProd(), item.getFraisVariables(), item.getFraisInstallation(), item.getPrixFab(), 
				    item.getHauteur(), item.getLargeur());
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see services.simple.ItemDAO#copyItemInfoToSoum(int[], int)
	 * NOTE : THIS METHOD MUST BE TRANSACTIONAL !!
	 */
	@Transactional
	public int[] copyItemInfoToSoum(int[] oldNoItemArray, int noSoumissionCible){
				
		String SQL = "INSERT INTO Items ( NoSoumission, NomItem, Description, Quantité, RectoVerso, " +
					 "SourceProd, CodeCatalogue, Copie, FraisVariables, FraisInstallation, Hauteur, " +
					 "Largeur, FichierSource, Notes, PrixFab, NoOrdre, SousCategorie, Famille, Facturation, GeniusItem ) " +
					 "SELECT Items.NoSoumission, Items.NomItem, Items.Description, Items.Quantité, Items.RectoVerso, " +
					 "Items.SourceProd, Items.CodeCatalogue, Items.NoItem, Items.FraisVariables, Items.FraisInstallation, " +
					 "Items.Hauteur, Items.Largeur, Items.FichierSource, Items.Notes, Items.PrixFab, Items.NoOrdre, " +
					 "Items.SousCategorie, Items.Famille, Items.Facturation, " + 
					 "CASE WHEN(NoItem = GeniusItem) THEN NULL ELSE GeniusItem END AS Expr1 " +
					 "FROM Items " +
					 "WHERE NoItem = ?";

		int numrows = 0;
		for(int i=0; i<oldNoItemArray.length; i++)
			numrows += jdbcTemplateObject.update(SQL, oldNoItemArray[i]);
		
		final int[] newNoItemArray = new int[oldNoItemArray.length];
		java.util.Arrays.fill(newNoItemArray, 0);
		
		if(numrows > 0){
			// Loop over all new copies of the old items to 
			// query the new noItem and then update
			// 1) NoSoumission = noSoumissionCible
			// 2) Copie = Null
			// 3) GeniusItem
			
			SQL ="UPDATE Items " +
				 "SET NoSoumission = ?, " +
				 "Copie = NULL, " +
				 "NoOrdre = 0, " + 
				 "GeniusItem = (CASE WHEN (GeniusItem = 182001 OR GeniusItem = 181998 "+
				 "Or GeniusItem = 150086 Or GeniusItem = 150090 "+
				 "Or GeniusItem = 150089 Or GeniusItem = 150085 "+
				 "Or GeniusItem = 150091 Or GeniusItem = 150087) THEN NoItem ELSE GeniusItem END) " +
				 "WHERE Copie = ?";
			
			for(int i=0; i<oldNoItemArray.length; i++){
				
				// First query the new noItem
				Integer newNoItem = jdbcTemplateObject.queryForObject("SELECT NoItem FROM Items WHERE Copie = ?", 
																	Integer.class, oldNoItemArray[i]);
				newNoItemArray[i] = newNoItem.intValue();
				
				// Then update
				numrows = jdbcTemplateObject.update(SQL, noSoumissionCible, oldNoItemArray[i]);
				if(numrows != 1)
					System.out.println("Update of item " + newNoItem + "(copy of " + oldNoItemArray[i] + " did not work.");
			}
		}
		else {
			// log an error
			System.out.println("Copy of items did not work.");
		}
		return newNoItemArray;
	}

}
