package org.tppe.services;

import org.tppe.entities.Batch;
import org.tppe.entities.Product;
import org.tppe.entities.Stock;
import org.tppe.exceptions.BlankDescriptionException;
import org.tppe.exceptions.InvalidValueException;

import java.time.LocalDate;
import java.util.Date;

public class StockServices {
    private Stock stock;

    public StockServices(){
        this.stock = new Stock();
    }

    public Product findProductByName(String name){
        for (Product product : this.stock.getProducts()){
            if(product.getName() == name){
                return product;
            }
        }
        return null;
    }
    public Product findProductByBarcode(String barcode){
        for (Product product : this.stock.getProducts()){
            if(product.getBarcode() == barcode){
                return product;
            }
        }
        return null;
    }

    public void receiveProduct(String name, String barcode, double buyPrice, double sellPrice, int quantity, LocalDate expirationDate){
        try {
            this.stock.addProduct(name, barcode, buyPrice, sellPrice, quantity, expirationDate);
        } catch (BlankDescriptionException e) {
            throw new RuntimeException(e);
        } catch (InvalidValueException e) {
            throw new RuntimeException(e);
        }
    }

    public int getTotalProductsByName(String name){
        Product product = this.findProductByName(name);
        return product.getQuantity();
    }

    public int getTotalProductsByBarcode(String barcode){
        Product product = this.findProductByBarcode(barcode);
        return product.getQuantity();
    }

    public int getBatchTotal(int batchId){
        for(Batch batch : this.stock.getBatches()){
            if(batch.getBatchId() == batchId){
                return batch.getBatchQuantity();
            }
        }
        return 0;
    }

    public void sellProduct(){

    }

    public void transferProduct(){

    }

    public void returnProduct(){

    }

    public void changeProductInStock(){

    }
}
