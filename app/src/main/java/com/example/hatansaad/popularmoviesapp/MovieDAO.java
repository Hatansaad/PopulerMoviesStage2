package com.example.hatansaad.popularmoviesapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MovieDAO {

    @Insert
    public void insertMovie(Movie movie);

    @Insert
    public void insertMultipleMovies(List<Movie> movies);

    @Query("DELETE FROM Movie WHERE id = :movieID")
    void deleteMovie(String movieID);

    @Update
    public void updateMovie(Movie movie);

    @Query("Select * from Movie")
    public LiveData<List<Movie>> getAllMovies();
    //public List<Movie> getAllMovies();

    @Query("select * from Movie where id= :movieID")
    public Movie getSingleMovie (String movieID);

}
