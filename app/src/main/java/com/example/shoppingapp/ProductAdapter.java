package com.example.shoppingapp;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter  extends ArrayAdapter<Product> {
    Resources res = getContext().getResources();

    public ProductAdapter(Context context, ArrayList<Product> products){super (context,0,products);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String ip=res.getString(R.string.ip);
        //Get the datat item for this position
        Product product = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_products, parent, false);
        }
        //Lookup view for data population
        TextView name_product=(TextView) convertView.findViewById(R.id.name_product);
        // Populate the data into the template view using the data object
        name_product.setText(product.model);

        //ImageView imageView= (ImageView) convertView.findViewById(R.id.imageView_brand);
        //Picasso.get().load("http://"+ip+":9000/Application/showBrandImage?brand="+brand).into(imageView);


        // Return the completed view to render on screen
        return convertView;
    }
}