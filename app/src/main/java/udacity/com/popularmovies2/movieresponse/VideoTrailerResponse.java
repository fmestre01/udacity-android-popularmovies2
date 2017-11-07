package udacity.com.popularmovies2.movieresponse;

import java.util.ArrayList;

import udacity.com.popularmovies2.model.Video;

public class VideoTrailerResponse {

    private int id;
    private ArrayList<Video> results;

    public int getId() {
        return id;
    }

    public ArrayList<Video> getResults() {
        return results;
    }
}
