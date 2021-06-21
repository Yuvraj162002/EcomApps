package com.example.ecom.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.android.models.Cart;
import com.example.android.models.Product;
import com.example.ecom.MainActivity;
import com.example.ecom.R;
import com.example.ecom.controllers.AdapterCallbacksListener;
import com.example.ecom.databinding.DialogWeightPickerBinding;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class WeightPickerDialog {
    private LayoutInflater inflater;

    private Context context;

    private Cart cart;

    WeightPickerCompleteListener listener;

    private AlertDialog dialog;

    private Product product;

    private int minValueKg;

    private int minValueG;

    private DialogWeightPickerBinding binding;

    public WeightPickerDialog(Context context, Cart cart){
        this.context = context;
        this.cart = cart;
        inflater=((MainActivity)context).getLayoutInflater();

    }


    public void show(Product product, WeightPickerCompleteListener listener) {

         dialog=new MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme)
                .setView(binding.getRoot())
                .show();

        binding.ProductTitle.setText(product.name);

        minQty();

        buttonEventHandlers();

    }

    private void minQty() {
        String[] minValues=String.valueOf(product.minQuantity).split("\\.");

        minValueKg=Integer.parseInt(minValues[0]);
        eventNumberPickerKg();

        String minQtyGram="0." + minValues[1];

        minValueG=(int)(Float.parseFloat(minQtyGram)*1000);
        eventNumberPickerG();
    }

    private void eventNumberPickerG() {
    int numberOfValues = 20- (minValueG/50);

    int pickerRange = minValueG;
    String[] ValueToDisplay= new String[numberOfValues];

    ValueToDisplay[0]=minValueG + "g";
    for(int i=1; i<numberOfValues;i++){

        ValueToDisplay[i] = (pickerRange + 50) + "gm";
        pickerRange += 50;
    }

    binding.GPicker.setMinValue(0);
    binding.GPicker.setMaxValue(ValueToDisplay.length-1);
    binding.GPicker.setDisplayedValues(ValueToDisplay);

    if(cart.cartItems.containsKey(product.name)){
        String[] minValue= String.valueOf(cart.cartItems.get(product.name).qty).split("\\.");
        String minQtyG ="0."+ minValue[1];

        int gram=(int) (Float.parseFloat(minQtyG)*1000);
        binding.GPicker.setValue((gram-minValueG)/50);
    }
    }

    private void eventNumberPickerKg() {
    int numberOfValues= 11 - minValueKg;

    int pickerRange=minValueKg;
    String[] ValueToDisplay = new String[numberOfValues];

    ValueToDisplay[0]= minValueKg +"Kg";
    for(int i=1; i<numberOfValues; i++){

        ValueToDisplay[i] =(++pickerRange) + "Kg";
    }
    binding.KgPicker.setMinValue(0);
    binding.KgPicker.setMaxValue(ValueToDisplay.length-1);
    binding.KgPicker.setDisplayedValues(ValueToDisplay);

    if(cart.cartItems.containsKey(product.name)){
       String[] minValues= String.valueOf(cart.cartItems.get(product.name).qty).split("\\.");
       binding.KgPicker.setValue(Integer.parseInt(minValues[0])-minValueKg);
    }
    }

    private void buttonEventHandlers() {
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveToCart(product, (WeightPickerCompleteListener) listener);
            }
        });

        binding.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.remove(product);

                listener.onCompleted();
                dialog.dismiss();
            }

        });
    }

    private void SaveToCart(Product product,WeightPickerCompleteListener listener) {

        Float quantity=(minValueKg +binding.KgPicker.getValue())+((((minValueG/50f)+binding.GPicker.getValue())*50)/1000f);

        if(quantity<product.minQuantity){
            Toast.makeText(context,"Minimum" +product.minQuantity +"Kg needs to be selected",Toast.LENGTH_SHORT).show();
            return;
        }
        cart.add(product,quantity);
        listener.onCompleted();
        dialog.dismiss();

    }

    public interface WeightPickerCompleteListener{
        void onCompleted();
    }
}
