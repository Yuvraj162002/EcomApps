package com.example.ecom.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.models.Cart;
import com.example.android.models.Product;
import com.example.android.models.ProductType;
import com.example.ecom.controllers.databinders.VBProductBinder;
import com.example.ecom.controllers.databinders.WBProductBinder;
import com.example.ecom.controllers.viewholders.VBProductViewHolder;
import com.example.ecom.controllers.viewholders.WBProductViewHolder;
import com.example.ecom.databinding.ItemVbProductBinding;
import com.example.ecom.databinding.ItemWbProductBinding;


import java.util.List;

public class ProductsAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Product> products;

    private WBProductBinder wbProductBinder;
    private VBProductBinder vbProductBinder;

    public ProductsAdapter(Context context, List<Product> products, Cart cart, AdapterCallbacksListener listener){
        this.context = context;
        this.products = products;

        wbProductBinder = new WBProductBinder(context, cart, listener);
        vbProductBinder = new VBProductBinder(context, cart, listener);
    }

    @Override
    public int getItemViewType(int position) {
        return products.get(position).type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ProductType.TYPE_WB){
            ItemWbProductBinding binding = ItemWbProductBinding.inflate(
                    LayoutInflater.from(context)
                    , parent
                    , false
            );
            return new WBProductViewHolder(binding);
        } else {
            ItemVbProductBinding binding = ItemVbProductBinding.inflate(
                    LayoutInflater.from(context)
                    , parent
                    , false
            );
            return new VBProductViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product product = products.get(position);

        if(holder instanceof WBProductViewHolder){
            wbProductBinder.bind(((WBProductViewHolder) holder).b, product,position);
        } else {
            vbProductBinder.bind(((VBProductViewHolder) holder).b, product);
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

}
