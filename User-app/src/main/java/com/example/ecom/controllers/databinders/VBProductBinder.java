package com.example.ecom.controllers.databinders;

import android.content.Context;
import android.view.View;

import com.example.android.models.Cart;
import com.example.android.models.Product;
import com.example.android.models.Variants;
import com.example.ecom.MainActivity;
import com.example.ecom.controllers.AdapterCallbacksListener;
import com.example.ecom.databinding.ChipVarientBinding;
import com.example.ecom.databinding.ItemVbProductBinding;
import com.example.ecom.dialogs.VariantsQtyPickerDialog;

import java.util.HashMap;


public class VBProductBinder {
    private Context context;
    private Cart cart;
    private AdapterCallbacksListener listener;
    private HashMap<String ,Boolean> saveVariantGrpVisibility=new HashMap<>();

    public VBProductBinder(Context context, Cart cart, AdapterCallbacksListener listener){
        this.context = context;
        this.cart = cart;
        this.listener = listener;
    }

    public void bind(ItemVbProductBinding b, Product product,int position){

        //bind data
        b.productVariants.setText(product.variants.size() + " variants");
        b.imageViewVB.setImageResource(product.imageURL);
        b.dropdownMenu.setVisibility(View.VISIBLE);
        b.dropdownMenu.setRotation(0);
        b.variants.setVisibility(View.GONE);

        if(saveVariantGrpVisibility.containsKey(product.name)){
            if(saveVariantGrpVisibility.get(product.name)){
                b.dropdownMenu.setVisibility(View.VISIBLE);
                b.dropdownMenu.setRotation(180);
                b.variants.setVisibility(View.VISIBLE);
            }
        }

        //show and gone variant group
        showAndHideVariantGrp(b,product);
        checkVbProductInCart(product, b);
        inflateVariants(product, b);
        editQty(product, b, position);

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

    public void checkVbProductInCart(Product product, ItemVbProductBinding b) {
        //total qty from cart
        int qty = 0;

        for (Variants variant : product.variants) {
            //check qty present in cart
            if (cart.cartItems.containsKey(product.name + " " + variant.name)) {
                qty += cart.cartItems.get(product.name + " " + variant.name).qty;
            }
        }
        //update views
        if (qty > 0) {
            b.nonZeroQtyGroup.setVisibility(View.VISIBLE);
            b.quantity.setText(qty + "");
        } else {
            b.nonZeroQtyGroup.setVisibility(View.GONE);
            b.quantity.setText(0 + "");
        }
    }

    private void inflateVariants(Product product, ItemVbProductBinding b) {
        b.variants.removeAllViews();

        //for variants more than 1
        //check variant size
        if (product.variants.size() > 1) {
            b.productName.setText(product.name);
            for (Variants variant : product.variants) {
                ChipVarientBinding binding = ChipVarientBinding.inflate(((MainActivity) context).getLayoutInflater());
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

    private void editQty(Product product, ItemVbProductBinding b, int position) {
        b.incBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for variants more than 1
                //check variant size
                if (product.variants.size() > 1)
                    showDialog(product, position);

                    //for single variant
                else {
                    int qty = Integer.parseInt(b.quantity.getText().toString()) + 1;
                    cart.add(product, product.variants.get(0), qty);
                    listener.onCartUpdated(position);
                }

            }
        });

        b.decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for variants more than 1
                //check variant size
                if (product.variants.size() > 1)
                    showDialog(product, position);

                    //for single variant
                else {
                    int qty = Integer.parseInt(b.productName.getText().toString()) - 1;
                    cart.add(product, product.variants.get(0), qty);
                    listener.onCartUpdated(position);
                }
            }
        });
    }
    private void showDialog(Product product, int position) {

        new VariantsQtyPickerDialog(context, product, listener, cart, position).show();
    }

}
