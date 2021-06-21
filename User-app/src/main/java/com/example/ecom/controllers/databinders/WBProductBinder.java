package com.example.ecom.controllers.databinders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.android.models.Cart;
import com.example.android.models.Product;
import com.example.ecom.MainActivity;
import com.example.ecom.controllers.AdapterCallbacksListener;
import com.example.ecom.databinding.DialogWeightPickerBinding;
import com.example.ecom.databinding.ItemWbProductBinding;
import com.example.ecom.dialogs.WeightPickerDialog;


public class WBProductBinder {

    private Context context;
    private Cart cart;
    private AdapterCallbacksListener listener;
    private LayoutInflater inflater;


    public WBProductBinder(Context context, Cart cart, AdapterCallbacksListener listener){
        this.context = context;
        this.cart = cart;
        this.listener = listener;
        this.inflater=((MainActivity)context).getLayoutInflater();
    }

   public void bind(ItemWbProductBinding b, Product product, int position){


        b.productsName.setText(product.name);
        b.productPrice.setText(String.format("₹%.2f/kg",product.pricePerKg));
        b.imageViewWB.setImageResource(product.imageURL);

        buttonEventHandlers(b,product,position);

        checkWbProductInCart(b,product);
    }

    private void checkWbProductInCart(ItemWbProductBinding b, Product product) {
        if(cart.cartItems.containsKey(product.name)){
            b.nonZeroQtyGroupWB.setVisibility(View.VISIBLE);
            b.addBtn.setVisibility(View.GONE);
            b.qty.setText(cart.cartItems.get(product.name).qty + "Kg");
        }
        else{
            b.nonZeroQtyGroupWB.setVisibility(View.GONE);
            b.addBtn.setVisibility(View.VISIBLE);
        }

    }

    private void buttonEventHandlers(ItemWbProductBinding b,Product product,int position) {
       b.addBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            new WeightPickerDialog(context,cart).show(product, new WeightPickerDialog.WeightPickerCompleteListener() {
                @Override
                public void onCompleted() {
                    checkWbProductInCart(b,product);
                    listener.onCartUpdated();
                }
            });
           }
       });
       b.editBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               new WeightPickerDialog(context,cart).show(product, new WeightPickerDialog.WeightPickerCompleteListener() {
                   @Override
                   public void onCompleted() {
                       checkWbProductInCart(b,product);
                   }
               });

           }
       });
    }

}
