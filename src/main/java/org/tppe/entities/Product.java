package org.tppe.entities;

import org.tppe.exceptions.BlankDescriptionException;
import org.tppe.exceptions.InvalidValueException;

public class Product {
    private String name;
    private String barcode;
    private int quantity;

    public Product(String name, String barcode, int quantity) {
        this.name = name;
        this.barcode = barcode;
        this.quantity = quantity;
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
