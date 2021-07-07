package com.example.ecom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecom.Constants.Constants;
import com.example.ecom.databinding.ActivityCartBinding;
import com.example.ecom.databinding.CartItemsBinding;
import com.example.ecom.models.Cart;
import com.example.ecom.models.CartItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import static android.widget.LinearLayout.HORIZONTAL;

public class CartActivity extends AppCompatActivity {

    ActivityCartBinding b;
    Cart cart;
    CartItem cartItem;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        Intent intent=getIntent();
        cart=(Cart)intent.getSerializableExtra(Constants.KEY);

        ShowCartItems();

        showTotal();

        b.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CartActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });

       // showMap();

    }

 /*   private void showMap() {
        b.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_map);
                dialog.show();
                GoogleMap googleMap;


                MapView mMapView = (MapView) dialog.findViewById(R.id.mapView);
                MapsInitializer.initialize(getActivity());

                mMapView = (MapView) dialog.findViewById(R.id.mapView);
                mMapView.onCreate(dialog.onSaveInstanceState());
                mMapView.onResume();// needed to get the map to display immediately
                googleMap = mMapView.getMap;
            }
        });
    }*/


    private void showTotal() {
        if(cart.noOfItems==1){
            b.CCTotalItems.setText(cart.noOfItems+" Item");
        }else{
        b.CCTotalItems.setText(cart.noOfItems+" Items");}
        b.Total.setText("₹"+cart.total);
    }

    private void ShowCartItems() {
        for(HashMap.Entry<String,CartItem>map : cart.cartItems.entrySet()){
            CartItemsBinding binding= CartItemsBinding.inflate(getLayoutInflater());

            binding.CItemName.setText(""+map.getKey());

            binding.CTotalPrice.setText("₹"+map.getValue().Cost());


            binding.CTotalItem.setText((int) (map.getValue().qty) + " x ₹" + (map.getValue().Cost()) / ((int) (map.getValue().qty)) + "/kg");
            b.CartList.addView(binding.getRoot());
            
        }
        }

    }
