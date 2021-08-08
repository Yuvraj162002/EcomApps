package com.example.android.admin_app.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.android.admin_app.controllers.databinders.VBProductBinder;
import com.example.android.admin_app.controllers.databinders.WBProductBinder;
import com.example.android.admin_app.controllers.viewholders.VBProductViewHolder;
import com.example.android.admin_app.controllers.viewholders.WBProductViewHolder;
import com.example.android.admin_app.databinding.ItemVbProductBinding;
import com.example.android.admin_app.databinding.ItemWbProductBinding;
import com.example.android.admin_app.models.Cart;
import com.example.android.admin_app.models.Product;
import com.example.android.admin_app.models.ProductType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductsAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    public List<Product> products;


    public List<Product> productsToShow;
    private Cart cart;
    private AdapterCallbacksListener listener;

    private WBProductBinder wbProductBinder;
    private VBProductBinder vbProductBinder;
    public int lastPosition;

    public ProductsAdapter(Context context, List<Product> products, Cart cart, AdapterCallbacksListener listener){
        this.cart=cart;
        this.context = context;
        this.products = products;
        this.products=new ArrayList<>(products);
        this.listener=listener;
        productsToShow=products;

        wbProductBinder = new WBProductBinder(context, cart, listener);
        vbProductBinder = new VBProductBinder(context, cart, listener);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ProductType.TYPE_WB){
            ItemWbProductBinding b = ItemWbProductBinding.inflate(
                    LayoutInflater.from(context)
                    , parent
                    , false
            );
            return new WBProductViewHolder(b);
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

        if(holder instanceof WBProductViewHolder){
            wbProductBinder.bind(((WBProductViewHolder) holder).b, productsToShow.get(position),position);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    lastPosition=holder.getAdapterPosition();
                    return false;
                }
            });
            return;
        }else if(holder instanceof VBProductViewHolder) {
            vbProductBinder.bind(((VBProductViewHolder) holder).b, productsToShow.get(position), position);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    lastPosition = holder.getAdapterPosition();
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productsToShow.size();
    }


    @Override
    public int getItemViewType(int position) {
        return productsToShow.get(position).type;
    }

    public void filter(String query){
        /*products=searchedProducts;
        notifyDataSetChanged();*/

    if(query.trim().isEmpty()){

        productsToShow=products;
        //((MainActivity)context).findViewById(R.id.NoItems).setVisibility(View.GONE);
        notifyDataSetChanged();
        return;
    }
    query=query.toLowerCase();

    //Items according to the query searched:
        List<Product> searchedProducts=new ArrayList<>();
        for(Product product:products){
            if(product.name.toLowerCase().contains(query)){
                searchedProducts.add(product);
            }
            if(searchedProducts.size()==0){
               // ((MainActivity)context).findViewById(R.id.NoItems).setVisibility(View.VISIBLE);
            }
            else {
               // ((MainActivity)context).findViewById(R.id.NoItems).setVisibility(View.GONE);

            }
        }
        productsToShow=searchedProducts;
        notifyDataSetChanged();
    }
    public void sortAlpha() {
        //Sort List of Items according to alphabetical order of labels given:
        Collections.sort(productsToShow, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.name.compareTo(o2.name);
            }
        });
        notifyDataSetChanged();
    }


}
