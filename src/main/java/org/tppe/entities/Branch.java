package org.tppe.entities;

public class Branch {
	private String name;
	private Stock stock;
	
	public Branch(String name) {
		super();
		this.name = name;
		this.stock = new Stock();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}
	
	

}
