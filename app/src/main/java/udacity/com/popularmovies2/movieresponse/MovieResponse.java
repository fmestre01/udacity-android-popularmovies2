package udacity.com.popularmovies2.movieresponse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResponse {

    private int page;
    private ArrayList<Movie> results;
    @SerializedName("total_results")
    private long totalResults;
    @SerializedName("total_pages")
    private long totalPages;

    public int getPage() {
        return page;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public long getTotalPages() {
        return totalPages;
    }
}
