package com.example.hatansaad.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<Movie> movies;
    Context context;

    public RecyclerViewAdapter(Context context, ArrayList<Movie> list) {
        this.context = context;
        this.movies = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.gridlist_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //Picasso.with(context).load(movies.get(i).getPoster()).into(viewHolder.poster);
        Picasso.with(context).load(movies.get(i).buildPosterPath()).into(viewHolder.poster);
        //Log.i("msg", "link is " + movies.get(i).getPoster());
    }

    @Override
    public int getItemCount() {
        //System.out.println("SIZE IS"+movies.size());
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;

        public ViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("SelectedMovie",movies.get(position));
                    context.startActivity(intent);
                }
            });
        }
    }
}
