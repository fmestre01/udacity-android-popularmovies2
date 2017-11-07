package udacity.com.popularmovies2.movieresponse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import udacity.com.popularmovies2.model.Movie;

public class MovieReviewResponse {

    int id;
    int page;
    private ArrayList<Movie> results;
    @SerializedName("total_pages")
    private long totalPages;
    @SerializedName("total_results")
    private long totalResults;

    public int getId() {
        return id;
    }

    public int getPage() {
        return page;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public long getTotalResults() {
        return totalResults;
    }
}
