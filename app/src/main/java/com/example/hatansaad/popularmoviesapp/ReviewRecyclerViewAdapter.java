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
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder> {
    ArrayList<Review> reviews;
    Context context;

    public ReviewRecyclerViewAdapter(Context context, ArrayList<Review> list) {
        this.context = context;
        this.reviews = list;
    }


    @NonNull
    @Override
    public ReviewRecyclerViewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.review_item, viewGroup, false);
        return new ReviewRecyclerViewAdapter.ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewRecyclerViewAdapter.ReviewViewHolder reviewViewHolder, int i) {
        Review review = reviews.get(i);
        String author = review.getAuthor();
        String content = review.getContent();

        reviewViewHolder.mAuthorText.setText(author);
        reviewViewHolder.mContentText.setText(content);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{

        TextView mAuthorText;
        TextView mContentText;

        ReviewViewHolder(View itemView) {
            super(itemView);
            mAuthorText = itemView.findViewById(R.id.review_author_tv);
            mContentText = itemView.findViewById(R.id.review_content_tv);
        }

    }
}
