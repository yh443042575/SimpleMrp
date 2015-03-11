package edu.hit.mrp.bean;

public class StockTransitBean {

	String date;
	String material;
	int inStock;
	int inTransit;
	public StockTransitBean(String date, String material, int inStock,
			int inTransit) {
		super();
		this.date = date;
		this.material = material;
		this.inStock = inStock;
		this.inTransit = inTransit;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public int getInStock() {
		return inStock;
	}
	public void setInStock(int inStock) {
		this.inStock = inStock;
	}
	public int getInTransit() {
		return inTransit;
	}
	public void setInTransit(int inTransit) {
		this.inTransit = inTransit;
	}
	
	
}
