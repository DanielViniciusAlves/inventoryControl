package org.tppe;

import org.tppe.entities.Product;
import org.tppe.exceptions.BlankDescriptionException;
import org.tppe.exceptions.InvalidValueException;
import org.tppe.services.StockServices;

public class App {
    public static void main(String[] args) {
        StockServices stockServices = new StockServices();
        stockServices.receiveProduct("Te43st", "12345-bh1", 12.3, 15.3, 10);

        Product product = stockServices.findProductByName("Test");
        System.out.println(product.getName());
        System.out.println(product.getBarcode());

        product = stockServices.findProductByBarcode("12345-bh1");
        System.out.println(product.getName());
        System.out.println(product.getBarcode());
    }
}
