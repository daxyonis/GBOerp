/**
 * 
 */
package services.dao;

import java.util.List;

import models.Imprimante;
import models.ImprimanteMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author Eva
 * DAO implementation for accessing table GroupeBO.dbo.Imprimante
 */
@Repository
public class ImprimanteDAOImpl implements ImprimanteDAO {		

		private JdbcTemplate jdbcTemplateObject;
		
		@Autowired
		public ImprimanteDAOImpl(JdbcTemplate jdbcTempl){
			this.jdbcTemplateObject = jdbcTempl;
		}

	/* (non-Javadoc)
	 * @see services.simple.ImprimanteDAO#findForFlexible(int)
	 */
	public List<Imprimante> findForFlexible(int productId) {
		String SQL = "SELECT I.* , MR.MachineRateCost + MR.AverageWorkerRate As TauxImpr, MR.AverageWorkerRate As TauxManip " +
					 "FROM (Imprimante I JOIN LienProduitImprimante LPI ON I.CodeSelection = LPI.CodeSelection) " +
					 "LEFT JOIN BODB.dbo.vgMfiMachineAndResource MR ON I.CodeMachine = MR.Machine " +
					 "WHERE LPI.ProdId = ?";
	    List <Imprimante> imps = jdbcTemplateObject.query(SQL, new Object[]{productId}, new ImprimanteMapper());
	    return imps;
	}

	/* (non-Javadoc)
	 * @see services.simple.ImprimanteDAO#findByCode(int)
	 */
	public Imprimante findByCode(String codeSelection) {
		String SQL = "SELECT I.* , MR.MachineRateCost + MR.AverageWorkerRate As TauxImpr, MR.AverageWorkerRate As TauxManip " +
					 "FROM Imprimante I  LEFT JOIN BODB.dbo.vgMfiMachineAndResource MR ON I.CodeMachine = MR.Machine " +
					 "WHERE I.CodeSelection = ?";
		
		Imprimante impr;
		try{
			impr = jdbcTemplateObject.queryForObject(SQL, new Object[]{codeSelection}, new ImprimanteMapper());		
		}
		catch(EmptyResultDataAccessException e){
			impr = null;
		}
		
		return impr;
	}

	/* (non-Javadoc)
	 * @see services.simple.ImprimanteDAO#findAll()
	 */
	public List<Imprimante> findAll() {
		String SQL = "SELECT I.* , MR.MachineRateCost + MR.AverageWorkerRate As TauxImpr, MR.AverageWorkerRate As TauxManip " +
					 "FROM Imprimante I  LEFT JOIN BODB.dbo.vgMfiMachineAndResource MR ON I.CodeMachine = MR.Machine ";
		
		List<Imprimante> imps = jdbcTemplateObject.query(SQL, new ImprimanteMapper());
		
		return imps;
	}

	/* (non-Javadoc)
	 * @see services.simple.ImprimanteDAO#save(models.Imprimante)
	 */
	public boolean save(Imprimante imp) {
		String SQL = "UPDATE Imprimante SET " +
					 "Machine = ? ,Mode = ?  ,VitesseImpression = ?, " +
					 "PourcentageEncre = ?, PrixEncre = ?, PerteEncre = ? , MargeEncre = ? , " +
					 "CodeMachine = ? , HauteurMax_po = ? ,LargeurMax_po = ?, Active = ? " +
					 "WHERE CodeSelection = ?";
		
		int numrows = jdbcTemplateObject.update(SQL, imp.getMachine(), imp.getMode(), imp.getVitesse(),
												imp.getPourcentEncre(), imp.getPrixEncre(), imp.getPerteEncre(), imp.getMargeEncre(),
												imp.getCodeMachine(), imp.getHauteurMax_po(), imp.getLargeurMax_po(),imp.isActive(),
												imp.getCodeSelection());
		
		return(numrows == 1);
	}

	@Override
	public boolean add(Imprimante imp) {
		String SQL = "INSERT INTO Imprimante "
				+ "(CodeSelection, Machine, Mode, VitesseImpression, "
				+ "PourcentageEncre, PrixEncre, PerteEncre, MargeEncre, "
				+ "CodeMachine, HauteurMax_po, LargeurMax_po, Active) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		
		int numrows = jdbcTemplateObject.update(SQL, imp.getCodeSelection(), imp.getMachine(), imp.getMode(), imp.getVitesse(),
				imp.getPourcentEncre(), imp.getPrixEncre(), imp.getPerteEncre(), imp.getMargeEncre(),
				imp.getCodeMachine(), imp.getHauteurMax_po(), imp.getLargeurMax_po(), imp.isActive());
		
		return(numrows == 1);
	}

	@Override
	public boolean remove(Imprimante imp) {
		String SQL = "DELETE FROM Imprimante WHERE CodeSelection = ?";
		int numrows = jdbcTemplateObject.update(SQL, imp.getCodeSelection());
		return(numrows == 1);
	}

	
}
