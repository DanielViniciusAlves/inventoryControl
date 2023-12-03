package org.tppe.entities;

import org.tppe.exceptions.BlankDescriptionException;
import org.tppe.exceptions.InvalidValueException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Stock {
    private List<Product> products;
    private List<Batch> batches;
    private int batchId;

    public Stock(){
        this.batches = new ArrayList<>();
        this.products = new ArrayList<>();
        this.batchId = 0;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Batch> getBatches() {
        return batches;
    }
    
    public Product findProductByName(String name) {
        for (Product product : this.getProducts()) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }
    
    public void changeProductInStock(Product product,int quantity) {
    }
    
    public void addProduct(String name, String barcode, double buyPrice, double sellPrice, int quantity, LocalDate expirationDate) throws BlankDescriptionException, InvalidValueException {
        if(name == null || barcode == null || name.isBlank() || barcode.isBlank()){
            throw new BlankDescriptionException("DescricaoEmBrancoException");
        }
        if(buyPrice <= 0 || sellPrice <= 0 || quantity <= 0){
            throw new InvalidValueException("ValorInvalidoException");
        }

        boolean productExist = false;
        for (Product product : this.products){
            if(product.getName() == name){
                product.changeProductInStock(product.getQuantity() + quantity);
                productExist = true;
            }
        }
        if(!productExist){
            Product product = new Product(name, barcode, quantity);
            this.products.add(product);
        }
        Batch batch = new Batch(this.batchId, name, barcode, buyPrice, sellPrice, quantity, expirationDate);
        this.batches.add(batch);
        this.batchId++;
    }

    public void removeProduct(Product product, int quantity, int batchId){
        for (Product tempProduct : this.products){
            if(product.getName() == tempProduct.getName()){
                product.changeProductInStock(product.getQuantity() - quantity);
            }
        }
        for (Batch tempBatch : this.batches){
            if(tempBatch.getBatchId() == batchId){
                tempBatch.setBatchQuantity(tempBatch.getBatchQuantity() - quantity);
            }
        }
    }

    public void ExpirationDate(){
        for (Batch batch : this.batches){
            if(batch.getExpirationDate() != null){
                if(7 >= (ChronoUnit.DAYS.between(LocalDate.now(), batch.getExpirationDate()))){
                    double newPrice = batch.getBuyPrice() * 0.8;
                    batch.setBuyPrice(newPrice);
                }
            }
        }
    }
}
