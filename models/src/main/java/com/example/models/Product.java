package com.example.models;

import java.util.List;

public class Product {

    //COMMON..
    public String name;
    public String imageURL;
    public int type;
    // public com.company.Assignment4_1.Model.Variant[] variantList;
    // public JavaIOFileDescriptorAccess variantsList;
    //WEIGHT BASED PRODUCT..
    Float minQut;
    public  Float pricePerKg;

    //VARIANT BASED PRODUCT..
    public List<Variant> Variant;

    //WB..
    public Product(String name, String imageURL, Float minQut, Float pricePerKg) {
        type = ProductType.TYPE_Weightbased;
        this.name = name;
        this.imageURL = imageURL;
        this.minQut = minQut;
        this.pricePerKg = pricePerKg;
    }

    //VB..
    public Product(String name, String imageURL, List<Variant> variant) {

        type = ProductType.TYPE_Varientbased;
        this.name = name;
        this.imageURL = imageURL;
        this.Variant = variant;
    }

    //public Product(String productName, String imageURL, List<com.company.Assignment4_1.Model.Variant> var) {
    //  }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        if (type == ProductType.TYPE_Weightbased)
            builder.append("WeightBased { ");
        else
            builder.append("VarientBased { ");

        builder.append("name = ").append(name);

        if (type == ProductType.TYPE_Varientbased) {
            builder.append("minQty = ").append(minQut);
            builder.append("pricePerKg = ").append(pricePerKg);
        } else {
            builder.append("VarientBased { ");
            builder.append(",variant = ").append(Variant);
        }
        builder.append(" } ");

        return builder.toString();


    }
}


