package com.example.hatansaad.popularmoviesapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class TrailerAsyncTaskLoader extends AsyncTaskLoader<ArrayList<Trailer>> {

    String url;

    public TrailerAsyncTaskLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Trailer> loadInBackground() {
        Context context = this.getContext();
        String response = Network.fetchResponse(url);
        ArrayList<Trailer> list =Json.trailersForMovie(response,context);
        //System.out.println("Trailer list size "+list.size());
        return list;
    }
}
