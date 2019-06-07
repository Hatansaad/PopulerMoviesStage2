package com.example.hatansaad.popularmoviesapp;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.hatansaad.popularmoviesapp.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Json {

    private static final String RESULTS_KEY = "results";
    private static final String POSTER_KEY = "poster_path";
    private static final String TITLE_KEY = "title";
    private static final String RELEASE_DATE_KEY = "release_date";
    private static final String VOTE_AVERAGE_KEY = "vote_average";
    private static final String PLOT_KEY = "overview";
    private static final String ID = "id";

    public static List<Movie> extractFeatureFromJson(String movieJSONRes) {

        if (TextUtils.isEmpty(movieJSONRes)) {
            return null;
        }
        List<Movie> movies = new ArrayList<>();
        LiveData<List<Movie>> mov;


        try {

            JSONObject baseJsonResponse = new JSONObject(movieJSONRes);

            if (baseJsonResponse.has(RESULTS_KEY)) {

                JSONArray moviesArray = baseJsonResponse.getJSONArray(RESULTS_KEY);

                for (int i = 0; i < moviesArray.length(); i++) {

                    JSONObject currentmovie = moviesArray.getJSONObject(i);

                    String poster =currentmovie.getString(POSTER_KEY);
                    String title =currentmovie.getString(TITLE_KEY);
                    String releaseDate =currentmovie.getString(RELEASE_DATE_KEY);
                    String voteAverage =currentmovie.getString(VOTE_AVERAGE_KEY);
                    String plot =currentmovie.getString(PLOT_KEY);
                    String id=currentmovie.getString(ID);

                    if (!poster.equals("null")) {

                        Movie movie = new Movie(poster,title,releaseDate,voteAverage,plot,id);
                        movies.add(movie);
                    }
                }
            } else {
                movies = null;
            }
        } catch (JSONException e) {
            System.out.println("Error while parsing the json");
        }

        if (movies == null){ System.out.println("movies are null"); }
        else { System.out.println("movies not null"); }
        return movies;
    }

    public static ArrayList<Trailer> trailersForMovie(String jsonString, Context context) {
        if (jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray results = jsonObject.getJSONArray(context.getResources().getString(R.string.json_results_array));
                ArrayList<Trailer> trailerList = new ArrayList<>();
                for (int i = 0; i < results.length(); i++) {
                    JSONObject r = results.getJSONObject(i);
                    String name = r.getString("name");
                    String videoKey = r.getString("key");
                    Trailer trailer = new Trailer(name, videoKey);
                    trailerList.add(trailer);
                }
                return trailerList;
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ArrayList<Review> reviewsForMovie(String jsonString, Context context) {
        if (jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray results = jsonObject.getJSONArray(context.getResources().getString(R.string.json_results_array));
                ArrayList<Review> reviewList = new ArrayList<>();
                for (int i = 0; i < results.length(); i++) {
                    JSONObject r = results.getJSONObject(i);
                    String author = r.getString("author");
                    String content = r.getString("content");
                    Review review = new Review(author, content);
                    reviewList.add(review);
                }
                return reviewList;
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
