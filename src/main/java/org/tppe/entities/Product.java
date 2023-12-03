package org.tppe.entities;

import org.tppe.exceptions.BlankDescriptionException;
import org.tppe.exceptions.EstoqueNegativoException;
import org.tppe.exceptions.InvalidValueException;

public class Product {
    private String name;
    private String branch;
    private String barcode;
    private int quantity;
    private int minimumLimit;
    private String supplier;

    public Product(String name, String barcode, int quantity) {
        this.name = name;
        this.barcode = barcode;
        this.quantity = quantity;
        this.supplier = supplier;
        
    }

    public String getFilial() {
        return this.branch;
    }
    public void setFilial(String filial) {
        this.branch = filial;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void changeProductInStock(int quantity) throws EstoqueNegativoException {
        if(quantity<0){
            this.quantity = 0;
            throw new EstoqueNegativoException("EstoqueNegativoException");
        } else{
            this.quantity = quantity;
        }
    }

    public void setMinimumLimit(int minimumLimit) {
        this.minimumLimit = minimumLimit;
    }

    public int getMinimumLimit() {
        return minimumLimit;
    }
    
    public String getSupplier() {
        return supplier;
    }
}
