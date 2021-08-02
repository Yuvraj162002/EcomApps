package com.example.android.admin_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.android.admin_app.controllers.AdapterCallbacksListener;
import com.example.android.admin_app.controllers.ProductsAdapter;
import com.example.android.admin_app.databinding.ActivityProductsBinding;
import com.example.android.admin_app.databinding.ProductInfoDialogBinding;
import com.example.android.admin_app.dialogs.AddProductDialog;
import com.example.android.admin_app.models.Cart;
import com.example.android.admin_app.models.Product;
import com.example.android.admin_app.tmp.ProductsHelper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 0;
    private static final int RESULT = 1001;
    ActivityProductsBinding b;
    ProductInfoDialogBinding binding;
    private ProductsAdapter adapter;
    List<Product> productList = new ArrayList<>();
    public static Context context;

    Cart cart;
    private ItemTouchHelper itemTouchHelper;
    private boolean isUpdated;
    public boolean isDragModeOn = false;
    AddProductDialog  dialog = new AddProductDialog(AddProductDialog.PRODUCT_ADD);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityProductsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        cart = new Cart();
        context=getApplicationContext();
        loadCartFromSharePreferences();
        setupAdapter();
        fabHandlers();

    }

    private void fabHandlers() {
        b.AddProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProducts();
            }
        });
    }

    private void AddProducts() {
        dialog.showDialog(ProductsActivity.this, new AddProductDialog.OnProductAddListener(){

            @Override
            public void onProductAddedOrEdit(Product product, Uri imageURL) {
                adapter.products.add(product);
                adapter.productsToShow.add(product);
                adapter.notifyItemInserted(adapter.products.size() - 1);
                Toast.makeText(ProductsActivity.this, "Product Added!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnCancelled() {
                Toast.makeText(ProductsActivity.this, "Cancelled!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            dialog.OnActivityResult(requestCode, resultCode, data);
        }
    }


    private void loadCartFromSharePreferences() {
        String cart = getPreferences(MODE_PRIVATE).getString("CART", null);

        if (cart == null) {
            this.cart = new Cart();
            return;
        }

        this.cart = new Gson().fromJson(cart, Cart.class);
        deleteProduct();

    }

    private void setupAdapter() {
        this.productList = ProductsHelper.getProducts();
        AdapterCallbacksListener listener = new AdapterCallbacksListener() {
            @Override
            public void onCartUpdated(int position) {
                isUpdated = true;
                adapter.notifyItemChanged(position);
            }
        };

        adapter = new ProductsAdapter(this
                , productList
                , cart
                , listener);

        b.list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        b.list.setAdapter(adapter);
        b.list.setLayoutManager(new LinearLayoutManager(this));
        dragDropHandle();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isUpdated) {
            Gson gson = new Gson();
            String json = gson.toJson(cart);
            getPreferences(MODE_PRIVATE).edit().putString("CART", json).apply();
            isUpdated = false;
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.product_edit:
                editProduct();
                return true;

            case R.id.product_remove:
                deleteProduct();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void deleteProduct() {
        new AlertDialog.Builder(this)
                .setTitle("Do you really want to delete this product?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Product productToBeDeleted = adapter.products.get(adapter.lastPosition);
                        adapter.products.remove(adapter.lastPosition);
                        adapter.productsToShow.remove(productToBeDeleted);
                        adapter.notifyItemRemoved(adapter.lastPosition);
                        // adapter.notifyItemChanged(adapter.lastPosition);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ProductsActivity.this, "Cancelled!", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void editProduct() {
        Product productToBeEdited = adapter.productsToShow.get(adapter.lastPosition);
        AddProductDialog productAdderDialog = new AddProductDialog(AddProductDialog.PRODUCT_EDIT);
        productAdderDialog.product = productToBeEdited;
        productAdderDialog.showDialog(ProductsActivity.this, new AddProductDialog.OnProductAddListener() {

            @Override
            public void onProductAddedOrEdit(Product product, Uri imageURL) {
                adapter.productsToShow.set(adapter.lastPosition, product);
                adapter.notifyItemChanged(adapter.lastPosition);
                Toast.makeText(ProductsActivity.this, "Product Edited!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnCancelled() {
                Toast.makeText(ProductsActivity.this, "Cancelled!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.appmenu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        //Listener to add Search Function of adapter:
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.alphaSort) {
            adapter.sortAlpha();
        } else if (item.getItemId() == R.id.dragDrop) {
            toggleDragDropButton(item);
            isDragModeOn = !isDragModeOn;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleDragDropButton(MenuItem item) {
        Drawable icon = item.getIcon();
        if (isDragModeOn) {
            icon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
            Toast.makeText(this, "Drag Off!!", Toast.LENGTH_SHORT).show();
        } else {
            icon.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            Toast.makeText(this, "Drag On!!", Toast.LENGTH_SHORT).show();
        }
        item.setIcon(icon);

        if (isDragModeOn) {
            itemTouchHelper.attachToRecyclerView(null);
        } else {
            itemTouchHelper.attachToRecyclerView(b.list);
        }
    }


    private void dragDropHandle() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                Collections.swap(adapter.productsToShow, fromPosition, toPosition);
                b.list.getAdapter().notifyItemMoved(fromPosition, toPosition);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
    }

}