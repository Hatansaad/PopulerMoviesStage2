package com.example.hatansaad.popularmoviesapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class MyAsyncTask extends AsyncTaskLoader<ArrayList<Movie>> {

    String url;

    public MyAsyncTask(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        String response = Network.fetchResponse(url);
        ArrayList<Movie> list=new ArrayList<>();
        //ArrayList<Movie> list =Json.extractFeatureFromJson(response);
        //System.out.println("list size "+list.size());
        return list;
    }
}
