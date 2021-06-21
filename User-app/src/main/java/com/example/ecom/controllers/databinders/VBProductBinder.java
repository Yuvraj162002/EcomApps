package com.example.ecom.controllers.databinders;

import android.content.Context;

import com.example.android.models.Cart;
import com.example.android.models.Product;
import com.example.ecom.controllers.AdapterCallbacksListener;
import com.example.ecom.databinding.ItemVbProductBinding;


public class VBProductBinder {
    private Context context;
    private Cart cart;
    private AdapterCallbacksListener listener;

    public VBProductBinder(Context context, Cart cart, AdapterCallbacksListener listener){
        this.context = context;
        this.cart = cart;
        this.listener = listener;
    }

    public void bind(ItemVbProductBinding b, Product product){

    }
}
