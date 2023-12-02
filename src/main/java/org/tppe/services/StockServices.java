package org.tppe.services;

import org.tppe.entities.Batch;
import org.tppe.entities.Product;
import org.tppe.entities.Stock;
import org.tppe.exceptions.BlankDescriptionException;
import org.tppe.exceptions.InvalidValueException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class StockServices {
    private Stock stock;
    private Map<String, Boolean> lowStockAlerts; 

    public StockServices() {
        this.stock = new Stock();
        this.lowStockAlerts = new HashMap<>();
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

                    if (product.getQuantity() <= product.getMinimumLimit()) {
                        lowStockAlerts.put(productName, true); // Configura o alerta de estoque mínimo
                    }

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

    public boolean hasLowStockAlert(String productName, int minimumLimit) {
        Product product = findProductByName(productName);

        if (product != null) {
            int currentQuantity = product.getQuantity();
            boolean alert = checkLowStockAlert(productName, currentQuantity, minimumLimit);
            return alert;
        }

        System.out.println("Produto não encontrado: " + productName);
        return false;
    }

    private boolean checkLowStockAlert(String productName, int currentQuantity, int minimumLimit) {
        System.out.println("Produto: " + productName + ", Quantidade Atual em Estoque: " + currentQuantity + ", Limite Pré-definido: " + minimumLimit);
        boolean alert = currentQuantity <= minimumLimit;
        System.out.println("Alerta de Estoque Mínimo: " + alert);
        return alert;
    }
}
