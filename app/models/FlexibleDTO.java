/**
 * 
 */
package models;
import java.math.MathContext;
import java.math.BigDecimal;

/**
 * @author Eva
 * Data-Transfer Object (DTO) used to 
 * get both the data from the 
 * Flexible object and the cost from Genius 
 */
public class FlexibleDTO {
	
	private Flexible flex;
	private double maxCost;
	private String unit;
	
	/**
	 * 
	 * @return the unit for cost
	 */
	public String getUnit(){
		return unit;
	}
	
	public void setUnit(String unit){
		this.unit = unit;
	}
	
	/**
	 * @return the flex
	 */
	public Flexible getFlex() {
		return flex;
	}
	/**
	 * @param flex the flex to set
	 */
	public void setFlex(Flexible flex) {
		this.flex = flex;
	}
	/**
	 * @return the maxCost
	 */
	public double getMaxCost() {
		// round up to 2 decimals
		BigDecimal dec = new BigDecimal(maxCost, new MathContext(2)); 
		return dec.doubleValue();
	}
	/**
	 * @param maxCost the maxCost to set
	 */
	public void setMaxCost(double maxCost) {
		this.maxCost = maxCost;
	}
	
}
