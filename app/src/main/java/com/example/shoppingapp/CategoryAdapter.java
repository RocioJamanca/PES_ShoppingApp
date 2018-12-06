package com.example.shoppingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<String> {
    public CategoryAdapter(Context context, ArrayList<String> categories){super (context,0,categories);}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

        // Return the completed view to render on screen
        return convertView;
    }
}
