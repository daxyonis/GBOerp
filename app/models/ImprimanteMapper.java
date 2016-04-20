/**
 * 
 */
package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
/**
 * @author Eva
 *
 */
public class ImprimanteMapper implements RowMapper<Imprimante>{
	
	public Imprimante mapRow(ResultSet rs, int rowNum) throws SQLException{
		
		Imprimante imp = new Imprimante();
		
		imp.setCodeSelection(rs.getNString("CodeSelection"));
		imp.setMachine(rs.getNString("Machine"));
		imp.setMode(rs.getNString("Mode"));
		imp.setVitesse(rs.getInt("VitesseImpression"));
		imp.setPourcentEncre(rs.getInt("PourcentageEncre"));
		imp.setPrixEncre(rs.getBigDecimal("PrixEncre"));
		imp.setPerteEncre(rs.getInt("PerteEncre"));
		imp.setMargeEncre(rs.getInt("MargeEncre"));
		imp.setCodeMachine(rs.getNString("CodeMachine"));
		imp.setHauteurMax_po(rs.getInt("HauteurMax_po"));
		imp.setLargeurMax_po(rs.getInt("LargeurMax_po"));
		imp.setActive(rs.getBoolean("Active"));
		imp.setTauxImpr(rs.getFloat("TauxImpr"));
		imp.setTauxManip(rs.getFloat("TauxManip"));
		
		return imp;
	}

}
