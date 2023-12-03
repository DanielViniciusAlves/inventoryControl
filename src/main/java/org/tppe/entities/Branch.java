package org.tppe.entities;

public class Branch {
	private String name;
	private Stock stock;
	
	public Branch(String name,Stock stock) {
		super();
		this.name = name;
		this.stock = stock;
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
