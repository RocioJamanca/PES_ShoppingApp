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

public class BrandAdapter extends ArrayAdapter<String> {
    Resources res = getContext().getResources();

    public BrandAdapter(Context context, ArrayList<String> brands){super (context,0,brands);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String ip=res.getString(R.string.ip);
        //Get the datat item for this position
        String brand = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.items_brand, parent, false);
        }
        //Lookup view for data population
        TextView name_brand=(TextView) convertView.findViewById(R.id.name_brand);
        // Populate the data into the template view using the data object
        name_brand.setText(brand);

        ImageView imageView= (ImageView) convertView.findViewById(R.id.imageView_brand);
        Picasso.get().load("http://"+ip+":9000/Application/showBrandImage?brand="+brand).into(imageView);


        // Return the completed view to render on screen
        return convertView;
    }
}