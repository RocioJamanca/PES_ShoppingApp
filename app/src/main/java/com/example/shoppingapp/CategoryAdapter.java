package com.example.shoppingapp;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.graphics.drawable.IconCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.support.v4.content.res.TypedArrayUtils.getNamedResourceId;
import static android.support.v4.content.res.TypedArrayUtils.getString;


public class CategoryAdapter extends ArrayAdapter<String> {
    Resources res = getContext().getResources();

    public CategoryAdapter(Context context, ArrayList<String> categories){
        super (context,0,categories);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String ip=res.getString(R.string.ip);
        //Get the datat item for this position
        String category = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.items_category, parent, false);
        }
        //Lookup view for data population
        TextView name_category=(TextView) convertView.findViewById(R.id.name_category);
        // Populate the data into the template view using the data object
        name_category.setText(category);

        //Aqui consultamos la imagen al servidor
        ImageView imageView= (ImageView) convertView.findViewById(R.id.imageView2);
        Picasso.get().load("http://"+ip+":9000/Application/showCategoryImage?category="+category).into(imageView);

        // Return the completed view to render on screen
        return convertView;
    }
}
