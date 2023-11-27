package org.tppe;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.tppe.entities.Batch;
import org.tppe.entities.Product;
import org.tppe.entities.Stock;
import org.tppe.exceptions.BlankDescriptionException;
import org.tppe.exceptions.InvalidValueException;
import org.tppe.services.StockServices;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestStock {

    private static Stock stock;

    @BeforeEach
    public void init(){
        stock = new Stock();
    }

    @ParameterizedTest
    @CsvSource({
            "ProductA, 123, 10.0, 20.0, 100, 2023-12-31",
    })
    public void testAddProduct(String name, String barcode, double buyPrice, double sellPrice, int quantity, LocalDate expirationDate) {
        assertDoesNotThrow(() -> {
            stock.addProduct(name, barcode, buyPrice, sellPrice, quantity, expirationDate);
        });

        Product addedProduct = stock.getProducts().getFirst();
        assertEquals(name, addedProduct.getName());

        Batch addedBatch = stock.getBatches().getFirst();
        assertEquals(name, addedBatch.getProductName());

        assertDoesNotThrow(() -> {
            stock.addProduct(name, barcode, buyPrice, sellPrice, 50, expirationDate);
        });

        addedProduct = stock.getProducts().getFirst();
        addedBatch = stock.getBatches().getFirst();

        assertEquals(quantity + 50, addedProduct.getQuantity());
        assertEquals(quantity, addedBatch.getBatchQuantity());
    }

    @ParameterizedTest
    @CsvSource({
            "ProductB, 123, 10.0, 20.0, 100, 2023-12-31",
    })
    public void testRemoveProduct(String name, String barcode, double buyPrice, double sellPrice, int quantity, LocalDate expirationDate){
        assertDoesNotThrow(() -> {
            stock.addProduct(name, barcode, buyPrice, sellPrice, quantity, expirationDate);
        });
        Product addedProduct = stock.getProducts().getFirst();
        stock.removeProduct(addedProduct, 10, 0);

        Batch addedBatch = stock.getBatches().getFirst();

        assertEquals(quantity - 10, addedProduct.getQuantity());
        assertEquals(quantity - 10, addedBatch.getBatchQuantity());
    }

    @ParameterizedTest
    @CsvSource({
            "ProductC, 456, 0, 20.0, 100, 2023-12-31",
            "ProductD, 123, 15.0, -1, 150, 2023-12-31",
            "ProductF, 123, 15.0, 20.0, -10, 2023-12-31"
    })
    public void testInvalidInputValueReceiveProduct(String name, String barcode, double buyPrice, double sellPrice, int quantity, LocalDate expirationDate) {
        assertThrows(InvalidValueException.class, () -> {
            stock.addProduct(name, barcode, buyPrice, sellPrice, quantity, expirationDate);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "    , 456, 20.0, 20.0, 100, 2023-12-31",
            "ProductG,  , 15.0, 10.0, 150, 2023-12-31",
    })
    public void testBlankDescriptionReceiveProduct(String name, String barcode, double buyPrice, double sellPrice, int quantity, LocalDate expirationDate) {
        assertThrows(BlankDescriptionException.class, () -> {
            stock.addProduct(name, barcode, buyPrice, sellPrice, quantity, expirationDate);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "ProductH, 123, 10.0, 20.0, 100, 2023-11-27",
    })
    public void testExpirationDateExpired(String name, String barcode, double buyPrice, double sellPrice, int quantity, LocalDate expirationDate) {
        assertDoesNotThrow(() -> {
            stock.addProduct(name, barcode, buyPrice, sellPrice, quantity, expirationDate);
        });

        stock.ExpirationDate();

        Batch addedBatch = stock.getBatches().getFirst();
        assertEquals(buyPrice * 0.8, addedBatch.getBuyPrice());
    }

    @ParameterizedTest
    @CsvSource({
            "ProductH, 123, 10.0, 20.0, 100",
    })
    public void testExpirationDateValid(String name, String barcode, double buyPrice, double sellPrice, int quantity) {
        assertDoesNotThrow(() -> {
            stock.addProduct(name, barcode, buyPrice, sellPrice, quantity, LocalDate.now().plusDays(10));
        });

        stock.ExpirationDate();

        Batch addedBatch = stock.getBatches().getFirst();
        assertEquals(buyPrice, addedBatch.getBuyPrice());
    }

    @ParameterizedTest
    @CsvSource({
            "ProductI, 123, 10.0, 20.0, 100,  ",
    })
    public void testExpirationDateValid(String name, String barcode, double buyPrice, double sellPrice, int quantity, LocalDate expirationDate) {
        assertDoesNotThrow(() -> {
            stock.addProduct(name, barcode, buyPrice, sellPrice, quantity, expirationDate);
        });

        stock.ExpirationDate();

        Batch addedBatch = stock.getBatches().getFirst();
        assertEquals(buyPrice, addedBatch.getBuyPrice());
    }
}
