package org.tppe.services;

import org.tppe.entities.Batch;
import org.tppe.entities.Product;
import org.tppe.entities.Stock;
import org.tppe.exceptions.BlankDescriptionException;
import org.tppe.exceptions.InvalidValueException;

import java.time.LocalDate;

public class StockServices {
    private Stock stock;

    public StockServices() {
        this.stock = new Stock();
    }

    public Product findProductByName(String name) {
        for (Product product : this.stock.getProducts()) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public Product findProductByBarcode(String barcode) {
        for (Product product : this.stock.getProducts()) {
            if (product.getBarcode().equals(barcode)) {
                return product;
            }
        }
        return null;
    }

    public void receiveProduct(String name, String barcode, double buyPrice, double sellPrice, int quantity, LocalDate expirationDate) {
        try {
            this.stock.addProduct(name, barcode, buyPrice, sellPrice, quantity, expirationDate);

            Product receivedProduct = this.findProductByBarcode(barcode);
            Batch receivedBatch = this.stock.getBatches().get(this.stock.getBatches().size() - 1); // Último lote adicionado

            receivedProduct.setQuantity(receivedProduct.getQuantity() + quantity);

            System.out.println("Mercadoria recebida: " + name + " - Quantidade: " + quantity);

        } catch (BlankDescriptionException e) {
            throw new RuntimeException(e);
        } catch (InvalidValueException e) {
            throw new RuntimeException(e);
        }
    }


    public int getTotalProductsByName(String name) {
        Product product = this.findProductByName(name);
        return product != null ? product.getQuantity() : 0;
    }

    public int getTotalProductsByBarcode(String barcode) {
        Product product = this.findProductByBarcode(barcode);
        return product != null ? product.getQuantity() : 0;
    }

    public int getBatchTotal(int batchId) {
        for (Batch batch : this.stock.getBatches()) {
            if (batch.getBatchId() == batchId) {
                return batch.getBatchQuantity();
            }
        }
        return 0;
    }

    public void sellProduct(String productName, int quantity) {
        try {
            Product product = findProductByName(productName);

            if (product != null) {
                if (product.getQuantity() >= quantity) {
                    product.setQuantity(product.getQuantity() - quantity);

                    System.out.println("Venda processada para: " + productName + " - Quantidade: " + quantity);
                } else {
                    throw new InvalidValueException("Quantidade insuficiente em estoque para venda.");
                }
            } else {
                throw new InvalidValueException("Produto não encontrado para venda.");
            }
        } catch (InvalidValueException e) {
            throw new RuntimeException(e);
        }
    }

    public void transferProduct(String productName, int quantity, String sourceBranch, String destinationBranch) {
    }

    public void returnProduct(String productName, int quantity) {
        try {
            Product product = findProductByName(productName);

            if (product != null) {
                if (product.getQuantity() >= quantity) {
                    product.setQuantity(product.getQuantity() - quantity);

                    System.out.println("Devolução processada para: " + productName + " - Quantidade: " + quantity);
                } else {
                    throw new InvalidValueException("Quantidade insuficiente em estoque para devolução.");
                }
            } else {
                throw new InvalidValueException("Produto não encontrado para devolução.");
            }
        } catch (InvalidValueException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeProductInStock() {
    }
}
