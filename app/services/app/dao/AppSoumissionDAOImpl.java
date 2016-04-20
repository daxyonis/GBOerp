package services.app.dao;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import models.Soumission;
import models.app.AppSoumission;
import models.app.AppSoumissionMapper;

@Repository
@Transactional
public class AppSoumissionDAOImpl implements AppSoumissionDAO {
	
	private JdbcTemplate jdbcTemplateObject;
	private SimpleJdbcInsert insertSoumission;
	
	/**
	 * This enum describes possible query types
	 * SEARCH : search query for returning a list of data
	 * COUNT  : count query returning an int 
	 */
	private static enum QueryTypeSoumEnum {SEARCH, COUNT}
	
	@Autowired
	public AppSoumissionDAOImpl(JdbcTemplate jdbcTempl, DataSource ds){
		this.jdbcTemplateObject = jdbcTempl;
		this.insertSoumission = new SimpleJdbcInsert(ds)
								.withTableName("AppSoumission")
								.usingColumns("Projet","NoVendeur","NomVendeur",// champs obligatoires
											  "GeniusNoClient","DateEntre",		// champs obligatoires
										  	  "Commission") 	// champs obligatoires										  	  
								.usingGeneratedKeyColumns("NoSoumission");	
	}

	@Override
	public Optional<AppSoumission> findByNo(int noSoum) {
		String SQL = "SELECT * FROM AppSoumission WHERE NoSoumission = ?";
		AppSoumission soum;
		
		try{
			soum = jdbcTemplateObject.queryForObject(SQL, 
					new Object[]{noSoum}, 
					new AppSoumissionMapper(AppSoumissionMapper.FieldScopeEnum.ALL));
		}
		catch(EmptyResultDataAccessException e){
			// if not found, return null
			soum = null;
		}
		
		return Optional.ofNullable(soum);
	}
	
	
	@Override
	public Optional<AppSoumission> findBySuiteVersion(int suite, String version) {
		if(version.trim().length() > 1){
			return Optional.ofNullable(null);
		}
		
		String SQL = "SELECT * FROM AppSoumission WHERE Suite = ? AND Version = ?";
		AppSoumission soum;
		
		try{
			soum = jdbcTemplateObject.queryForObject(SQL, 
					new Object[]{suite, version}, 
					new AppSoumissionMapper(AppSoumissionMapper.FieldScopeEnum.ALL));
		}
		catch(EmptyResultDataAccessException e){
			// if not found, return null
			soum = null;
		}
		
		return Optional.ofNullable(soum);
	}		

	@Override
	public List<AppSoumission> findBySuite(int suite) {
	    String SQL = "SELECT * FROM AppSoumission WHERE Suite = ? ORDER BY Version";
	    List<AppSoumission> soums = jdbcTemplateObject.query(SQL, new AppSoumissionMapper(AppSoumissionMapper.FieldScopeEnum.ALL), suite);
	    return soums;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, isolation=Isolation.DEFAULT)
	public int create(AppSoumission soum) {
		// First we do an insert with id return
		Map<String, Object> parameters = new HashMap<String, Object>(6);
                parameters.put("Projet", soum.entete.getProjet());
                parameters.put("NoVendeur", soum.entete.getNoVendeur());
                parameters.put("NomVendeur", soum.entete.getNoVendeur());
                parameters.put("GeniusNoClient", soum.entete.getGeniusNoClient());
                parameters.put("DateEntre", new Date());        
                parameters.put("Commission", soum.getCommission());       
                
                //****************************************************************
                // NB: if I don't set this to false, call to
                // executeAndReturnKey() throws a DataIntegrityViolationException
                // (whereas the insert with JdbcTemplate.update() works fine ! but we don't get the
                // generated NoSoumission... maybe I should use the OUTPUT CLAUSE
                insertSoumission.setAccessTableColumnMetaData(false);
              //****************************************************************
                
            	Number newNoSoum = insertSoumission.executeAndReturnKey(parameters);
    	
            	// Then once the Soumission is created we update some fields
		String SQL = "UPDATE AppSoumission " +
			"SET Suite = (SELECT MAX(Suite)+1 FROM AppSoumission), " +
			"Version = 'A', " +
			"ClientSoum = (SELECT Name FROM BODB.dbo.vgMfiCustomers WHERE Customer = ?) " + 					 
			"WHERE NoSoumission = ?";					  
		
		jdbcTemplateObject.update( SQL, soum.entete.getGeniusNoClient(), newNoSoum.intValue());
    			
		return newNoSoum.intValue();
	}

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, isolation=Isolation.DEFAULT)
	public int createNewVersion(AppSoumission soum) {
	    // First we do an insert with id return
	    Map<String, Object> parameters = new HashMap<String, Object>(6);
	    parameters.put("Projet", soum.entete.getProjet());
	    parameters.put("NoVendeur", soum.entete.getNoVendeur());
	    parameters.put("NomVendeur", soum.entete.getNoVendeur());
	    parameters.put("GeniusNoClient", soum.entete.getGeniusNoClient());
	    parameters.put("DateEntre", new Date());        
	    parameters.put("Commission", soum.getCommission());       

	    //****************************************************************
	    // NB: if I don't set this to false, call to
	    // executeAndReturnKey() throws a DataIntegrityViolationException
	    // (whereas the insert with JdbcTemplate.update() works fine ! but we don't get the
	    // generated NoSoumission... maybe I should use the OUTPUT CLAUSE
	    insertSoumission.setAccessTableColumnMetaData(false);
	    //****************************************************************

	    Number newNoSoum = insertSoumission.executeAndReturnKey(parameters);

	    // Then once the Soumission is created we update some fields
	    String SQL = "UPDATE AppSoumission " +
		    "SET Suite = ?, " +
		    // next version
		    "Version = CHAR(ASCII(SUBSTRING((SELECT MAX(Version) FROM AppSoumission WHERE Suite = ?), 1, 1))+1), " +
		    "ClientSoum = (SELECT Name FROM BODB.dbo.vgMfiCustomers WHERE Customer = ?) " + 					 
		    "WHERE NoSoumission = ?";					  
	    
	    jdbcTemplateObject.update(SQL, soum.getSuite(), soum.getSuite(), soum.entete.getGeniusNoClient(), newNoSoum.intValue());

	    return newNoSoum.intValue();	   
	}

	@Override
	/**
	 * NOTE: does not update NoSoumission (id) or Suite as they must be set by create
	 * Updates strictly the members of AppSoumission; to update AppSoumEntete, use AppSoumEnteteDAO 
	 */
	public boolean update(AppSoumission soum) {
		String SQL = "UPDATE AppSoumission " +
					 "SET Version = ?, Commission = ?, Statut = ?, " +
					 "NoDossier = ?, GeniusNoCom = ?, DateProductionDebut = ?, DateProductionNecessaire = ?, " +
					 "Flex = ?, NoteInterne = ?, FlagHeader = ? " + 
				     "WHERE NoSoumission = ?";
		int numrows = jdbcTemplateObject.update(SQL, soum.getVersion(), soum.getCommission(), soum.getStatut(), 
					soum.getNoDossier(),soum.getGeniusNoCom(), soum.getDateProductionDebut(), soum.getDateProductionNecessaire(),
					soum.getFlex(), soum.getNoteInterne(), soum.getFlagHeader(), soum.getNoSoumission());
		
		return (numrows == 1);
	}
		
	
	@Override
	public boolean delete(int noSoum) {
		String SQL = "DELETE FROM AppSoumission WHERE NoSoumission = ?";
		int numrows = jdbcTemplateObject.update(SQL,noSoum);
		return (numrows == 1);
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
				+ "FROM vboAppSoumOverview ";
			
			
			break;
		case COUNT:
			// Begin the count query
			SQL = "SELECT COUNT(NoSoumission) FROM vboAppSoumOverview ";
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
	 * Return a subset of AppSoumission given several search criteria
	 * We use the view vboAppSoumOverview in order to gain performance in search
	 * Also non-clustered indexes should exist on the following columns of AppSoumission : 
	 * NoVendeur, Statut, DateEntre, GeniusNoClient, Suite, NoDossier
	 * getPage()
	 */
	@Override
	public List<AppSoumission> getPage(String search, String sort, String order, int pageSize, int pageNumber,
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
				
		List<AppSoumission> soums = this.jdbcTemplateObject.query(SQL, 
									  objList.toArray(), 
									  new AppSoumissionMapper(AppSoumissionMapper.FieldScopeEnum.OVERVIEW));		
		
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

}
