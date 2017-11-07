package udacity.com.popularmovies2.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesFavoritesMovies {

    private static final String PREFERENCE_NAME_FAV_MOVIES = "app_state_fav_movies";
    private static SharedPreferencesFavoritesMovies instance = null;
    private final SharedPreferences sharedPreferences;

    private SharedPreferencesFavoritesMovies(Context context) {
        sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(PREFERENCE_NAME_FAV_MOVIES, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferencesFavoritesMovies getInstance(Context context) {
        if (instance == null)
            instance = new SharedPreferencesFavoritesMovies(context);
        return instance;
    }

    public boolean getBoolean(int key) {
        return sharedPreferences.getBoolean(String.valueOf(key), false);
    }

    public void putBoolean(int key, boolean value) {
        sharedPreferences.edit().putBoolean(String.valueOf(key), value).apply();
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }
}
