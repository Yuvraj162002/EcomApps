package com.example.ecom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.android.models.Cart;
import com.example.android.models.Product;
import com.example.ecom.controllers.AdapterCallbacksListener;
import com.example.ecom.controllers.ProductsAdapter;
import com.example.ecom.databinding.ActivityMainBinding;
import com.example.ecom.tmp.ProductsHelper;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;
    private ProductsAdapter adapter;
    List<Product> productList=ProductsHelper.getProducts();
    Cart cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        setupAdapter();
    }

    private void setupAdapter() {
        AdapterCallbacksListener listener = new AdapterCallbacksListener() {
            @Override
            public void onCartUpdated(int position) {
                updateCartSummary();
                adapter.notifyItemChanged(position,"payload");
            }
        };

        adapter = new ProductsAdapter(this
                , productList
                , cart
                , listener);


        b.list.setAdapter(adapter);
        b.list.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateCartSummary() {
        if(!cart.cartItems.isEmpty()){
            b.noOfItems.setText(cart.noOfItems+"items");
            b.total.setText("₹"+String.format("%.2f",cart.total));

            b.cartSummary.setVisibility(View.VISIBLE);
        }
        else {
            b.cartSummary.setVisibility(View.GONE);
        }
    }
}