package org.tppe.entities;

import java.time.LocalDate;
import java.util.Date;

public class Batch {

    private int batchId;
    private String productName;
    private String barcode;
    private double buyPrice;
    private double sellPrice;
    private int batchQuantity;

    private LocalDate expirationDate;

    public Batch(int batchId, String productName, String barcode, double buyPrice, double sellPrice, int batchQuantity, LocalDate expirationDate) {
        this.batchId = batchId;
        this.productName = productName;
        this.barcode = barcode;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.batchQuantity = batchQuantity;
        this.expirationDate = expirationDate;
    }

    public int getBatchId() {
        return batchId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public int getBatchQuantity() {
        return batchQuantity;
    }

    public void setBatchQuantity(int batchQuantity) {
        this.batchQuantity = batchQuantity;
    }


    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
