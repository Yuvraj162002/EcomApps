package com.example.ecom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;


import com.example.ecom.controllers.AdapterCallbacksListener;
import com.example.ecom.controllers.ProductsAdapter;
import com.example.ecom.databinding.ActivityMainBinding;
import com.example.ecom.models.Cart;
import com.example.ecom.models.Product;
import com.example.ecom.tmp.ProductsHelper;
import com.google.gson.Gson;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;
    private ProductsAdapter adapter;
    List<Product> productList=ProductsHelper.getProducts();
    Cart cart;
    private boolean isUpdated;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        loadCartFromSharePreferences();

        setupAdapter();
    }

    private void loadCartFromSharePreferences() {
        String cart = getPreferences(MODE_PRIVATE).getString("CART", null);

        if(cart==null){
            this.cart=new Cart();
            return;
        }

        this.cart=new Gson().fromJson(cart,Cart.class);

        updateCartSummary();
    }

    private void setupAdapter() {
        AdapterCallbacksListener listener = new AdapterCallbacksListener() {
            @Override
            public void onCartUpdated(int position) {
                updateCartSummary();
                isUpdated=true;
                adapter.notifyItemChanged(position);
            }
        };

        adapter = new ProductsAdapter(this
                , productList
                , cart
                , listener);

        b.list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        b.list.setAdapter(adapter);
        b.list.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateCartSummary() {
        if(!cart.cartItems.isEmpty()){
            b.totalItems.setText(cart.noOfItems+"items");
            b.totalAmount.setText("₹"+String.format("%.2f",cart.total));

            b.cartSummary.setVisibility(View.VISIBLE);
        }
        else {
            b.cartSummary.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isUpdated) {
            Gson gson = new Gson();
            String json = gson.toJson(cart);
            getPreferences(MODE_PRIVATE).edit().putString("CART", json).apply();
            isUpdated=false;
        }
    }
}
