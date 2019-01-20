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

import org.w3c.dom.Text;

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
        TextView category_product=(TextView) convertView.findViewById(R.id.category_product);
        TextView model_product=(TextView) convertView.findViewById(R.id.model_product);
        TextView brand_product=(TextView) convertView.findViewById(R.id.brand_product);
        TextView price_product=(TextView) convertView.findViewById(R.id.price_product);
        if(product.quantity!=0){
            TextView quantity_product=(TextView) convertView.findViewById(R.id.quantity_product);
            TextView quantity_textView=(TextView) convertView.findViewById(R.id.quantity_textView);
            quantity_product.setText(product.quantity);
            quantity_product.setVisibility(View.VISIBLE);
            quantity_textView.setVisibility(View.VISIBLE);
        }


        // Populate the data into the template view using the data object
        category_product.setText(product.category);
        model_product.setText(product.model);
        brand_product.setText(product.brand);
        String price = new Double(product.price).toString();
        price_product.setText(price);



        ImageView imageView= (ImageView) convertView.findViewById(R.id.imageView_product);
        Picasso.get().load("http://"+ip+":9000/Application/showProductImage?model="+product.model).into(imageView);


        // Return the completed view to render on screen
        return convertView;
    }
}