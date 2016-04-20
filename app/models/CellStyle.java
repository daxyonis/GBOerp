/**
 * 
 */
package models;

/**
 * @author Eva
 *
 */
public class CellStyle {	 
	
	private short RowId;	// 16-bit signed (-32K-32K)
	private byte ColIndex;	// 8-bit signed  (-128-127)
	private String CssClass;
	private String CssColor;
	private String CssBgColor;
	
	public boolean hasCssClass(){
		return !((CssClass == null) || (CssClass.isEmpty()));
	}
	public boolean hasCssColor(){
		return !((CssColor == null) || (CssColor.isEmpty()));
	}
	public boolean hasCssBgColor(){
		return !((CssBgColor == null) || (CssBgColor.isEmpty()));
	}
	
	public boolean hasStyles(){
		return ( hasCssClass() || hasCssColor() || hasCssBgColor() );
	}
		
	
	/**
	 * @return the rowId
	 */
	public short getRowId() {
		return RowId;
	}
	/**
	 * @param rowId the rowId to set
	 */
	public void setRowId(short rowId) {
		RowId = rowId;
	}
	/**
	 * @return the colIndex
	 */
	public byte getColIndex() {
		return ColIndex;
	}
	/**
	 * @param colIndex the colIndex to set
	 */
	public void setColIndex(byte colIndex) {
		ColIndex = colIndex;
	}
	/**
	 * @return the cssClass
	 */
	public String getCssClass() {
		return CssClass;
	}
	/**
	 * @param cssClass the cssClass to set
	 */
	public void setCssClass(String cssClass) {
		CssClass = cssClass;
	}
	/**
	 * @return the cssColor
	 */
	public String getCssColor() {
		return CssColor;
	}
	/**
	 * @param cssColor the cssColor to set
	 */
	public void setCssColor(String cssColor) {
		CssColor = cssColor;
	}
	/**
	 * @return the cssBgColor
	 */
	public String getCssBgColor() {
		return CssBgColor;
	}
	/**
	 * @param cssBgColor the cssBgColor to set
	 */
	public void setCssBgColor(String cssBgColor) {
		CssBgColor = cssBgColor;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "CellStyle [RowId=" + RowId + ", ColIndex=" + ColIndex
				+ ", CssClass=" + CssClass + ", CssColor=" + CssColor
				+ ", CssBgColor=" + CssBgColor + "]";
	}
	
	

}
