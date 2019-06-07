package com.example.hatansaad.popularmoviesapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    Database database;
    //MutableLiveData<LiveData<List<Movie>>> movieLiveDataFavorite = new MutableLiveData<>();
    MutableLiveData <List<Movie>> movieLiveDataMain = new MutableLiveData<>();
    LiveData<List<Movie>> movieLiveDataFavorite;

    public MovieViewModel (Application application) {
        super (application);
        database = Database.getDatabase(application);
        movieLiveDataFavorite= database.moviesDAO().getAllMovies();
    }

    public LiveData<List<Movie>> getFavMovies (boolean fav, String url) {

        movieLiveDataFavorite = database.moviesDAO().getAllMovies();
        return movieLiveDataFavorite;
    }

    public MutableLiveData<List<Movie>> getMainMovies (boolean fav, String url) {

            new MoviesfromServerAsyncTask().execute(url);
            return movieLiveDataMain;

    }

    public class MoviesfromServerAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... url) {
            String response = Network.fetchResponse(url[0]);
            List<Movie> movies =  Json.extractFeatureFromJson(response);
            movieLiveDataMain.postValue(movies);
            //System.out.println("in MoviesfromServerAsyncTask "+movies.size());
            return null;
        }
    }
    public class FavoriteAsyncTask extends AsyncTask <Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            //System.out.println("in Favorite AsyncTask ");
            movieLiveDataFavorite= database.moviesDAO().getAllMovies();
            return null;
        }
    }
}
