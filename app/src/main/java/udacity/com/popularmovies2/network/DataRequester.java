package udacity.com.popularmovies2.network;

public interface DataRequester {
    void onFailure(Throwable error);

    void onSuccess(Object respObj);
}
