package udacity.com.popularmovies2.utils;

public interface DataUpdateListener {
    void onSuccess(int operationType);

    void onFailure();
}
