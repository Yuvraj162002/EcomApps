package com.example.ecom.models;

import java.io.Serializable;
import java.util.List;

public class Inventory implements Serializable {
    public List<Product> productsList;

    public Inventory(){

    }
    public Inventory(List<Product> productsList){
        this.productsList=productsList;
    }
}
