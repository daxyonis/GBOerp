package models;

import java.util.List;

/**
 * Class for data transfer to Bootstrap Table for server-side pagination
 * see https://github.com/wenzhixin/bootstrap-table-examples/blob/master/json/data2.json
 * @author Eva
 *
 */
public class SoumissionDTO {
	
	private int total;
	private List<Soumission> rows;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<Soumission> getRows() {
		return rows;
	}
	public void setRows(List<Soumission> rows) {
		this.rows = rows;
	}
	
		

}
