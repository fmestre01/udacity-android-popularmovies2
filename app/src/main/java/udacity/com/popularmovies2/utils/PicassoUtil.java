package udacity.com.popularmovies2.utils;

import android.content.Context;

public class PicassoUtil {
    private static com.squareup.picasso.Picasso instance;

    private PicassoUtil() {
        throw new AssertionError("0 instances.");
    }

    public static com.squareup.picasso.Picasso with(Context context) {
        if (instance == null) {
            instance = new com.squareup.picasso.Picasso.Builder(context.getApplicationContext()).build();
        }
        return instance;
    }
}