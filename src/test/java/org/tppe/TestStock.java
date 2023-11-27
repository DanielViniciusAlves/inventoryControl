package org.tppe;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.tppe.entities.Product;
import org.tppe.entities.Stock;
import org.tppe.services.StockServices;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        stock.addProduct(name, barcode, buyPrice, sellPrice, quantity);
        Product foundProduct = findProduct(barcode);
        assertEquals(name, foundProduct.getName());

        stock.addProduct(name, barcode, buyPrice, sellPrice, 100);
        foundProduct = findProduct(barcode);
        assertEquals(quantity + 100, foundProduct.getQuantity());
    }

    @ParameterizedTest
    @CsvSource({
            "ProductA, 123, 10.0, 20.0, 100",
    })
    public void testRemoveProduct(String name, String barcode, double buyPrice, double sellPrice, int quantity){
        stock.addProduct(name, barcode, buyPrice, sellPrice, quantity);
        Product foundProduct = findProduct(barcode);
        stock.removeProduct(foundProduct, 10);

        assertEquals(quantity - 10, foundProduct.getQuantity());
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
