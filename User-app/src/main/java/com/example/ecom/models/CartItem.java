package com.example.ecom.models;

public class CartItem {
    public float qty;
    String name;
    float unitPrice;

    public CartItem(String name, float unitPrice, float qty) {
        this.name=name;
        this.unitPrice=unitPrice;
        this.qty = qty;
    }

    public float Cost(){
        return unitPrice* qty;
    }

}
