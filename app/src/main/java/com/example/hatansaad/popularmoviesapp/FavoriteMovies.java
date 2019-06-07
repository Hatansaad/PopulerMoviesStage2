package com.example.hatansaad.popularmoviesapp;

import android.content.Context;
import android.os.AsyncTask;

public class FavoriteMovies {
    private boolean setAsFavorite = true;
    private Database database;

    public FavoriteMovies (Context context) {
        database = Database.getDatabase(context);
    }

    public void insertFavMovie (Movie movie){
        setAsFavorite = true;
        new FavoriteMoviesAsyncTask ().execute(movie);
    }

    public void deleteFavMovie (Movie movie){
        setAsFavorite = false;
        new FavoriteMoviesAsyncTask ().execute(movie);
    }


    private class FavoriteMoviesAsyncTask extends AsyncTask<Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movieModel) {
            if (setAsFavorite){
                System.out.println("inserting movie with id "+movieModel[0].getId());
                    database.moviesDAO().insertMovie(movieModel[0]);
            } else {
                System.out.println("deleting movie with id "+movieModel[0].getId());
                database.moviesDAO().deleteMovie(movieModel[0].getId());
            }
            return null;
        }
    }
}
