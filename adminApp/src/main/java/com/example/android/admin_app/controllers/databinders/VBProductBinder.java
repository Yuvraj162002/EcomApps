package com.example.android.admin_app.controllers.databinders;

import android.content.Context;
import android.view.ContextMenu;
import android.view.View;


import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.android.admin_app.MainActivity;
import com.example.android.admin_app.ProductsActivity;
import com.example.android.admin_app.R;
import com.example.android.admin_app.controllers.AdapterCallbacksListener;
import com.example.android.admin_app.controllers.ProductsAdapter;
import com.example.android.admin_app.databinding.ChipVariantBinding;
import com.example.android.admin_app.databinding.ItemVbProductBinding;
import com.example.android.admin_app.models.Cart;
import com.example.android.admin_app.models.Product;
import com.example.android.admin_app.models.Variants;

import java.util.HashMap;


public class VBProductBinder {
    private Context context;
    private Cart cart;
    ProductsAdapter adapter;
    private final AdapterCallbacksListener listener;
    private final HashMap<String ,Boolean> saveVariantGrpVisibility=new HashMap<>();

    public VBProductBinder(Context context, Cart cart, AdapterCallbacksListener listener){
        this.context = context;
        this.cart = cart;
        this.listener = listener;
    }

    public void bind(ItemVbProductBinding b, Product product, int position){

        //bind data
        b.productVariants.setText(product.variants.size() + " variants");
        b.imageViewVB.setImageResource(product.imageURL);
        b.dropdownMenu.setVisibility(View.VISIBLE);
        b.dropdownMenu.setRotation(0);
        b.variants.setVisibility(View.GONE);
        showContextMenu(b.getRoot());


        if(saveVariantGrpVisibility.containsKey(product.name)){
            if(saveVariantGrpVisibility.get(product.name)){
                b.dropdownMenu.setVisibility(View.VISIBLE);
                b.dropdownMenu.setRotation(180);
                b.variants.setVisibility(View.VISIBLE);
            }
        }

        //show and gone variant group
        showAndHideVariantGrp(b,product);
        inflateVariants(product, b);

    }

    private void showAndHideVariantGrp(ItemVbProductBinding b, Product product) {
        b.dropdownMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check visibility
                if (b.variants.getVisibility() == View.GONE) {
                    b.variants.setVisibility(View.VISIBLE);
                    b.dropdownMenu.setRotation(180);

                    saveVariantGrpVisibility.put(product.name,true);
                } else {
                    b.variants.setVisibility(View.GONE);
                    b.dropdownMenu.setRotation(0);
                    saveVariantGrpVisibility.put(product.name,false);
                }
            }
        });
    }


    private void inflateVariants(Product product, ItemVbProductBinding b) {
        b.variants.removeAllViews();

        //for variants more than 1
        //check variant size
        if (product.variants.size() > 1) {
            b.productName.setText(product.name);
            for (Variants variant : product.variants) {
                ChipVariantBinding binding = ChipVariantBinding.inflate(((ProductsActivity) context).getLayoutInflater());
                binding.getRoot().setText(variant.name + " - Rs." + variant.price);
                b.variants.addView(binding.getRoot());
            }
            return;
        }

        //for single variant
        b.dropdownMenu.setVisibility(View.GONE);
        b.productVariants.setText("Rs." + product.variants.get(0).price);
        b.productName.setText(product.name + " " + product.variants.get(0).name);
    }
    public void showContextMenu(ConstraintLayout root){
        root.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                if(!(context instanceof ProductsActivity)){
                    return;
                }
                ProductsActivity activity=((ProductsActivity)context);
                if(!activity.isDragModeOn) {
                    activity.getMenuInflater().inflate(R.menu.contextmenu, menu);
                }
            }
        });
    }


}
