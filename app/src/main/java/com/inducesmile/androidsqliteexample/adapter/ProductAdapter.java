package com.inducesmile.androidsqliteexample.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.database.DatabaseUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.inducesmile.androidsqliteexample.Product;
import com.inducesmile.androidsqliteexample.R;
import com.inducesmile.androidsqliteexample.database.SqliteDatabase;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> implements Filterable{

    private Context context;
    List<Product> listProducts, filterList; //private?
    CustomFilter filter;

    private SqliteDatabase mDatabase;

    public ProductAdapter(Context context, List<Product> listProducts) {
        this.context = context;
        this.listProducts = listProducts;
        this.filterList=listProducts;
        mDatabase = new SqliteDatabase(context);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_layout, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final Product singleProduct = listProducts.get(position);

        //holder.productImage
        holder.name.setText(singleProduct.getName());
        holder.quantity.setText(String.valueOf(singleProduct.getQuantity()));
        holder.tag.setText(singleProduct.getTag());
        holder.price.setText(String.valueOf(singleProduct.getPrice()));

        holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(singleProduct);
            }
        });

        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                mDatabase.deleteProduct(singleProduct.getId());

                //refresh the activity page.
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String checked;
                if(isChecked) {
                     checked = "1";
                    Log.v("DEBUG CHECKBOX", checked);
                    int black = R.color.colorBlack;
                    //((CardView)holder.itemView).setCardBackgroundColor("000000");
                } else {
                     checked = "0";
                    Log.v("DEBUG CHECKBOX", checked);
                    int white = R.color.colorPrimary;
                    ((CardView)holder.itemView).setCardBackgroundColor(white);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }


    private void editTaskDialog(final Product product){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_product_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);
        final EditText tagField = (EditText)subView.findViewById(R.id.enter_tag);
        final EditText priceField = (EditText)subView.findViewById(R.id.enter_price);

        if(product != null){
            nameField.setText(product.getName());
            quantityField.setText(String.valueOf(product.getQuantity()));
            tagField.setText(product.getTag());
            priceField.setText(String.valueOf(product.getPrice()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit product");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT PRODUCT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final int quantity = Integer.parseInt(quantityField.getText().toString());
                final String tag = tagField.getText().toString();
                final int price = Integer.parseInt(priceField.getText().toString());

                if(TextUtils.isEmpty(name) || quantity <= 0){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    //mDatabase.updateProduct(new Product(product.getId(), name, quantity, tag));
                    mDatabase.updateProduct(new Product(product.getId(), name, quantity, tag, price));
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter(filterList,this);
        }

        return filter;
    }
}
