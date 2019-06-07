package com.example.hatansaad.popularmoviesapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class Movie implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int movieId;
    private String poster;
    private String title;
    private String releaseDate;
    private String voteAverage;
    private String plot;
    private String id;
    private String imageID;

    public Movie(String poster,String title,String releaseDate,String voteAverage, String plot, String id) {

        //this.poster=poster;
        //this.poster=buildPosterPath();
        this.imageID=poster;
        this.poster=buildPosterPath();

        this.title=title;
        this.releaseDate=releaseDate;
        this.voteAverage=voteAverage;
        this.plot=plot;
        this.id=id;
    }

    protected Movie(Parcel in) {
        poster = in.readString();
        title = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readString();
        plot = in.readString();
        id = in.readString();
        imageID = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPoster() {
        return poster;
        //return buildPosterPath();
    }

    public String buildPosterPath() {
        String PosterURL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/";
        //return PosterURL + poster;
        return PosterURL + imageID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster);
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(voteAverage);
        dest.writeString(plot);
        dest.writeString(id);
        dest.writeString(imageID);
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getPlot() {
        return plot;
    }

    public String getId() {
        return id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public static Creator<Movie> getCREATOR() {
        return CREATOR;
    }

}
