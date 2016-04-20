package models;

public class Statut {
	
	public final static int MIN_CODE = 0;
	public final static int MAX_CODE = 9;

	private int code;
	private String statut;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	
	
}
