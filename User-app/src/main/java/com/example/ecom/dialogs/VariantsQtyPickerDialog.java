package com.example.ecom.dialogs;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.example.ecom.MainActivity;
import com.example.ecom.R;
import com.example.ecom.controllers.AdapterCallbacksListener;
import com.example.ecom.databinding.DialogVariantPickerBinding;
import com.example.ecom.databinding.DialogWeightPickerBinding;
import com.example.ecom.databinding.ItemVariantBinding;
import com.example.ecom.models.Cart;
import com.example.ecom.models.Product;
import com.example.ecom.models.Variants;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.HashMap;


public class VariantsQtyPickerDialog {

    private Context context;
    private Product product;
    private AdapterCallbacksListener listener;
    private Cart cart;
    private int position;
    private DialogVariantPickerBinding b;
    private AlertDialog dialog;
    private HashMap<String, Integer> saveVariantsQty = new HashMap<>();

    public VariantsQtyPickerDialog(Context context, Product product, AdapterCallbacksListener listener, Cart cart, int position) {

        this.context = context;
        this.product = product;
        this.listener = listener;
        this.cart = cart;
        this.position = position;
    }


    public void show(){
        b = DialogVariantPickerBinding.inflate(((MainActivity) context).getLayoutInflater());

        dialog = new MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme)
                .setCancelable(false)
                .setView(b.getRoot())
                .show();

        b.ProductTitle.setText(product.name);

        inflateVariants();

        saveVariants();

        removeAllVariants();
    }

    private void inflateVariants() {
        for (Variants variant : product.variants) {

            ItemVariantBinding binding = ItemVariantBinding.inflate(((MainActivity) context).getLayoutInflater());
            binding.variantName.setText("Rs." + variant.price + " - " + variant.name);
            b.variantList.addView(binding.getRoot());

            //prefill selected variants
            prefillSelectedVariant(binding, variant.name);

            //add quantity
            addQuantityForEachVariant(binding, variant.name);

            //dec quantity
            decQuantityForEachVariant(binding, variant.name);
        }
    }

    private void prefillSelectedVariant(ItemVariantBinding binding, String variantName) {
        //check cart variant is prent or not
        if (cart.cartItems.containsKey(product.name + " " + variantName)) {
            //Save qty in saveVariantsQty
            saveVariantsQty.put(variantName, (int) cart.cartItems.get(product.name + " " + variantName).qty);

            binding.nonZeroQtyGroup.setVisibility(View.VISIBLE);
            binding.qty.setText(saveVariantsQty.get(variantName) + "");
        }
    }


    private void addQuantityForEachVariant(ItemVariantBinding binding, String variantName) {
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save qty of variants
                //check variant present or not
                if (saveVariantsQty.containsKey(variantName)) {
                    saveVariantsQty.put(variantName, saveVariantsQty.get(variantName) + 1);

                } else {
                    saveVariantsQty.put(variantName, 1);
                }
                //update views
                binding.qty.setText(saveVariantsQty.get(variantName) + "");
                binding.nonZeroQtyGroup.setVisibility(View.VISIBLE);
            }
        });
    }


    private void decQuantityForEachVariant(ItemVariantBinding binding, String variantName) {
        binding.decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save qty of variants
                if (saveVariantsQty.containsKey(variantName)) {
                    saveVariantsQty.put(variantName, saveVariantsQty.get(variantName) - 1);
                }
                //check variant quantity size
                if (saveVariantsQty.get(variantName) == 0) {
                    binding.nonZeroQtyGroup.setVisibility(View.GONE);
                }

                binding.qty.setText(saveVariantsQty.get(variantName) + "");
            }
        });
    }

    private void saveVariants() {
        b.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!saveVariantsQty.isEmpty()) {
                    for (Variants variant : product.variants) {
                        //check variant present in saveVariantsQty
                        if (saveVariantsQty.containsKey(variant.name)) {
                            cart.add(product, variant, saveVariantsQty.get(variant.name));
                        }
                    }
                    //update views
                    listener.onCartUpdated(position);
                }
                dialog.dismiss();
            }
        });
    }

    private void removeAllVariants() {
        b.removeAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!saveVariantsQty.isEmpty()) {
                    cart.removeAllVBP(product);
                    //update views
                    listener.onCartUpdated(position);
                }
                dialog.dismiss();
            }
        });
    }
}
