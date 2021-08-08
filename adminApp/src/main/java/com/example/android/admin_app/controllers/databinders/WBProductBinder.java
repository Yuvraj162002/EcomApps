package com.example.android.admin_app.controllers.databinders;

import android.content.Context;
import android.view.ContextMenu;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.android.admin_app.Activities.ProductsActivity;
import com.example.android.admin_app.R;
import com.example.android.admin_app.controllers.AdapterCallbacksListener;
import com.example.android.admin_app.controllers.ProductsAdapter;
import com.example.android.admin_app.databinding.ItemWbProductBinding;
import com.example.android.admin_app.models.Cart;
import com.example.android.admin_app.models.Product;


public class WBProductBinder {

    private Context context;
    ProductsAdapter adapter;
    private Cart cart;
    private AdapterCallbacksListener listener;



    public WBProductBinder(Context context, Cart cart, AdapterCallbacksListener listener){
        this.context = context;
        this.cart = cart;
        this.listener = listener;
    }

   public void bind(ItemWbProductBinding b, Product product, int position){


        b.productsName.setText(product.name);
        b.productPrice.setText(String.format("₹%.2f/kg",product.pricePerKg));
        b.imageViewWB.setImageResource(product.imageURL);
        showContextMenu(b.getRoot());
    }
    public void showContextMenu(ConstraintLayout root){
        root.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                if(!(context instanceof ProductsActivity)){
                    return;
                }
                ProductsActivity activity=((ProductsActivity)context);
                if(!activity.isDragModeOn){
                activity.getMenuInflater().inflate(R.menu.contextmenu,menu);
            }}
        });
    }

}
