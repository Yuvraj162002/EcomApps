package com.example.ecom.FireBaseHelpers;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class ProductsHelper {
    public FirebaseFirestore db;
    public Dialog dialog;
    public ConnectivityManager connectivityManager;
     public Context context;


    public ProductsHelper(Context context) {
        this.context=context;
        db = FirebaseFirestore.getInstance();
    }

    public  void ShowLoadingDialog(Context context){

        if(dialog != null && dialog.isShowing())
            return;

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle("Loading and Fetching Product.....");

        if(dialog.getWindow() == null)
            return;

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public void hideLoadingDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public boolean isOffline() {
        connectivityManager  = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo dataNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return !(wifiNetworkInfo.isConnected() || dataNetworkInfo.isConnected());
    }
}
