package org.tppe;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.tppe.entities.Product;
import org.tppe.entities.Stock;
import org.tppe.exceptions.BlankDescriptionException;
import org.tppe.exceptions.InvalidValueException;
import org.tppe.services.StockServices;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestStock {

    private static Stock stock;

    @BeforeAll
    public static void init(){
        stock = new Stock();
    }

    @ParameterizedTest
    @CsvSource({
            "ProductA, 123, 10.0, 20.0, 100",
    })
    public void testFindProductByName(String name, String barcode, double buyPrice, double sellPrice, int quantity) {
        assertDoesNotThrow(() -> {
            stock.addProduct(name, barcode, buyPrice, sellPrice, quantity);
        });

        Product foundProduct = findProduct(barcode);
        assertEquals(name, foundProduct.getName());

        assertDoesNotThrow(() -> {
            stock.addProduct(name, barcode, buyPrice, sellPrice, quantity);
        });
        foundProduct = findProduct(barcode);
        assertEquals(quantity + 100, foundProduct.getQuantity());
    }

    @ParameterizedTest
    @CsvSource({
            "ProductA, 123, 10.0, 20.0, 100",
    })
    public void testRemoveProduct(String name, String barcode, double buyPrice, double sellPrice, int quantity){
        assertDoesNotThrow(() -> {
            stock.addProduct(name, barcode, buyPrice, sellPrice, quantity);
        });
        Product foundProduct = findProduct(barcode);
        stock.removeProduct(foundProduct, 10);

        assertEquals(quantity - 10, foundProduct.getQuantity());
    }

    @ParameterizedTest
    @CsvSource({
            "ProductA, 456, 0, 20.0, 100",
            "ProductB, 123, 15.0, -1, 150",
            "ProductC, 123, 15.0, 20.0, -10"
    })
    public void testInvalidInputValueReceiveProduct(String name, String barcode, double buyPrice, double sellPrice, int quantity) {
        assertThrows(InvalidValueException.class, () -> {
            stock.addProduct(name, barcode, buyPrice, sellPrice, quantity);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "    , 456, 20.0, 20.0, 100",
            "ProductB,  , 15.0, 10.0, 150",
    })
    public void testBlankDescriptionReceiveProduct(String name, String barcode, double buyPrice, double sellPrice, int quantity) {
        assertThrows(BlankDescriptionException.class, () -> {
            stock.addProduct(name, barcode, buyPrice, sellPrice, quantity);
        });
    }

    private Product findProduct(String barcode){
        for (Product product : this.stock.getProducts()){
            if(product.getBarcode() == barcode){
                return product;
            }
        }
        return null;
    }
}
