package edu.hit.mrp.bean;

public class ProductsComponent {

	private int rID;
	private int ID;
	private int parentID;
	private int count;
	private int projectTimeLimit;
	private String displayText;
	private String componentName;
	private String componentCode;
	public int getrID() {
		return rID;
	}
	public void setrID(int rID) {
		this.rID = rID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getParentID() {
		return parentID;
	}
	public void setParentID(int parentID) {
		this.parentID = parentID;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getProjectTimeLimit() {
		return projectTimeLimit;
	}
	public void setProjectTimeLimit(int projectTimeLimit) {
		this.projectTimeLimit = projectTimeLimit;
	}
	public String getDisplayText() {
		return displayText;
	}
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public String getComponentCode() {
		return componentCode;
	}
	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
	}
}
