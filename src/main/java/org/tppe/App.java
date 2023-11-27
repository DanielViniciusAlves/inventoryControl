package org.tppe;

import org.tppe.entities.Product;
import org.tppe.exceptions.BlankDescriptionException;
import org.tppe.exceptions.InvalidValueException;
import org.tppe.services.StockServices;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        StockServices stockServices = new StockServices();
        LocalDate date = LocalDate.parse("2023-11-27");
        stockServices.receiveProduct("Test", "12345", 12.3, 15.3, 1, date);
        System.out.println(stockServices.getBatchTotal(0));
        System.out.println(stockServices.getTotalProductsByName("Test"));

        stockServices.receiveProduct("Test", "12345", 12.3, 15.3, 10, date);
        System.out.println(stockServices.getBatchTotal(0));
        System.out.println(stockServices.getBatchTotal(1));
        System.out.println(stockServices.getTotalProductsByName("Test"));

        Product product = stockServices.findProductByName("Test");
        System.out.println(product.getName());
        System.out.println(product.getBarcode());

        product = stockServices.findProductByBarcode("12345");
        System.out.println(product.getName());
        System.out.println(product.getBarcode());
    }
}
