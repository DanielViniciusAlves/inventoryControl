package org.tppe;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.tppe.entities.Branch;
import org.tppe.entities.Product;
import org.tppe.exceptions.BlankDescriptionException;
import org.tppe.exceptions.EstoqueNegativoException;
import org.tppe.exceptions.InvalidValueException;
import org.tppe.services.BranchService;
import org.tppe.services.StockServices;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBranchServices {

    private static BranchService branchService;

    @BeforeAll
    public static void init(){
        branchService = new BranchService();
    }

    @ParameterizedTest
    @CsvSource({
            "ProductA",
            "ProductB"
    })
    public void testAddBranch(String name) throws BlankDescriptionException {
        branchService.addBranch(name, new StockServices());

        Branch foundBranch = branchService.findBranch(name);

        assertEquals(name, foundBranch.getName());
    }


    @ParameterizedTest
    @CsvSource({
            "BranchA, BranchB, 100, productA",
    })
    public void testTransferProduct(String destination, String source, int quantity, String productName) throws BlankDescriptionException, InvalidValueException, EstoqueNegativoException {
        branchService.addBranch(destination, new StockServices());
        Branch branch = branchService.addBranch(source, new StockServices());

        branch.getStockServices().receiveProduct(productName, "123", 10.0, 20.0, 100, LocalDate.parse("2023-12-31"));
        branch.getStockServices().transferProduct(branchService, productName, quantity, source, destination);

        assertEquals(0, branch.getStockServices().findProductByName("productA").getQuantity());
    }
}
