package org.tppe.services;

import org.tppe.entities.Product;
import org.tppe.entities.Stock;
import org.tppe.exceptions.BlankDescriptionException;
import org.tppe.exceptions.InvalidValueException;

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

    public void receiveProduct(String name, String barcode, double buyPrice, double sellPrice, int quantity){
        try {
            this.stock.addProduct(name, barcode, buyPrice, sellPrice, quantity);
        } catch (BlankDescriptionException e) {
            throw new RuntimeException(e);
        } catch (InvalidValueException e) {
            throw new RuntimeException(e);
        }
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
