package com.example.hatansaad.popularmoviesapp;

import android.app.LoaderManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Movie> movies=new ArrayList<>();
    private RecyclerViewAdapter adapter;
    MovieViewModel viewModel;
    TextView noInternetMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int NUMBER_OF_COLUMNS = 2;

        movies = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new RecyclerViewAdapter(this, movies);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS);
        recyclerView.setLayoutManager(layoutManager);

        viewModel=ViewModelProviders.of(this).get(MovieViewModel.class);
        noInternetMsg = findViewById(R.id.no_internet_tv);

        //System.out.println("sort value is "+getSortValue());
        if (getSortValue().equals(getString(R.string.favorite_movies_pref))) {

            viewModel.movieLiveDataFavorite.observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> moviess) {
                    movies.clear();
                    movies.addAll(moviess);
                    adapter.notifyDataSetChanged();
                }
            });

        } else {
            if (isConnected()) {
                viewModel.getMainMovies(false,buildURL()).observe(this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movieModels) {
                        movies.clear();
                        movies.addAll(movieModels);
                        adapter.notifyDataSetChanged();
                    }
                });

                noInternetMsg.setVisibility(View.GONE);


            } else {
                noInternetMsg.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //System.out.println("in onRestart()");
        if (getSortValue().equals(getString(R.string.favorite_movies_pref))) {

            viewModel.getFavMovies(true,"").observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> moviess) {

                    movies.clear();

                    if (movies != null) {
                        movies.addAll(moviess);
                        adapter.notifyDataSetChanged();
                    }

                }
            });

        } else {
            if (isConnected()) {
                noInternetMsg.setVisibility(View.GONE);
                viewModel.getMainMovies(false, buildURL()).observe(this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movieModels) {
                        movies.clear();
                        movies.addAll(movieModels);
                        adapter.notifyDataSetChanged();
                    }
                });

            } else {
                noInternetMsg.setVisibility(View.VISIBLE);
            }

        }
    }


    private String buildURL() {

        final String BASE_URL = "https://api.themoviedb.org/3/movie";
        final String API_KEY_QUERY_PARAMETER = "api_key";
        //final String API_KEY = "98fb4fcf1cadbaf4b043a664285933f2";
        final String API_KEY = "4a98b81f8fff36eaeb9325505937b94b";

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortByValue =sharedPreferences.getString(getString(R.string.sort_by_key),getString(R.string.most_popular));
        //System.out.println("sortValue: "+sortByValue);
        Uri uri = Uri.parse(BASE_URL);
        Uri.Builder builder = uri.buildUpon();
        //builder.appendQueryParameter("primary_release_year", "2018");

        if (sortByValue.equals(getString(R.string.top_rated))){
            //builder.appendQueryParameter("sort_by", "top_rated");
            builder.appendPath("top_rated");
        } else {
            //builder.appendQueryParameter("sort_by","popular");
            builder.appendPath("popular");
        }

        builder.appendQueryParameter(API_KEY_QUERY_PARAMETER,API_KEY);
        return builder.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings_menu_item) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        }
        return true;
    }

    private String getSortValue() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPrefs.getString(getString(R.string.sort_by_key), getString(R.string.most_popular));
    }

    private Boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

}
