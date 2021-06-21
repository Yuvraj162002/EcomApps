package com.example.ecom.dialogs;

import android.content.Context;

import com.example.android.models.Cart;
import com.example.android.models.Product;


public class VariantsQtyPickerDialog {

    private Context context;

    public VariantsQtyPickerDialog(Context context, Cart cart){
        this.context = context;
    }

    public void show(Product product, VariantsQtyPickerCompleteListener listener){

    }

    public interface VariantsQtyPickerCompleteListener {
        void onCompleted();
    }
}
