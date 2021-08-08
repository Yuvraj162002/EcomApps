package com.example.ecom.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecom.Activities.MainActivity;
import com.example.ecom.FireBaseHelpers.ProductsHelper;
import com.example.ecom.R;
import com.example.ecom.controllers.databinders.VBProductBinder;
import com.example.ecom.controllers.databinders.WBProductBinder;
import com.example.ecom.controllers.viewholders.VBProductViewHolder;
import com.example.ecom.controllers.viewholders.WBProductViewHolder;
import com.example.ecom.databinding.ItemVbProductBinding;
import com.example.ecom.databinding.ItemWbProductBinding;
import com.example.ecom.models.Cart;
import com.example.ecom.models.Product;
import com.example.ecom.models.ProductType;


import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Product> products;


    public List<Product> productsToShow;
    private Cart cart;
    private AdapterCallbacksListener listener;

    private WBProductBinder wbProductBinder;
    private VBProductBinder vbProductBinder;

    public ProductsAdapter(Context context, List<Product> products, Cart cart, AdapterCallbacksListener listener){
        this.cart=cart;
        this.context = context;
        this.products = products;
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
            return;
        }
            vbProductBinder.bind(((VBProductViewHolder)holder).b, productsToShow.get(position),position);

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
        ((MainActivity)context).findViewById(R.id.NoItems).setVisibility(View.GONE);
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
                ((MainActivity)context).findViewById(R.id.NoItems).setVisibility(View.VISIBLE);
            }
            else {
                ((MainActivity)context).findViewById(R.id.NoItems).setVisibility(View.GONE);

            }
        }
        productsToShow=searchedProducts;
        notifyDataSetChanged();
    }
}
