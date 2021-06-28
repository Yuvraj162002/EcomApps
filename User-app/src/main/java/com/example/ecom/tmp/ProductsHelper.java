package com.example.ecom.tmp;

import com.example.ecom.R;
import com.example.ecom.models.Product;
import com.example.ecom.models.Variants;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductsHelper {

        public static List<Product> getProducts(){
         List<Product>products=new ArrayList<>(
         Arrays.asList(
                 new Product("Kiwi", R.drawable.kiwi, new ArrayList<>(Arrays.asList(
                         new Variants("500g", 90),
                         new Variants("1kg", 150)))),
                 new Product("Surf Excel", R.drawable.surf_excel, new ArrayList<>(Arrays.asList(
                         new Variants("1kg", 95),
                         new Variants("2kg", 180),
                         new Variants("5kg", 400)))),

                 new Product("Orange",R.drawable.orange,70,1),
                 new Product("Apple",R.drawable.apple,new ArrayList<>(Arrays.asList(
                         new Variants("1Kg",100),
                         new Variants("2kg",180)
                 )))

         )
            );


            return products;
        }

    }

