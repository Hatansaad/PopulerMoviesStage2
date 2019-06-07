package com.example.hatansaad.popularmoviesapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Trailer>> {

    private Movie movie;
    private ArrayList<Trailer> trailers = new ArrayList<>();
    private ArrayList<Review> reviews = new ArrayList<>();
    private final int LOADER_ID = 2;
    private final int REVIEWS_LOADER_ID = 3;
    private TrailerRecyclerViewAdapter adapter;
    private ReviewRecyclerViewAdapter reviewAdapter;
    private RecyclerView trailerRecyclerView;
    private RecyclerView reviewRecyclerView;
    private Context context;
    FavoriteMovies favoriteMovies /*= new FavoriteMovies(this)*/;
    ImageView star;
    Boolean Clicked=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        context=getApplicationContext();
        favoriteMovies = new FavoriteMovies(this);
        Bundle args = getIntent().getExtras();
        movie = args.getParcelable("SelectedMovie");

        //System.out.println("movie id is "+movie.getId());

        //Trailers
        trailerRecyclerView = findViewById(R.id.trailer_recycler_view);
        adapter = new TrailerRecyclerViewAdapter(this, trailers);
        trailerRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        trailerRecyclerView.setLayoutManager(layoutManager);
        //Reviews
        reviewRecyclerView = findViewById(R.id.review_recycler_view);
        reviewAdapter = new ReviewRecyclerViewAdapter(this, reviews);
        reviewRecyclerView.setAdapter(reviewAdapter);
        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(this);
        reviewRecyclerView.setLayoutManager(reviewLayoutManager);

        ImageView mImageView = findViewById(R.id.poster);
        TextView titleTextView = findViewById(R.id.title);
        TextView releasDateTextView = findViewById(R.id.release_date);
        TextView voteAverageTextView = findViewById(R.id.vote_average);
        TextView plotTextView = findViewById(R.id.plot);

        star = findViewById(R.id.star);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SingleMovieAsyncTask().execute(movie);
                Clicked=true;
            }
        });

        //Picasso.with(this).load(movie.getPoster()).into(mImageView);
        Picasso.with(this).load(movie.buildPosterPath()).into(mImageView);
        new SingleMovieAsyncTask().execute(movie);
        titleTextView.setText(movie.getTitle());
        releasDateTextView.setText(movie.getReleaseDate());
        voteAverageTextView.setText(movie.getVoteAverage());
        plotTextView.setText(movie.getPlot());

        getLoaderManager().initLoader(LOADER_ID, null, this);
        getLoaderManager().initLoader(REVIEWS_LOADER_ID, null, ReviewResultLoaderListener);

    }

    private String buildTrailerURL() {

        final String BASE_URL = "https://api.themoviedb.org/3/movie";
        final String API_KEY_QUERY_PARAMETER = "api_key";
        final String API_KEY = "4a98b81f8fff36eaeb9325505937b94b";

        //System.out.println("sortValue: "+sortByValue);
        Uri uri = Uri.parse(BASE_URL);
        Uri.Builder builder = uri.buildUpon();

        builder.appendPath(movie.getId());
        builder.appendPath("videos");
        builder.appendQueryParameter(API_KEY_QUERY_PARAMETER,API_KEY);
        return builder.toString();
    }

    private String buildReviewURL() {

        final String BASE_URL = "https://api.themoviedb.org/3/movie";
        final String API_KEY_QUERY_PARAMETER = "api_key";
        final String API_KEY = "4a98b81f8fff36eaeb9325505937b94b";

        Uri uri = Uri.parse(BASE_URL);
        Uri.Builder builder = uri.buildUpon();

        builder.appendPath(movie.getId());
        builder.appendPath("reviews");
        builder.appendQueryParameter(API_KEY_QUERY_PARAMETER,API_KEY);
        return builder.toString();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        System.out.println("OnRestart() ");

        favoriteMovies = new FavoriteMovies(this);
        star = findViewById(R.id.star);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SingleMovieAsyncTask().execute(movie);
                Clicked=true;
            }
        });

        getLoaderManager().restartLoader(LOADER_ID,null,this);
        getLoaderManager().restartLoader(REVIEWS_LOADER_ID,null,ReviewResultLoaderListener);
    }

    @Override
    public Loader<ArrayList<Trailer>> onCreateLoader(int id, Bundle args) {
        String strUrl= buildTrailerURL();
        //System.out.println("URL is "+strUrl);
        return new TrailerAsyncTaskLoader(this, strUrl);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Trailer>> loader, ArrayList<Trailer> data) {
        trailers.clear();
        trailers.addAll(data);
        //System.out.println("trailers list size "+trailers.size());
        /*for(int i=0;i<trailers.size();i++){
            System.out.println("Row "+trailers.get(i).getName()+" and key "+trailers.get(i).getVideoKey());
        }*/
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Trailer>> loader) {
        trailers.clear();
        adapter.notifyDataSetChanged();
    }



    private LoaderManager.LoaderCallbacks<ArrayList<Review>> ReviewResultLoaderListener
            = new LoaderManager.LoaderCallbacks<ArrayList<Review>>() {

        @Override
        public Loader<ArrayList<Review>> onCreateLoader(int id, Bundle args) {
            String strUrl= buildReviewURL();
            //System.out.println("URL for reviews is "+strUrl);
            return new ReviewAsyncTaskLoader(context, strUrl);
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Review>> loader, ArrayList<Review> data) {
            reviews.clear();
            reviews.addAll(data);
            //System.out.println("reviews list size "+reviews.size());
        /*for(int i=0;i<trailers.size();i++){
            System.out.println("Row "+trailers.get(i).getName()+" and key "+trailers.get(i).getVideoKey());
        }*/
            reviewAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Review>> loader) {
            reviews.clear();
            reviewAdapter.notifyDataSetChanged();
        }
    };

    private void markAsFavorite(Movie movie) {
        favoriteMovies.insertFavMovie(movie);
    }

    private void unfavoriteMovie(Movie movie) {
        favoriteMovies.deleteFavMovie(movie);
    }


    private class SingleMovieAsyncTask extends AsyncTask<Movie, Void, Movie> {

        @Override
        protected Movie doInBackground(Movie... movie) {
            Database database = Database.getDatabase(DetailsActivity.this);
            Movie singleMovie = database.moviesDAO().getSingleMovie(movie[0].getId());
            //System.out.println("single movie is "+singleMovie.getId());

            return singleMovie;
        }

        @Override
        protected void onPostExecute(Movie list) {
            super.onPostExecute(list);
            if(Clicked){
                if (list != null) {
                    unfavoriteMovie(list);
                    star.setImageResource(R.drawable.unfav_star);
                } else {
                    markAsFavorite(movie);
                    star.setImageResource(R.drawable.fav_star);
                }
            }
            else {
                if (list != null) {
                    star.setImageResource(R.drawable.fav_star);
                } else {
                    star.setImageResource(R.drawable.unfav_star);
                }
            }
        }
    }
}
