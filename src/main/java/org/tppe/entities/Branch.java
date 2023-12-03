package org.tppe.entities;

import org.tppe.services.StockServices;

public class Branch {
	private String name;
	private StockServices stockServices;

	private Stock stock;

	public Branch(String name, StockServices stockServices, Stock stock) {
		super();
		this.name = name;
		this.stockServices = stockServices;
		this.stock = stock;
	}

	public String getName() {
		return name;
	}


	public StockServices getStockServices() {
		return stockServices;
	}

	public Stock getStock() {
		return stock;
	}

}
