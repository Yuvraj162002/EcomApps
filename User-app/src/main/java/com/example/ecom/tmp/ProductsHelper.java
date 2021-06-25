package com.example.ecom.tmp;

import com.example.android.models.Product;
import com.example.android.models.Variants;
import com.example.ecom.R;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductsHelper {

        public static List<Product> getProducts(){
         List<Product>products=new ArrayList<>(
         Arrays.asList(
                 new Product("Apple", R.drawable.apple, 80.00f, 0.5f),
                 new Product("Kiwi", R.drawable.kiwi, new ArrayList<>(Arrays.asList(
                         new Variants("500g", 90),
                         new Variants("1kg", 150))))
         )
            );


            return products;
        }

    }

