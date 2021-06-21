package com.example.ecom.tmp;

import com.example.android.models.Product;
import com.example.ecom.R;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductsHelper {

        public static List<Product> getProducts(){
         List<Product>products=new ArrayList<>(
         Arrays.asList(
                 new Product("Apple",R.drawable.apple,1f,100f)
         )
            );


            return products;
        }

    }

