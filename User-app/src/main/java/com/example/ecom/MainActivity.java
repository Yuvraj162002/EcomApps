package com.example.ecom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Parcelable;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ecom.Constants.Constants;
import com.example.ecom.controllers.AdapterCallbacksListener;
import com.example.ecom.controllers.ProductsAdapter;
import com.example.ecom.databinding.ActivityCartBinding;
import com.example.ecom.databinding.ActivityMainBinding;
import com.example.ecom.models.Cart;
import com.example.ecom.models.Product;
import com.example.ecom.tmp.ProductsHelper;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;
    ActivityCartBinding binding;
    private ProductsAdapter adapter;
    List<Product> productList=new ArrayList<>();

    Cart cart;
    private boolean isUpdated;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        cart=new Cart();
        loadCartFromSharePreferences();
        setupAdapter();
        buttonEventHandler();
    }

    private void buttonEventHandler() {
        b.checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CartActivity.class);
                intent.putExtra(Constants.KEY,cart);
                startActivity(intent);
            }
        });
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
        this.productList=ProductsHelper.getProducts();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.appmenu,menu);

        SearchView searchView=(SearchView)menu.findItem(R.id.search).getActionView();

        //Listener to add Search Function of adapter:
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.myCart){
            myCart();
        }
        return super.onOptionsItemSelected(item);
    }

    private void myCart() {
        if(cart.cartItems.isEmpty()){
            b.NoItems.setVisibility(View.VISIBLE);
            b.list.setVisibility(View.GONE);
        }
        else {
            b.list.setVisibility(View.VISIBLE);
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            intent.putExtra(Constants.KEY, cart);
            startActivity(intent);
        }
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
