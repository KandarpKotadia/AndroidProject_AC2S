package com.example.json_5;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> categories;
    private final ArrayList<String> images;

    public MyListAdapter(Activity context, ArrayList<String> c_names, ArrayList<String> c_urls) {
        super(context, R.layout.list_design, c_names);

        this.context = context;
        this.categories = c_names;
        this.images = c_urls;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_design, null, true);

        TextView category_name = (TextView) rowView.findViewById(R.id.tv_category);
        ImageView category_image = (ImageView) rowView.findViewById(R.id.iv_image);

        category_name.setText( categories.get(position) );
        //new DownloadImageTask(category_image).execute(images.get(position));
        Picasso.get().load(images.get(position)).into(category_image);


        return rowView;
    }
}
