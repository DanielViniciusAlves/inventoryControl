package org.tppe;

import org.tppe.entities.Batch;
import org.tppe.entities.Branch;
import org.tppe.entities.Product;
import org.tppe.exceptions.BlankDescriptionException;
import org.tppe.exceptions.EstoqueNegativoException;
import org.tppe.exceptions.InvalidValueException;
import org.tppe.services.BranchService;
import org.tppe.services.StockServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) throws BlankDescriptionException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter branch name:");
        String[] branchName = scanner.nextLine().split("\\s");
        BranchService branchService = new BranchService();
        Branch defaultBranch = branchService.addBranch(branchName[0], new StockServices());

        int choice;
        do {
            System.out.println("Menu:");
            System.out.println("1. Receive Product");
            System.out.println("2. Find Product by Name");
            System.out.println("3. Find Product by Barcode");
            System.out.println("4. Return Product");
            System.out.println("5. Get Total Products by Barcode");
            System.out.println("6. Get Total Products by Name");
            System.out.println("7. Sell Product");
            System.out.println("8. Get Batch Total");
            System.out.println("9. Check Low Stock Alert");
            System.out.println("10. Transfer product between branches.");
            System.out.println("11. Add new branch.");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter product details (name, barcode, buyPrice, sellPrice, quantity, expirationDate): ");
                    String[] productDetails = scanner.nextLine().split("\\s");
                    defaultBranch.getStockServices().receiveProduct(productDetails[0], productDetails[1],
                            Double.parseDouble(productDetails[2]), Double.parseDouble(productDetails[3]),
                            Integer.parseInt(productDetails[4]), LocalDate.parse(productDetails[5]));
                    break;
                case 2:
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();
                    Product productNameInfo = defaultBranch.getStockServices().findProductByName(name);
                    System.out.println("Information found: " + productNameInfo.getName() + productNameInfo.getQuantity());
                    break;
                case 3:
                    System.out.print("Enter product barcode: ");
                    String barcode = scanner.nextLine();
                    Product productBarcodeInfo = defaultBranch.getStockServices().findProductByBarcode(barcode);
                    System.out.println("Information found: " + productBarcodeInfo.getName() + productBarcodeInfo.getQuantity());
                    break;
                case 4:
                    System.out.print("Enter product details for return (name, quantity): ");
                    String[] returnDetails = scanner.nextLine().split("\\s");
                    defaultBranch.getStockServices().returnProduct(returnDetails[0], Integer.parseInt(returnDetails[1]));
                    break;
                case 5:
                    System.out.print("Enter product barcode: ");
                    barcode = scanner.nextLine();
                    int productBarcodeQnt = defaultBranch.getStockServices().getTotalProductsByBarcode(barcode);
                    System.out.println("Quantity stored: " + productBarcodeQnt);
                    break;
                case 6:
                    System.out.print("Enter product name: ");
                    name = scanner.nextLine();
                    int productNameQnt = defaultBranch.getStockServices().getTotalProductsByName(name);
                    System.out.println("Quantity stored: " + productNameQnt);
                    break;
                case 7:
                    System.out.print("Enter product details for sale (name, quantity): ");
                    String[] saleDetails = scanner.nextLine().split("\\s");
                    defaultBranch.getStockServices().sellProduct(saleDetails[0], Integer.parseInt(saleDetails[1]));
                    break;
                case 8:
                    System.out.print("Enter batch ID (starts at 0): ");
                    int batchId = scanner.nextInt();
                    int batchTotal = defaultBranch.getStockServices().getBatchTotal(batchId);
                    System.out.println("Batch Total: " + batchTotal);
                    break;
                case 9:
                    System.out.print("Enter product details for low stock alert (name, minimum): ");
                    String[] lowStockDetails = scanner.nextLine().split("\\s");
                    boolean alert = defaultBranch.getStockServices().hasLowStockAlert(lowStockDetails[0], Integer.parseInt(lowStockDetails[1]));
                    if (alert) {
                        System.out.println("Low stock!");
                    } else {
                        System.out.println("Stock above minimum.");
                    }
                    break;
                case 10:
                    System.out.print("Enter product and branch details (productName, quantity, sourceBranch, destinationBranch): ");
                    String[] transferDetails = scanner.nextLine().split("\\s");
                    try {
                        defaultBranch.getStockServices().transferProduct(branchService, transferDetails[0], Integer.parseInt(transferDetails[1]), transferDetails[2], transferDetails[3]);
                    } catch (BlankDescriptionException | InvalidValueException | EstoqueNegativoException e ) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 11:
                    System.out.print("Enter branch name: ");
                    String[] branchDetails = scanner.nextLine().split("\\s");
                    branchService.addBranch(branchDetails[0], new StockServices());
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

        } while (choice != 0);

        scanner.close();
    }
}
