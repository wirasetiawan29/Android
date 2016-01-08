package com.imovie.gits.imovie.Movie;

/**
 * Created by wirasetiawan29 on 16/01/2015.
 */
public class Movies {
    int id;
    String title;
    String year;
    String mpaa_rating;
    String runtime;
    String critics_consensus;
    //release_dates
    //ratings
    String synopsis;
    Poster posters;

    public Movies(){}
    public Poster getPosters() {
        return posters;
    }
    //abridged_cast
    //alternate_ids
    //links


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMpaa_rating() {
        return mpaa_rating;
    }

    public void setMpaa_rating(String mpaa_rating) {
        this.mpaa_rating = mpaa_rating;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setPosters(Poster posters) {
        this.posters = posters;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getCritics_consensus() {
        return critics_consensus;
    }

    public void setCritics_consensus(String critics_consensus) {
        this.critics_consensus = critics_consensus;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
