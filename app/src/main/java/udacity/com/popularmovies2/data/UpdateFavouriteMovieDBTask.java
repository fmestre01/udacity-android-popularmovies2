package udacity.com.popularmovies2.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import udacity.com.popularmovies2.movieresponse.Movie;
import udacity.com.popularmovies2.utils.DataUpdateListener;

public class UpdateFavouriteMovieDBTask extends AsyncTask<Void, Void, Void> {

    public static final int ADDED_TO_FAVORITE = 1;
    public static final int REMOVED_FROM_FAVORITE = 2;
    private static final String TAG = UpdateFavouriteMovieDBTask.class.getSimpleName();
    private Context mContext;
    private Movie mMovie;
    private DataUpdateListener mDataUpdateListener;

    public UpdateFavouriteMovieDBTask(Context context, Movie movie, DataUpdateListener updateListener) {
        mDataUpdateListener = updateListener;
        mContext = context;
        mMovie = movie;
    }

    @Override
    protected Void doInBackground(Void... params) {
        deleteOrSaveFavoriteMovie();
        return null;
    }

    private void deleteOrSaveFavoriteMovie() {
        Cursor favMovieCursor = mContext.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                new String[]{MovieContract.MovieEntry.COLUMN_MOVIE_ID},
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                new String[]{String.valueOf(mMovie.getId())},
                null);

        if (favMovieCursor.moveToFirst()) {
            int rowDeleted = mContext.getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI,
                    MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                    new String[]{String.valueOf(mMovie.getId())});

            if (rowDeleted > 0) {
                mDataUpdateListener.onSuccess(REMOVED_FROM_FAVORITE);
            } else {
                mDataUpdateListener.onFailure();
            }

        } else {
            ContentValues values = new ContentValues();
            values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, mMovie.getId());
            values.put(MovieContract.MovieEntry.COLUMN_TITLE, mMovie.getTitle());
            values.put(MovieContract.MovieEntry.COLUMN_POSTER_IMAGE, mMovie.getPosterPath());
            values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, mMovie.getOverview());
            values.put(MovieContract.MovieEntry.COLUMN_AVERAGE_RATING, mMovie.getVoteAverage());
            values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, mMovie.getReleaseDate());
            values.put(MovieContract.MovieEntry.COLUMN_BACKDROP_IMAGE, mMovie.getBackdropPath());

            Uri insertedUri = mContext.getContentResolver().insert(
                    MovieContract.MovieEntry.CONTENT_URI,
                    values);

            long movieRowId = ContentUris.parseId(insertedUri);

            if (movieRowId > 0) {
                mDataUpdateListener.onSuccess(ADDED_TO_FAVORITE);
            } else {
                mDataUpdateListener.onFailure();
            }
        }
        favMovieCursor.close();
    }
}
