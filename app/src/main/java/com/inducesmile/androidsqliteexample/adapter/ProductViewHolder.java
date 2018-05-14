package com.inducesmile.androidsqliteexample.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.inducesmile.androidsqliteexample.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public ImageView productImage;
    public TextView name;
    public TextView quantity;
    public TextView tag;
    public TextView price;
    public ImageView deleteProduct;
    public ImageView editProduct;
    public CheckBox checkBox;

    public ProductViewHolder(View itemView) {
        super(itemView);
        //productImage = (ImageView)itemView.findViewById(R.id.person_photo);
        name = (TextView)itemView.findViewById(R.id.product_name);
        quantity = (TextView)itemView.findViewById(R.id.product_quantity);
        tag = (TextView)itemView.findViewById(R.id.product_tag);
        price = (TextView)itemView.findViewById(R.id.product_price);
        deleteProduct = (ImageView)itemView.findViewById(R.id.delete_product);
        editProduct = (ImageView)itemView.findViewById(R.id.edit_product);
        checkBox = (CheckBox)itemView.findViewById(R.id.checkBox);
    }
}
