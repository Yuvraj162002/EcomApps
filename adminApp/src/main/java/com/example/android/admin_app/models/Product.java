package com.example.android.admin_app.models;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {
    //common
    public String name;
    public int imageURL;
    public int type;
    //wbp:
    public float pricePerKg;
    public float minQuantity;
    //vbp:
    public List<Variants> variants;
    public static final int WEIGHT_BASED = 0, VARIANTS_BASED = 1;

    public Product(){
    }

    public Product(String name, int pricePerKg, float minQuantity){
        type=WEIGHT_BASED;
        this.name = name;
        this.pricePerKg = pricePerKg;
        this.minQuantity = minQuantity;
    }

    //wb PRODUCT CONSTRUCTOR:
    public Product(String name, int imageURL, float pricePerKg, float minQuantity) {
        type= ProductType.TYPE_WB;
        this.name = name;
        this.imageURL = imageURL;
        this.pricePerKg = pricePerKg;
        this.minQuantity = minQuantity;
    }
    public Product(String name) {
        type = VARIANTS_BASED;
        this.name = name;
    }
    //vb PRODUCT CONSTRUCTOR:
    public Product(String name, int imageURL, List<Variants> variants) {
        type= ProductType.TYPE_VB;
        this.name = name;
        this.imageURL = imageURL;
        this.variants = variants;
    }
    public String convertMinQtyToWeight(float quantity) {
        if (minQuantity < 1) {
            return (int) (minQuantity * 1000) + "g";
        }
        return (int) (minQuantity) + "kg";
    }
    public String variantsString() {
        String variant = variants.toString();
        return variant.replace("[", "")
                .replace("]", "")
                .replace(", ", "\n");
    }
    public void makeWeightProduct(String name, int pricePerKg, float minQty) {
        type = WEIGHT_BASED;
        this.name = name;
        this.pricePerKg = pricePerKg;
        this.minQuantity = minQty;
    }
    public void makeVariantProduct(String name) {
        type = VARIANTS_BASED;
        this.name = name;
    }
    public void fromVariantStrings(String[] vs) {
        variants = new ArrayList<>();
        for (String s : vs) {
            String[] v = s.split(",");
            variants.add(new Variants(v[0], Integer.parseInt(v[1])));
        }
    }

}

