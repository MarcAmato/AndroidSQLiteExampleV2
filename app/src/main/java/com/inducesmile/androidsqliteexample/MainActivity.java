package com.inducesmile.androidsqliteexample;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.inducesmile.androidsqliteexample.adapter.ProductAdapter;
import com.inducesmile.androidsqliteexample.database.SqliteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    SearchView sv;
    private SqliteDatabase mDatabase;

    ProductAdapter adapter; // !!! TEST CODE

    RecyclerView productView;
    List<Product> allProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LinearLayoutManager fLayout = (LinearLayoutManager) findViewById(R.id.activity_to_do);
        sv= (SearchView) findViewById(R.id.mSearch);

        // !!! TEST CODE
        adapter =  new ProductAdapter(this, allProducts);

        productView = (RecyclerView) findViewById(R.id.product_list); // Moved type above constructor
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        productView.setLayoutManager(linearLayoutManager);
        productView.setHasFixedSize(true);

        // !!! TEST CODE
        productView.setAdapter(adapter);

        mDatabase = new SqliteDatabase(this);
        allProducts = mDatabase.listProducts(); // Moved type above constructor

        if(allProducts.size() > 0){
            productView.setVisibility(View.VISIBLE);
            final ProductAdapter mAdapter = new ProductAdapter(this, allProducts);
            productView.setAdapter(mAdapter);
            //SEARCH
            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    //FILTER AS YOU TYPE
                    mAdapter.getFilter().filter(query);
                    return false;
                }
            });


        }else {
            productView.setVisibility(View.GONE);
            Toast.makeText(this, "There is no product in the database. Start adding now", Toast.LENGTH_LONG).show();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add new quick task
                addTaskDialog();
            }
        });

        // !!! Only for debugging in console (see database contents)
        Cursor cursor = mDatabase.getAllData();
        Log.v("DEBUG DATABASE", DatabaseUtils.dumpCursorToString(cursor));
    }






    private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_product_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);
        final EditText tagField = (EditText)subView.findViewById(R.id.enter_tag);
        final EditText priceField = (EditText)subView.findViewById(R.id.enter_price);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new product");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ADD PRODUCT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final int quantity = Integer.parseInt(quantityField.getText().toString());
                final String tag = tagField.getText().toString();
                final int price = Integer.parseInt(priceField.getText().toString()); // !!! Will this typecast work?

                if(TextUtils.isEmpty(name) || quantity <= 0 || TextUtils.isEmpty(tag) || price <= 0){
                    Toast.makeText(MainActivity.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    Product newProduct = new Product(name, quantity, tag, price);
                    mDatabase.addProduct(newProduct);

                    //refresh the activity
                    finish();
                    startActivity(getIntent());

                    // !!! Possible alternative to avoid full screen refresh. (From ToDo)
//                    dialog.dismiss();
//                    showItemList();

                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }

    // !!! Test ---- EVERTHING BELOW HERE !!!
//    public void showItemList() {
//        // List<Product> productList = mDatabase.listProducts();
//        ArrayList<String> productList = mDatabase.listProducts2();
//        if(adapter==null) {
//            adapter = new ProductAdapter(this, allProducts);
//            productView.setAdapter(adapter);
//        } else {
//            allProducts.clear();
//            adapter.notifyDataSetChanged();
//            adapter = new ProductAdapter(this, allProducts);
//            productView.setAdapter(adapter);
//            mDatabase.listProducts();
//        }
//    }
}
