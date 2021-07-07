package com.example.ecom.models;

import java.io.Serializable;

public class Variants implements Serializable {
    public String name;
    public float price;

    public Variants(String name, float price) {
        this.name = name;
        this.price = price;
    }
}
