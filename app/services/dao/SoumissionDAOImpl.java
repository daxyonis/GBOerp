/**
 * 
 */
package services.dao;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

//import play.Logger;

import models.Client;
import models.Soumission;
import models.SoumissionMapper;

/**
 * @author Eva
 *
 */
@Repository
public class SoumissionDAOImpl implements SoumissionDAO {
	
	/**
	 * This enum describes possible query types
	 * SEARCH : search query for returning a list of data
	 * COUNT  : count query returning an int 
	 */
	private static enum QueryTypeSoumEnum {SEARCH, COUNT}
	
	
	@Autowired
	private JdbcTemplate jdbcTemplateObject;
	
	private SimpleJdbcInsert insertSoumission;
			
	@Autowired
	public SoumissionDAOImpl(DataSource ds){
		this.insertSoumission = new SimpleJdbcInsert(ds)
								.withTableName("Soumission")
								.usingColumns("Projet","NoVendeur","GeniusNoClient","DateEntre",	// champs obligatoires
					  				  "DateLivraison","commission","Prix",	// champs obligatoires
					  				"Contact","ClientTelephoneContact","ClientEmail")	// non obligatoires	
        						.usingGeneratedKeyColumns("NoSoumission");		
	}
	
	public Soumission findByNo(int noSoum) {
		String SQL = "SELECT * FROM Soumission WHERE NoSoumission = ?";		
		
		try{
		    Soumission soum = jdbcTemplateObject.queryForObject(SQL, 
		                        new Object[]{noSoum}, new SoumissionMapper(SoumissionMapper.FieldScopeEnum.ALL));
		    return soum;
		}
		catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	public List<Soumission>	findByKeyword(KeyFindSoumEnum key, 
										  String word,
										  String minDate,
										  String maxDate,
										  String clientNo,
										  String repNo){
		List<Object> objList = new ArrayList<Object>();
		String SQL = "SELECT TOP 100 "
				+ "NoSoumission, Suite, Version, Projet, Statut, "
				+ "NoDossier, BanniereClient, Critere, ClientSoum, DateEntre, "
				+ "NoVendeur, GeniusNoClient "
				+ "FROM Soumission";
		switch(key){		
		case NO_SOUM:
			// NoSoumission starts with word parameter
			SQL += " WHERE (NoSoumission LIKE ?)";
			objList.add(word + "%");
			break;
		case SUITE:
			// Suite starts with word parameter
			SQL += " WHERE (Suite LIKE ?)";
			objList.add(word + "%");
			break;			
		case PROJET:
			SQL += " WHERE (Projet LIKE ? COLLATE Latin1_General_CI_AI) ";
			objList.add("%" + word + "%");
			break;
		case CLIENT:
			SQL += " WHERE (ClientSoum LIKE ? COLLATE Latin1_General_CI_AI) ";
			objList.add("%" + word + "%");
			break;
		case ALL:
		default:
			SQL +=" WHERE (NoSoumission LIKE ? OR Suite LIKE ? "
				+ " OR Projet LIKE ? COLLATE Latin1_General_CI_AI"
				+ " OR ClientSoum LIKE ? COLLATE Latin1_General_CI_AI)";
			objList.add(word + "%");
			objList.add(word + "%");
			objList.add("%" + word + "%");
			objList.add("%" + word + "%");			
		}		
		if(!minDate.isEmpty() && !maxDate.isEmpty()){
			SQL += " AND (DateEntre BETWEEN ? AND ?)";
			objList.add(minDate);
			objList.add(maxDate);
		}
		else if(!minDate.isEmpty()){			
			SQL += " AND (DateEntre >= ?)";
			objList.add(minDate);
			}			
		else if(!maxDate.isEmpty()){
			SQL += " AND (DateEntre <= ?)";
			objList.add(maxDate);
			}		
		if(!clientNo.isEmpty()){
			SQL += " AND (GeniusNoClient = ?)";
			objList.add(clientNo);
		}
		if(!repNo.isEmpty()){
			SQL += " AND (NoVendeur = ?)";
			objList.add(repNo);
		}
		SQL += " ORDER BY Suite DESC, Version ASC";
		
		
		List <Soumission> soums = jdbcTemplateObject.query(SQL, 
								  objList.toArray(), 
								  new SoumissionMapper(SoumissionMapper.FieldScopeEnum.OVERVIEW));				
	    return soums;
	}
	
	
	public List<Soumission> findTop(int numrows, KeyFindSoumEnum orderBy){		
		
		String SQL = "SELECT TOP " + numrows + " Soumission.* FROM Soumission";
		
		switch(orderBy){
		case NO_SOUM:
			SQL += " ORDER BY NoSoumission DESC";	
			break;
		case PROJET:
			SQL += " ORDER BY Projet";
			break;
		case CLIENT:
			SQL += " ORDER BY ClientSoum";
			break;
		case SUITE:
			SQL += "ORDER BY Suite DESC, Version ASC";
			break;
		case ALL:	
			// Note : we do not order by NoSoumission then, only by Suite,Version
			default:
				SQL += " ORDER BY Suite DESC, Version, Projet, ClientSoum ";
		}
		
		List <Soumission> soums = jdbcTemplateObject.query(SQL,
								  new SoumissionMapper(SoumissionMapper.FieldScopeEnum.ALL));
		return soums;
	}

	/* (non-Javadoc)
	 * @see services.simple.SoumissionDAO#create(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Date, java.util.Date, float, float)
	 SimpleDateFormat  */
	@Transactional
	public int create(String projet, String noVendeur, String geniusNoClient,
			String contact, String clientTelephoneContact, String clientEmail,			
			Date dateEntre, Date dateLivraison, float commission, double prix) {					
		
		// First we do an insert with id return
		Map<String, Object> parameters = new HashMap<String, Object>(10);
        parameters.put("Projet", projet);
        parameters.put("NoVendeur", noVendeur);
        parameters.put("GeniusNoClient", geniusNoClient);
        parameters.put("DateEntre", dateEntre);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");        
        String formattedDateLivraison = format1.format(dateLivraison);
        parameters.put("DateLivraison", formattedDateLivraison);	// TBM : dateLivraison is a string DD/mm/yyyy
        parameters.put("Commission", commission);
        parameters.put("Prix",new BigDecimal(prix).setScale(2, BigDecimal.ROUND_HALF_UP));
        
        if(!contact.isEmpty()){
        	parameters.put("Contact", contact);
        }
        if(!clientTelephoneContact.isEmpty()){
        	parameters.put("ClientTelephoneContact", clientTelephoneContact);
        }
        if(!clientEmail.isEmpty()){
        	parameters.put("ClientEmail", clientEmail);
        }
        
        
        //****************************************************************
        // NB: if I don't set this to false, call to
        // executeAndReturnKey() throws a DataIntegrityViolationException
        // (whereas the insert with JdbcTemplate.update() works fine ! but we don't get the
        // generated NoSoumission... maybe I should use the OUTPUT CLAUSE
        insertSoumission.setAccessTableColumnMetaData(false);
      //****************************************************************
        
    	Number newNoSoum = insertSoumission.executeAndReturnKey(parameters);
        
        
        // Then once the Soumission is created we update some fields
		String SQL = "UPDATE Soumission " +
					 "SET Suite = (SELECT MAX(Suite)+1 FROM Soumission), " +
					 "Version = 'A', " +
					 "DateOuverture = ?, DateDebutEstimation = ?, " +
					 "NomVendeur = (SELECT Description1 FROM BODB.dbo.vgMfiSalesmen WHERE Salesman = ?), " +
					 "Client = (SELECT Name FROM BODB.dbo.vgMfiCustomers WHERE Customer = ?), " +
					 "ClientSoum = (SELECT Name FROM BODB.dbo.vgMfiCustomers WHERE Customer = ?), " +
					 "Critere = 'SOUM-RAPIDE-FLEX' " + 					 
					 "WHERE NoSoumission = ?";					  
		
		jdbcTemplateObject.update( SQL, dateEntre, dateEntre, noVendeur, 
								   geniusNoClient, geniusNoClient, 
								   newNoSoum.intValue());
				
		return newNoSoum.intValue();
	}

	/**
	 * Builds the query given several search criteria.
	 * @param queryType     the type of query
	 * @param objList       an array of object to fill up with parameters
	 * @param search		text search keyword
	 * @param sort          the field by which to sort
	 * @param order			order in which to sort Suite, Version
	 * @param repNo			the rep id
	 * @param statusCode    the status code
	 * @param numero		the soum id or order id
	 * @param minDate       
	 * @param maxDate
	 * @param startRow     for SEARCH type queries, the starting row in page (ignored for COUNT queries)
	 * @param endRow       for SEARCH type queries, the end row in page (ignored for COUNT queries)
	 * @return
	 */
	private String buildQuery(QueryTypeSoumEnum queryType, 
							  List<Object> objList,
							  String search, String sort, String order, 
							  String repNo, 
							  int statusCode, int numero,
							  String minDate, String maxDate,
							  int startRow, int endRow){
		
		String SQL = "";
					
		switch(queryType){
		case SEARCH:
			String sortField = "";
			String chosenOrder = order.equalsIgnoreCase("desc") ? "desc" : "asc";
			
			try{
				sortField = Soumission.getSortableDBFieldNameFor(sort);
			}
			catch(IllegalArgumentException e){
				sortField = "Suite";
			}
			if(sortField.equals("Suite")){
				sortField = "Suite " + chosenOrder + ", Version " + chosenOrder;
			}
			else{
				sortField = sortField + " " + chosenOrder;
			}
			
			// Begin the CTE
			SQL = "WITH searchQ AS ("
				+ "SELECT *, "
				+ "ROW_NUMBER() OVER (order by " + sortField + ") AS 'RowNumber' "
				+ "FROM vboSoumOverview ";
			
			
			break;
		case COUNT:
			// Begin the count query
			SQL = "SELECT COUNT(NoSoumission) FROM vboSoumOverview ";
			break;
			default:
				return SQL;
		}
		
		boolean isSearch = (search.isEmpty()  == false);
		boolean isRep = (repNo.equals("NA")== false);
		boolean isStatus = (statusCode > 0); 
		boolean isNum = (numero > 0);
		boolean isMinDate = (minDate.isEmpty() == false);
		boolean isMaxDate = (maxDate.isEmpty() == false);

		if(isSearch || isRep || isStatus || isNum || isMinDate || isMaxDate){
			SQL += "WHERE ";
			String linkWord = "";			
			
			if(isRep){
				SQL += "(NoVendeur = ?) ";
				objList.add(repNo);
				linkWord = "AND ";
			}
			
			if(isStatus){
				SQL += linkWord + "(Statut = ?) ";
				objList.add(statusCode);
				linkWord = "AND ";
			}
			
			if(isNum){
				SQL += linkWord + "(Suite = ? OR NoDossier = ?) ";
				objList.add(numero);
				objList.add(numero);
				linkWord = "AND ";
			}
			
			if(isSearch){
				// Split using the '+' sign (since it is a regex operator we put backslashes to escape it)
				String [] keywords = search.split("\\+");
				for(String word : keywords){
					SQL += linkWord + "(Recherche LIKE ? COLLATE Latin1_General_CI_AI)";				
					objList.add("%" + word.trim() + "%");
					linkWord = "AND ";
				}
			}
			
			if(isMinDate && isMaxDate){
				SQL += linkWord + "(DateEntre BETWEEN ? AND ?) ";
				objList.add(minDate);
				objList.add(maxDate);
				linkWord = "AND ";
			}
			else if(isMinDate){			
				SQL += linkWord + "(DateEntre >= ?) ";
				objList.add(minDate);
				linkWord = "AND ";
				}			
			else if(isMaxDate){
				SQL += linkWord + "(DateEntre <= ?) ";
				objList.add(maxDate);
				}	
		}
		
		if(queryType == QueryTypeSoumEnum.SEARCH){
			// Terminate the CTE
			SQL += ") ";
					
			// Now select final result from the CTE
			SQL +="SELECT * FROM searchQ "
				+ "WHERE RowNumber between ? and ? "
				+ "ORDER BY RowNumber ";
			objList.add(startRow);
			objList.add(endRow);
		}
		
		return (SQL);
	}
	/**
	 * SEARCHING method for soumission search
	 * Return a subset of Soumission given several search criteria
	 * We use the view vboSoumOverview in order to gain performance in search
	 * Also non-clustered indexes should exist on the following columns of Soumission : 
	 * NoVendeur, Statut, DateEntre, GeniusNoClient, Suite, NoDossier
	 * getPage()
	 */
	@Override
	public List<Soumission> getPage(String search, String sort, String order, int pageSize, int pageNumber,
									String repNo, int statusCode, int numero,
									String minDate, String maxDate) {
			
		int startRow = (pageNumber -1) * pageSize + 1;
		int endRow = pageNumber * pageSize;  						
		
		List<Object> objList = new ArrayList<Object>();
		String SQL = buildQuery(QueryTypeSoumEnum.SEARCH, objList, search, sort, order, 
							   repNo, statusCode, numero, minDate, maxDate,
							   startRow, endRow);
		
		// Log some debug info
		//Logger.debug("SQL request is : " + SQL);
				
		List<Soumission> soums = this.jdbcTemplateObject.query(SQL, 
									  objList.toArray(), 
									  new SoumissionMapper(SoumissionMapper.FieldScopeEnum.OVERVIEW));		
		
		return soums;
		
	}

	/**
	 * COUNTING method for soumission search
	 */
	@Override
	public int countAll(String search, String repNo, int statusCode, int numero, String minDate, String maxDate) {		
		
		
		Integer count;
		try{
			List<Object> objList = new ArrayList<Object>();
			String SQL = buildQuery(QueryTypeSoumEnum.COUNT, objList, search, "", "",
					   repNo, statusCode, numero, minDate, maxDate,
					   0, 0);
			
			// Log some debug info
			//Logger.debug("SQL request is : " + SQL);
			
			if(objList.isEmpty()){
				count = this.jdbcTemplateObject.queryForObject(SQL,  Integer.class);
			}
			else{				
				count = this.jdbcTemplateObject.queryForObject(SQL, objList.toArray(), Integer.class);
			}
		}
		catch(EmptyResultDataAccessException e){
			count = Integer.valueOf(0);
		}
		
		return count.intValue();
	}

	@Override
	public int copyClientInfo(int noSoum, Client client) {
		String SQL = "UPDATE Soumission SET "
				   + "ClientAdresse = ?, ClientTelephone = ?, ClientFax = ?, ClientVille = ?, "
				   + "ClientCP = ?, ClientProvince = ?, ClientPays = ?, "
				   + "LIVRLieu = ?, LIVRAdresse = ?, LIVRTelephone = ?, LIVRVille = ?, LIVRCP = ?, LIVRProvince = ?, LIVRProvinceCode = ?, LIVRPays = ?, "
				   + "ClientFactureA = ?, FACTAdresse = ?, FACTTelephone = ?, FACTVille = ?, FACTCP = ?, FACTProvince = ?, FACTProvinceCode = ?, FACTPays = ? "
				   + "WHERE NoSoumission = ?";
		
		String adr = client.getAdresse();
		String tel = client.getTelephone();
		String ville = client.getVille();
		String cp = client.getCodePostal();
		String prov = client.getProvince();
		String pays = client.getPays();
		String nom = client.getNom();
		String provcode = client.getProvinceCode();
		
		return jdbcTemplateObject.update(SQL, 
				adr, tel, client.getFax(), ville, 
				cp, prov, pays, 
				nom, adr, tel, ville, cp, prov, provcode, pays, 
				nom, adr, tel, ville, cp, prov, provcode, pays,
				noSoum);
				
	}
	
	
}
