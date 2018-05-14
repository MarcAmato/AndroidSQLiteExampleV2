package com.inducesmile.androidsqliteexample.adapter;

import android.widget.Filter;

import com.inducesmile.androidsqliteexample.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kev
 */
public class CustomFilter extends Filter{

    ProductAdapter adapter;
   // ArrayList<Player> filterList;
   List<Product> filterList;


    public CustomFilter(List<Product> filterList,ProductAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            List<Product> filteredProducts=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getName().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredProducts.add(filterList.get(i));
                }
            }

            results.count=filteredProducts.size();
            results.values=filteredProducts;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }


        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.listProducts= (List<Product>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
