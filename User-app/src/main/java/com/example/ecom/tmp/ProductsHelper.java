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
                 new Product("Apple",R.drawable.apple,new ArrayList<>(Arrays.asList(
                         new Variants("1Kg",100),
                         new Variants("2kg",180)
                 ))),
                 new Product("Green Apple", R.drawable.green_apple, new ArrayList<>(Arrays.asList(
                         new Variants("500g", 150),
                         new Variants("1kg", 280)))),
                 new Product("Ketchup", R.drawable.ketchup, new ArrayList<>(Arrays.asList(
                         new Variants("250g", 45),
                         new Variants("500g", 80),
                         new Variants("1kg", 150)))),

                 new Product("Pomegranate",R.drawable.pomegranate,200,1),
                 new Product("Onion",R.drawable.onion,new ArrayList<>(Arrays.asList(
                         new Variants("1.5Kg",70),
                         new Variants("5Kg",300)
                 ))),
                 new Product("Potato", R.drawable.potato, new ArrayList<>(Arrays.asList(
                         new Variants("500g", 25),
                         new Variants("1kg", 40)))),
                 new Product("Tomato", R.drawable.tomato, new ArrayList<>(Arrays.asList(
                         new Variants("1kg", 20),
                         new Variants("2kg", 35)
                 ))),
                 new Product("Black Grapes",R.drawable.black_grapes,100,1),
                 new Product("Mango",R.drawable.mango,new ArrayList<>(Arrays.asList(
                         new Variants("1Kg",150),
                         new Variants("2kg",290)))),
                 new Product("Sugar",R.drawable.sugar,50,1),
                 new Product("Dove Shampoo",R.drawable.dove,142,1),
                 new Product("Lux Soap",R.drawable.lux,50,1),
                 new Product("ToothBrush",R.drawable.brush,12,1)

         ) );


            return products;
        }

    }

