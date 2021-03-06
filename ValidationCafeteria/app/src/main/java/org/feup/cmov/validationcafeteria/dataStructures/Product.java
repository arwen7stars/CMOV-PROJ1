package org.feup.cmov.validationcafeteria.dataStructures;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String image;
    private double price;
    private int quantity = 0;

    public Product(int id, String name, double price, String image, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }

    public double getTotalPriceRounded() {
        double totalPrice = (double) quantity * price;

        double rounded = (double) Math.round(totalPrice * 100.0) / 100.0;
        return rounded;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

}
