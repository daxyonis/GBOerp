/**
 * 
 */
package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author Eva
 * Maps a JDBC result set to a Flexible object
 */
public class FlexibleMapper implements RowMapper<Flexible>{

	public Flexible mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Flexible flex = new Flexible();
		
		// Set Flexible properties
		flex.setId(rs.getShort("Id"));
		flex.setCaracteristiques(rs.getNString("Caracteristiques"));
		flex.setCategorie(rs.getNString("Categorie"));
		flex.setCommentaireUtile(rs.getNString("CommentaireUtile"));
		flex.setDescription(rs.getNString("Description"));
		flex.setDistributeur(rs.getNString("Distributeur"));
		flex.setDureeSupport(rs.getNString("DureeSupport"));
		flex.setEnStock(rs.getBoolean("EnStock"));
		flex.setEpaisseur(rs.getDouble("Epaisseur"));
		flex.setEpaisseurUnites(rs.getNString("EpaisseurUnites"));
		flex.setFicheTechniqueLien(rs.getNString("FicheTechniqueLien"));
		flex.setFicheTechniqueNom(rs.getNString("FicheTechniqueNom"));
		flex.setFormat(rs.getNString("Format"));
		flex.setFormatMax(rs.getInt("FormatMax"));
		flex.setImpression(rs.getNString("Impression"));
		flex.setInterieurExterieur(rs.getNString("InterieurExterieur"));
		flex.setNoItemGenius(rs.getString("NoItemGenius"));
		flex.setPrixCoutant1(rs.getDouble("PrixCoutant1"));
		flex.setPrixCoutant2(rs.getDouble("PrixCoutant2"));
		flex.setProduit(rs.getNString("Produit"));
		flex.setUtilite(rs.getNString("Utilite"));
		
		return flex;
	}
}
