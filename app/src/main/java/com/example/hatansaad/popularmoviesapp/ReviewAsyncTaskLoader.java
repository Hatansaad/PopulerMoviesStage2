package com.example.hatansaad.popularmoviesapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class ReviewAsyncTaskLoader extends AsyncTaskLoader<ArrayList<Review>> {

    String url;

    public ReviewAsyncTaskLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Review> loadInBackground() {
        Context context = this.getContext();
        String response = Network.fetchResponse(url);
        ArrayList<Review> list =Json.reviewsForMovie(response,context);
        //System.out.println("Review list size "+list.size());
        return list;
    }
}
