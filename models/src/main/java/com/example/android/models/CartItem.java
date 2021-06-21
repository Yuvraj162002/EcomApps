package com.example.android.models;

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

    @Override
    public String toString() {
        return "\n\t" + name + " ( " +
                String.format("%f X %f = %f", unitPrice, qty, Cost()) +
                " )";

    }
}
