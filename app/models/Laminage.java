package models;

public class Laminage {
	
	public final static double LAMINAGE_MIN_TARIF = 20.00;
	
	private int id;
	private String nom;
	private String caracteristique;
	private double coutPiCar;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getCaracteristique() {
		return caracteristique;
	}
	public void setCaracteristique(String caracteristique) {
		this.caracteristique = caracteristique;
	}
	public double getCoutPiCar() {
		return coutPiCar;
	}
	public void setCoutPiCar(double coutPiCar) {
		this.coutPiCar = coutPiCar;
	}
	

}
