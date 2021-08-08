package com.example.ecom.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.widget.SearchView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.ecom.Constants.Constants;
import com.example.ecom.FireBaseHelpers.ProductsHelper;
import com.example.ecom.R;
import com.example.ecom.controllers.AdapterCallbacksListener;
import com.example.ecom.controllers.ProductsAdapter;
import com.example.ecom.databinding.ActivityCartBinding;
import com.example.ecom.databinding.ActivityMainBinding;
import com.example.ecom.models.Cart;
import com.example.ecom.models.Inventory;
import com.example.ecom.models.Product;
import com.example.ecom.tmp.ProductHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;
    ActivityCartBinding binding;
    private ProductsAdapter adapter;
    List<Product> productList=new ArrayList<>();
    List<Product> list;
    private int RECEIVED_CART=1;
    Cart cart;
    private boolean isUpdated;
    private ProductsHelper productsHelper=new ProductsHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        cart=new Cart();
        loadCartFromSharePreferences();
        setupAdapter();
        //Setup();
        FetchProductsFromCloudFirestore();
        buttonEventHandler();
    }

    private void FetchProductsFromCloudFirestore() {
        if(productsHelper.isOffline()){
            productsHelper.showToast(MainActivity.this,"Oops No Internet!!");
            return;
        }
        productsHelper.ShowLoadingDialog(this);
        productsHelper.db.collection("Inventory").document("Product List")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Inventory inventory = documentSnapshot.toObject(Inventory.class);
                            list = inventory.productsList;
                        } else {
                            list = new ArrayList<>();
                        }
                        setupAdapter();
                        productsHelper.hideLoadingDialog();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        productsHelper.hideLoadingDialog();
                    }
                });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECEIVED_CART) {
            if (resultCode == RESULT_OK) {
                adapter.notifyDataSetChanged();
                updateCartSummary();
            }
        }
    }
}
