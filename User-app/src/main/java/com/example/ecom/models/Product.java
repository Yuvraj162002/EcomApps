package com.example.ecom.models;



import java.util.List;

public class Product {
    //common
    public String name;
    public int imageURL;
    public int type;
    //wbp:
    public float pricePerKg;
    public float minQuantity;
    //vbp:
    public List<Variants> variants;

    public Product(){

    }

    //wb PRODUCT CONSTRUCTOR:
    public Product(String name, int imageURL, float pricePerKg, float minQuantity) {
        type= ProductType.TYPE_WB;
        this.name = name;
        this.imageURL = imageURL;
        this.pricePerKg = pricePerKg;
        this.minQuantity = minQuantity;
    }
    //vb PRODUCT CONSTRUCTOR:
    public Product(String name, int imageURL, List<Variants> variants) {
        type= ProductType.TYPE_VB;
        this.name = name;
        this.imageURL = imageURL;
        this.variants = variants;
    }

}

