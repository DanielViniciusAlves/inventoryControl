package org.tppe.entities;

import org.tppe.exceptions.BlankDescriptionException;
import org.tppe.exceptions.InvalidValueException;

import java.util.ArrayList;
import java.util.List;

public class Stock {
    private List<Product> products;

    public Stock(){
        this.products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(String name, String barcode, double buyPrice, double sellPrice, int quantity){
        boolean productExist = false;
        for (Product product : this.products){
            if(product.getName() == name){
                product.setQuantity(product.getQuantity() + quantity);
                productExist = true;
            }
        }
        if(!productExist){
            Product product = new Product(name, barcode, buyPrice, sellPrice, quantity);
            this.products.add(product);
        }
    }

    public void removeProduct(Product product, int quantity){
        for (Product tempProduct : this.products){
            if(product.getName() == tempProduct.getName()){
                product.setQuantity(product.getQuantity() - quantity);
            }
        }
    }
}
