package com.example.android.admin_app.models;

import java.io.Serializable;

public class CartItem implements Serializable {
    public float qty;
    public String name;
    public float unitPrice;

    public CartItem(String name, float unitPrice, float qty) {
        this.name=name;
        this.unitPrice=unitPrice;
        this.qty = qty;
    }
    public CartItem(String name, float unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
        qty = 1;
    }


    public float Cost(){
        return unitPrice* qty;
    }

}
