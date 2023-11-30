package org.tppe;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.tppe.entities.Product;
import org.tppe.exceptions.BlankDescriptionException;
import org.tppe.exceptions.InvalidValueException;
import org.tppe.services.StockServices;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @ParameterizedTest
    @CsvSource({
            "ProductF, 123, 15.0, 25.0, 150, 2023-12-31"
    })
    public void testReturnProduct(String name, String barcode, double buyPrice, double sellPrice, int quantity, LocalDate expirationDate) {
        stockServices.receiveProduct(name, barcode, buyPrice, sellPrice, quantity, expirationDate);

        Product returnedProduct = stockServices.findProductByBarcode(barcode);
        int initialQuantity = returnedProduct.getQuantity();
        int quantityToReturn = 50;
        stockServices.returnProduct(name, quantityToReturn);

        assertEquals(initialQuantity - quantityToReturn, returnedProduct.getQuantity());
    }
    
    @ParameterizedTest
    @CsvSource({
            "ProductA, 123, 10.0, 20.0, 100, 2023-12-31, 50, BranchA, BranchB, ProductB, 456, 15.0, 25.0, 150, 2023-12-31",
            "ProductC, 789, 12.0, 22.0, 120, 2023-12-31, 30, BranchC, BranchD, ProductD, 789, 12.0, 22.0, 30, 2023-12-31"
    })
    public void testTransferProduct(
            String sourceProductName, String sourceBarcode, double sourceBuyPrice, double sourceSellPrice, int sourceQuantity, LocalDate sourceExpirationDate,
            int transferQuantity,
            String sourceBranch, String destinationBranch,
            String destinationProductName, String destinationBarcode, double destinationBuyPrice, double destinationSellPrice, int destinationQuantity, LocalDate destinationExpirationDate) {

        
        stockServices.receiveProduct(sourceProductName, sourceBarcode, sourceBuyPrice, sourceSellPrice, sourceQuantity, sourceExpirationDate);
        stockServices.receiveProduct(destinationProductName, destinationBarcode, destinationBuyPrice, destinationSellPrice, destinationQuantity, destinationExpirationDate);

        
        Product sourceProductBefore = stockServices.findProductByName(sourceProductName);
        Product destinationProductBefore = stockServices.findProductByBarcode(destinationBarcode);

        
        stockServices.transferProduct(sourceProductName, transferQuantity, sourceBranch, destinationBranch);

        
        Product sourceProductAfter = stockServices.findProductByName(sourceProductName);
        Product destinationProductAfter = stockServices.findProductByBarcode(destinationBarcode);

        
        assertEquals(sourceProductBefore.getQuantity() - transferQuantity, sourceProductAfter.getQuantity());
        assertEquals(destinationProductBefore.getQuantity() + transferQuantity, destinationProductAfter.getQuantity());
    }

    @ParameterizedTest
    @CsvSource({
            "ProductE, 012, 18.0, 28.0, 30, 2023-12-31, 60, BranchA, BranchB" // Tentativa de transferência com quantidade insuficiente
    })
    public void testTransferProductInsufficientQuantity(String productName, String barcode, double buyPrice, double sellPrice, int quantity, LocalDate expirationDate, int transferQuantity, String sourceBranch, String destinationBranch) {
        
        stockServices.receiveProduct(productName, barcode, buyPrice, sellPrice, quantity, expirationDate);

        
        assertThrows(InvalidValueException.class, () -> stockServices.transferProduct(productName, transferQuantity, sourceBranch, destinationBranch));
    }

}
