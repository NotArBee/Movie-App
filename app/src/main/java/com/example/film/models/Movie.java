package com.example.film.models;

import java.util.List;

public class Movie {

    private int id;
    private String title;
    private String overview;
    private String poster_path;
    private String release_date;
    private float vote_average;
    private List<Integer> genre_ids;
    private boolean adult;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return "https://image.tmdb.org/t/p/w500" + poster_path;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public float getVoteAverage() {
        return vote_average;
    }

    public List<Integer> getGenreIds() {
        return genre_ids;
    }

    public boolean isAdult() {
        return adult;
    }
}
