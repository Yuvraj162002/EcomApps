package com.example.android.models;



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
        type=ProductType.TYPE_WB;
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

             if (this.type ==ProductType.TYPE_WB) {
                stringBuilder.append("WeightBasedProduct { ");
             } else {
                 stringBuilder.append("VariantBasedProduct { ");
             }

             stringBuilder.append("name = ").append(this.name);

             if (this.type ==ProductType.TYPE_VB) {
                 stringBuilder.append("minQty = ").append(this.minQuantity);
                 stringBuilder.append(", pricePerKg = ").append(this.pricePerKg);
             } else {
                 stringBuilder.append(", variants = ").append(this.variants);
             }
             stringBuilder.append(" } ");

             return stringBuilder.toString();
    }
}

