package models;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

public class Commande {
	
	private String customerOrder;
	private String quote;
	private Date dateDelivery;
	private Date dateCustomer;
	private String revisionNumber;
	private String status;			// status value as a single character
	private String statusText;		// ADDED : get the status as text instead of character
	
	private String shipToCustomer;
	private String shipToCustomerName;
	private String shipToCustomerAddress1;
	private String shipToCustomerAddress2;
	private String shipToCustomerCity;	
	private String shipToCustomerProvinceName;
	private String shipToCustomerZipCode;
	private String shipToCustomerCountry;
	
	
	private String billToCustomer;
	private String billToCustomerName;
	private String billToCustomerAddress1;
	private String billToCustomerAddress2;
	private String billToCustomerCity;	
	private String billToCustomerProvinceName;
	private String billToCustomerZipCode;
	private String billToCustomerCountry;
	
	private String quickNote;	// this is in fact the project name !
	
	private String salesman;		// salesman id
	private String salesmanFullName;	// ADDED : get the salesman full name (prenom + nom)
	private double amountWithoutTaxes;
	
	public List<CommandeDetail> details;
	
	// Getters and Setters
	public String getCustomerOrder() {
		return customerOrder;
	}
	public void setCustomerOrder(String customerOrder) {
		this.customerOrder = customerOrder;
	}
	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
		this.quote = quote;
	}
	public Date getDateDelivery() {
		return dateDelivery;
	}
	public void setDateDelivery(Date dateDelivery) {
		this.dateDelivery = dateDelivery;
	}
	public Date getDateCustomer() {
		return dateCustomer;
	}
	public void setDateCustomer(Date dateCustomer) {
		this.dateCustomer = dateCustomer;
	}
	public String getRevisionNumber() {
		return revisionNumber;
	}
	public void setRevisionNumber(String revisionNumber) {
		this.revisionNumber = revisionNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public String getShipToCustomer() {
		return shipToCustomer;
	}
	public void setShipToCustomer(String shipToCustomer) {
		this.shipToCustomer = shipToCustomer;
	}
	public String getShipToCustomerName() {
		return shipToCustomerName;
	}
	public void setShipToCustomerName(String shipToCustomerName) {
		this.shipToCustomerName = shipToCustomerName;
	}
	public String getShipToCustomerAddress1() {
		return shipToCustomerAddress1;
	}
	public void setShipToCustomerAddress1(String shipToCustomerAddress1) {
		this.shipToCustomerAddress1 = shipToCustomerAddress1;
	}
	public String getShipToCustomerAddress2() {
		return shipToCustomerAddress2;
	}
	public void setShipToCustomerAddress2(String shipToCustomerAddress2) {
		this.shipToCustomerAddress2 = shipToCustomerAddress2;
	}
	public String getShipToCustomerCity() {
		return shipToCustomerCity;
	}
	public void setShipToCustomerCity(String shipToCustomerCity) {
		this.shipToCustomerCity = shipToCustomerCity;
	}
	public String getShipToCustomerProvinceName() {
		return shipToCustomerProvinceName;
	}
	public void setShipToCustomerProvinceName(String shipToCustomerProvinceName) {
		this.shipToCustomerProvinceName = shipToCustomerProvinceName;
	}
	public String getShipToCustomerZipCode() {
		return shipToCustomerZipCode;
	}
	public void setShipToCustomerZipCode(String shipToCustomerZipCode) {
		this.shipToCustomerZipCode = shipToCustomerZipCode;
	}
	public String getShipToCustomerCountry() {
		return shipToCustomerCountry;
	}
	public void setShipToCustomerCountry(String shipToCustomerCountry) {
		this.shipToCustomerCountry = shipToCustomerCountry;
	}
	public String getBillToCustomer() {
		return billToCustomer;
	}
	public void setBillToCustomer(String billToCustomer) {
		this.billToCustomer = billToCustomer;
	}
	public String getBillToCustomerName() {
		return billToCustomerName;
	}
	public void setBillToCustomerName(String billToCustomerName) {
		this.billToCustomerName = billToCustomerName;
	}
	public String getBillToCustomerAddress1() {
		return billToCustomerAddress1;
	}
	public void setBillToCustomerAddress1(String billToCustomerAddress1) {
		this.billToCustomerAddress1 = billToCustomerAddress1;
	}
	public String getBillToCustomerAddress2() {
		return billToCustomerAddress2;
	}
	public void setBillToCustomerAddress2(String billToCustomerAddress2) {
		this.billToCustomerAddress2 = billToCustomerAddress2;
	}
	public String getBillToCustomerCity() {
		return billToCustomerCity;
	}
	public void setBillToCustomerCity(String billToCustomerCity) {
		this.billToCustomerCity = billToCustomerCity;
	}
	public String getBillToCustomerProvinceName() {
		return billToCustomerProvinceName;
	}
	public void setBillToCustomerProvinceName(String billToCustomerProvinceName) {
		this.billToCustomerProvinceName = billToCustomerProvinceName;
	}
	public String getBillToCustomerZipCode() {
		return billToCustomerZipCode;
	}
	public void setBillToCustomerZipCode(String billToCustomerZipCode) {
		this.billToCustomerZipCode = billToCustomerZipCode;
	}
	public String getBillToCustomerCountry() {
		return billToCustomerCountry;
	}
	public void setBillToCustomerCountry(String billToCustomerCountry) {
		this.billToCustomerCountry = billToCustomerCountry;
	}
	public String getQuickNote() {
		return quickNote;
	}
	public void setQuickNote(String quickNote) {
		this.quickNote = quickNote;
	}
	public String getSalesman() {
		return salesman;
	}
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}	
	public String getSalesmanFullName() {
		return salesmanFullName;
	}
	public void setSalesmanFullName(String salesmanFullName) {
		this.salesmanFullName = salesmanFullName;
	}
	public double getAmountWithoutTaxes() {
		return amountWithoutTaxes;
	}
	public void setAmountWithoutTaxes(double amountWithoutTaxes) {
		this.amountWithoutTaxes = amountWithoutTaxes;
	}
	
	public String getFormattedAmountNoTax(){
		DecimalFormat df = new DecimalFormat("### ###.00");	    
	    return df.format(amountWithoutTaxes);
	}
	

}
