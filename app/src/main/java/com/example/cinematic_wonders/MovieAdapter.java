package com.example.cinematic_wonders;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie> {
    Context mContext;
    ArrayList<Movie> movieArrayList;

    public MovieAdapter(Context context, int resource, ArrayList<Movie> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View listItemView = convertView ;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.movie_grid_layout,parent,false);
        }
        Movie currentMovie = getItem(position);
        ImageView Img = listItemView.findViewById(R.id.img);
        TextView name = listItemView.findViewById(R.id.name);
        TextView type = listItemView.findViewById(R.id.type);

        Picasso.get().load(currentMovie.Image).into(Img);
        name.setText(currentMovie.Name);
        type.setText(currentMovie.Type);

        return listItemView;

    }
}
