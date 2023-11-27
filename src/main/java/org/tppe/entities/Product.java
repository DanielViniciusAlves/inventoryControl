package org.tppe.entities;

import org.tppe.exceptions.BlankDescriptionException;
import org.tppe.exceptions.InvalidValueException;

public class Product {
    private String name;
    private String barcode;
    private double buyPrice;
    private double sellPrice;
    private int quantity;

    public Product(String name, String barcode, double buyPrice, double sellPrice, int quantity) throws InvalidValueException, BlankDescriptionException {
        if(name == null || barcode == null || name.isBlank() || barcode.isBlank()){
            throw new BlankDescriptionException("DescricaoEmBrancoException");
        }
        if(buyPrice <= 0 || sellPrice <= 0 || quantity <= 0){
            throw new InvalidValueException("ValorInvalidoException");
        }

        this.name = name;
        this.barcode = barcode;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
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

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
