package org.tppe;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.tppe.entities.Product;
import org.tppe.exceptions.BlankDescriptionException;
import org.tppe.exceptions.InvalidValueException;
import org.tppe.services.StockServices;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class TestStockServices {

    private static StockServices stockServices;

    @BeforeAll
    public static void init(){
        stockServices = new StockServices();
    }

    @ParameterizedTest
    @CsvSource({
            "ProductA, 123, 10.0, 20.0, 100, 2023-12-31",
            "ProductB, 456, 15.0, 25.0, 150, 2023-12-31"
    })
    public void testFindProductByName(String name, String barcode, double buyPrice, double sellPrice, int quantity, LocalDate expirationDate) {
        stockServices.receiveProduct(name, barcode, buyPrice, sellPrice, quantity, expirationDate);

        Product foundProduct = stockServices.findProductByName(name);

        assertEquals(name, foundProduct.getName());
    }

    @ParameterizedTest
    @CsvSource({
            "ProductC, 456, 10.0, 20.0, 100, 2023-12-31",
            "ProductD, 123, 15.0, 25.0, 150, 2023-12-31"
    })
    public void testFindProductByBarcode(String name, String barcode, double buyPrice, double sellPrice, int quantity, LocalDate expirationDate) {
        stockServices.receiveProduct(name, barcode, buyPrice, sellPrice, quantity, expirationDate);

        Product foundProduct = stockServices.findProductByBarcode(barcode);

        assertEquals(barcode, foundProduct.getBarcode());
    }

    @ParameterizedTest
    @CsvSource({
            "ProductE, 456, 10.0, 20.0, 100, 2023-12-31",
            "ProductF, 123, 15.0, 25.0, 150, 2023-12-31"
    })
    public void testReceiveProduct(String name, String barcode, double buyPrice, double sellPrice, int quantity, LocalDate expirationDate) {
        stockServices.receiveProduct(name, barcode, buyPrice, sellPrice, quantity, expirationDate);

        Product foundProduct = stockServices.findProductByBarcode(barcode);

        assertEquals(barcode, foundProduct.getBarcode());
    }
}
