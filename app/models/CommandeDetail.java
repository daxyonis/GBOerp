package models;

import java.util.Date;

public class CommandeDetail {
	
	private String customerOrder;
	private int isCompleted;
	
	private String itemId;				// item id
	private String itemName;			// item name
	private String itemDescription;		// item description
	private String job;
	
	private double qtyOrdered;
	private double qtyShipped;
	private double qtyInvoiced;	
	private double priceNet;
	private double amountTotal;
	
	private Date jobDateProductionEnd;	

	public String getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(String customerOrder) {
		this.customerOrder = customerOrder;
	}

	public int getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(int isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getQtyOrdered() {
		return qtyOrdered;
	}

	public void setQtyOrdered(double qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	public double getQtyShipped() {
		return qtyShipped;
	}

	public void setQtyShipped(double qtyShipped) {
		this.qtyShipped = qtyShipped;
	}

	public double getQtyInvoiced() {
		return qtyInvoiced;
	}

	public void setQtyInvoiced(double qtyInvoiced) {
		this.qtyInvoiced = qtyInvoiced;
	}

	public double getPriceNet() {
		return priceNet;
	}

	public void setPriceNet(double priceNet) {
		this.priceNet = priceNet;
	}

	public double getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(double amountTotal) {
		this.amountTotal = amountTotal;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Date getJobDateProductionEnd() {
		return jobDateProductionEnd;
	}

	public void setJobDateProductionEnd(Date jobDateProductionEnd) {
		this.jobDateProductionEnd = jobDateProductionEnd;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	

	
}
