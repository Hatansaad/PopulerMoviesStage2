package com.example.hatansaad.popularmoviesapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class TrailerRecyclerViewAdapter extends RecyclerView.Adapter<TrailerRecyclerViewAdapter.TrailerViewHolder> {
    ArrayList<Trailer> trailers;
    Context context;
    private static final String URI_APP = "vnd.youtube:";
    private static final String URI_WEB = "http://www.youtube.com/watch?v=";

    public TrailerRecyclerViewAdapter(Context context, ArrayList<Trailer> list) {
        this.context = context;
        this.trailers = list;
    }


    @NonNull
    @Override
    public TrailerRecyclerViewAdapter.TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.trailer_item, viewGroup, false);
        return new TrailerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerRecyclerViewAdapter.TrailerViewHolder viewHolder, int i) {
        Trailer trailer = trailers.get(i);
        String name = trailer.getName();
        //System.out.println("name is "+name);
        viewHolder.mtrailerText.setText(name);
    }

    @Override
    public int getItemCount() {
        //System.out.println("size is "+trailers.size());
        return trailers.size();
    }



    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mtrailerText;

        TrailerViewHolder(View itemView) {
            super(itemView);
            mtrailerText = itemView.findViewById(R.id.trailer_text);
            //mtrailerText.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Trailer trailer = trailers.get(position);
            String videoKey = trailer.getVideoKey();
            Intent youTubeApp = new Intent(Intent.ACTION_VIEW, Uri.parse(URI_APP + videoKey));
            Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(URI_WEB + videoKey));
            try {
                context.startActivity(youTubeApp);
            } catch (ActivityNotFoundException ex) {
                context.startActivity(browser);
            }
        }
    }
}
