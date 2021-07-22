package com.example.android.admin_app.controllers.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.admin_app.databinding.ItemVbProductBinding;


public class VBProductViewHolder extends RecyclerView.ViewHolder {

    public ItemVbProductBinding b;

    public VBProductViewHolder(@NonNull ItemVbProductBinding b) {
        super(b.getRoot());
        this.b = b;
    }

}
