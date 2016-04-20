package models.app;

import java.util.List;

import models.app.AppSoumission;

public class AppSoumissionDTO {

	private int total;
	private List<AppSoumission> rows;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<AppSoumission> getRows() {
		return rows;
	}
	public void setRows(List<AppSoumission> rows) {
		this.rows = rows;
	}
}
