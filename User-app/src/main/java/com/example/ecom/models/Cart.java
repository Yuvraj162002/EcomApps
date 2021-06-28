package com.example.ecom.models;



import java.util.HashMap;

public class Cart {
    public HashMap<String, CartItem> cartItems=new HashMap<>();
    public float total=0;
    public int noOfItems=0;

    //To Add wb products:
   public void add(Product product, float quantity) {
       if(quantity==0){
           removeWBP(product);
           return;
       }

        // if item already exists in cart:
        if (cartItems.containsKey(product.name)) {
            total -= cartItems.get(product.name).Cost();
            cartItems.get(product.name).qty = quantity;
        }
        //if item doesn't exists in cart:
        else {
            CartItem newItem = new CartItem(product.name, product.pricePerKg, quantity);
            cartItems.put(product.name, newItem);

            noOfItems++;
        }
        //Updated cart summary:
        total += cartItems.get(product.name).Cost();
    }


    //To Add vb products:
    public void add(Product product, Variants variants, int qty) {
        String key= product.name+ " " + variants.name;
        //if already exists:
        if(cartItems.containsKey(key)){
            total-=cartItems.get(key).Cost();
            noOfItems-=cartItems.get(key).qty;
            cartItems.get(key).qty=qty;
        }
        //Added for the first time:
        else{
            CartItem newItem=new CartItem(product.name, variants.price,qty);
            cartItems.put(key,newItem);
        }
        //Updated cart summary:
        noOfItems+=qty;
        total+= cartItems.get(key).Cost();

        if(cartItems.get(key).qty==0){
            cartItems.remove(key);
        }
    }

    public void removeWBP(Product product) {
        //Update cart:
        if(cartItems.containsKey(product.name)) {
            total -= cartItems.get(product.name).Cost();
            noOfItems--;
            cartItems.remove(product.name);
        }
    }

    //to remove vb products:
    public void removeAllVBP(Product product){
        for(Variants variants : product.variants){
            String key= product.name+ " " + variants.name;

            if(cartItems.containsKey(key)){
                //Update cart:
                total-=cartItems.get(key).Cost();
                noOfItems-=cartItems.get(key).qty;
                cartItems.remove(key);
            }

        }
    }
}
