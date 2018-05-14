package com.inducesmile.androidsqliteexample;


public class Product {
    private	int	id;
    int photoId;
    private	String name;
    private	int	quantity;
    private String tag;
    private int price;

    public Product(String name, int quantity, String tag, int price) {
        this.name = name;
        this.quantity = quantity;
        this.tag = tag;
        this.price = price;
    }

    public Product(int id, String name, int quantity, String tag, int price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.tag = tag;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getTag() {
        return tag;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
