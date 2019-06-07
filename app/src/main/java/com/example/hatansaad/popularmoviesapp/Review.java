package com.example.hatansaad.popularmoviesapp;

public class Review {

    private String mAuthor;
    private String mContent;


    public String getAuthor() {
        return mAuthor;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public Review(String author, String content) {

        mAuthor = author;
        mContent = content;
    }
}
