package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CommandeMapper implements RowMapper<Commande>{

	@Override
	public Commande mapRow(ResultSet rs, int rowNum) throws SQLException {
		Commande comm = new Commande();
		
		comm.setCustomerOrder(rs.getString("CustomerOrder"));
		comm.setStatus(rs.getString("Status"));
		comm.setDateCustomer(rs.getDate("DateCustomer"));
		comm.setDateDelivery(rs.getDate("DateDelivery"));
		comm.setQuickNote(rs.getString("QuickNote"));
		comm.setQuote(rs.getString("Quote"));
		comm.setRevisionNumber(rs.getString("RevisionNumber"));
		comm.setSalesman(rs.getString("Salesman"));
		comm.setShipToCustomer(rs.getString("ShipToCustomer"));
		comm.setShipToCustomerAddress1(rs.getString("ShipToCustomerAddress1"));
		comm.setShipToCustomerAddress2(rs.getString("ShipToCustomerAddress2"));
		comm.setShipToCustomerCity(rs.getString("ShipToCustomerCity"));
		comm.setShipToCustomerCountry(rs.getString("ShipToCustomerCountry"));
		comm.setShipToCustomerName(rs.getString("ShipToCustomerName"));
		comm.setShipToCustomerProvinceName(rs.getString("ShipToCustomerProvinceName"));
		comm.setShipToCustomerZipCode(rs.getString("ShipToCustomerZipCode"));	
		comm.setBillToCustomer(rs.getString("BillToCustomer"));
		comm.setBillToCustomerAddress1(rs.getString("BillToCustomerAddress1"));
		comm.setBillToCustomerAddress2(rs.getString("BillToCustomerAddress2"));
		comm.setBillToCustomerName(rs.getString("BillToCustomerName"));
		comm.setBillToCustomerCity(rs.getString("BillToCustomerCity"));
		comm.setBillToCustomerCountry(rs.getString("BillToCustomerCountry"));
		comm.setBillToCustomerProvinceName(rs.getString("BillToCustomerProvinceName"));
		comm.setBillToCustomerZipCode(rs.getString("BillToCustomerZipCode"));			
		comm.setAmountWithoutTaxes(rs.getDouble("AmountWithoutTaxes"));
		
		return comm;
		
	}
	
	

}
