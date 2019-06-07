package com.example.hatansaad.popularmoviesapp;

public class Trailer {

    private String mName;
    private String mVideoKey;

    public Trailer(String name, String videoKey) {
        mName = name;
        mVideoKey = videoKey;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getVideoKey() {
        return mVideoKey;
    }

}
