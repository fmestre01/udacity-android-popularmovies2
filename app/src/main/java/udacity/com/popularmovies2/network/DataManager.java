package udacity.com.popularmovies2.network;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import udacity.com.popularmovies2.movieresponse.MovieResponse;
import udacity.com.popularmovies2.movieresponse.MovieReviewResponse;
import udacity.com.popularmovies2.movieresponse.VideoTrailerResponse;
import udacity.com.popularmovies2.utils.Const;

public class DataManager {

    public static final String BASE_URL_IMAGE_POSTER = "http://image.tmdb.org/t/p/w185";
    public static final String BASE_URL_IMAGE_BACKDROP = "http://image.tmdb.org/t/p/w780";
    private static final String TAG = DataManager.class.getSimpleName();
    private static final String API_BASE_URL = "http://api.themoviedb.org/3";
    private static final String API_KEY = "";
    public static String BASE_URL_VIDEO = "https://www.youtube.com/watch?v=";
    private static DataManager mInstance;
    private Context mContext;
    private RequestQueue mRequestQueue;

    private DataManager(Context context) {
        mContext = context;
    }

    public static synchronized DataManager getInstance(Context context) {
        if (mInstance == null) {
            Log.v(TAG, "Creating data manager instance");
            mInstance = new DataManager(context.getApplicationContext());
        }
        return mInstance;
    }

    public void init() {
        mRequestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        mRequestQueue.add(request);
    }


    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        mRequestQueue.add(request);
    }

    public void cancelPendingRequests(String requestTag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(requestTag);
        }
    }

    public void terminate() {
        mRequestQueue.stop();
    }

    public void getMovies(final WeakReference<DataRequester> wRequester, String movieSortFilter, int page, String language, String tag) {
        JSONObject obj = new JSONObject();
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                DataRequester req = null;
                if (wRequester != null) {
                    req = wRequester.get();
                }
                MovieResponse movieResponse = null;
                if (jsonObject != null && !TextUtils.isEmpty(jsonObject.toString())) {
                    movieResponse =
                            new Gson().fromJson(jsonObject.toString(), MovieResponse.class);
                }

                if (req != null) {
                    if (movieResponse != null) {
                        req.onSuccess(movieResponse);
                    }
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                DataRequester req = null;
                if (wRequester != null) {
                    req = wRequester.get();
                }
                if (req != null) {
                    req.onFailure(volleyError);
                }
            }
        };

        String toBeAppendedPath = null;
        if (movieSortFilter.equals(Const.HIGHEST_RATED)) {
            toBeAppendedPath = "top_rated";
        } else if (movieSortFilter.equals(Const.MOST_POPULAR)) {
            toBeAppendedPath = "popular";
        }

        Uri.Builder builder = Uri.parse(API_BASE_URL).buildUpon();
        builder.appendPath("movie").
                appendPath(toBeAppendedPath).
                appendQueryParameter("page", String.valueOf(page)).
                appendQueryParameter("language", language).
                appendQueryParameter("api_key", API_KEY);

        String url = builder.build().toString();

        JsonRequestObject request = new JsonRequestObject(Request.Method.GET,
                url, obj, responseListener, errorListener);
        addToRequestQueue(request, tag);
    }

    public void getVideoTrailers(final WeakReference<DataRequester> wRequester, int movieId, String language, String tag) {
        Log.v(TAG, "Api call : get video Trailers");
        JSONObject obj = new JSONObject();
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.v(TAG, "Success : get video trailers returned a response");

                DataRequester req = null;
                if (wRequester != null) {
                    req = wRequester.get();
                }
                VideoTrailerResponse videoTrailerResponse = null;
                if (jsonObject != null && !TextUtils.isEmpty(jsonObject.toString())) {
                    Log.v(TAG, "Success : converting Json to Java Object via Gson");
                    videoTrailerResponse =
                            new Gson().fromJson(jsonObject.toString(), VideoTrailerResponse.class);
                }

                if (req != null) {
                    if (videoTrailerResponse != null) {
                        req.onSuccess(videoTrailerResponse);
                    }
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                DataRequester req = null;
                if (wRequester != null) {
                    req = wRequester.get();
                }
                if (req != null) {
                    req.onFailure(volleyError);
                }
            }
        };


        Uri.Builder builder = Uri.parse(API_BASE_URL).buildUpon();
        builder.appendPath("movie").
                appendPath(String.valueOf(movieId)).
                appendPath("videos").
                appendQueryParameter("language", language).
                appendQueryParameter("api_key", API_KEY);

        String url = builder.build().toString();

        JsonRequestObject request = new JsonRequestObject(Request.Method.GET,
                url, obj, responseListener, errorListener);
        addToRequestQueue(request, tag);
    }

    public void getMovieReviews(final WeakReference<DataRequester> wRequester, int movieId, String language, String tag) {
        Log.v(TAG, "Api call : get Movie Reviews");
        JSONObject obj = new JSONObject();
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.v(TAG, "Success : get movie reviews returned a response");

                DataRequester req = null;
                if (wRequester != null) {
                    req = wRequester.get();
                }
                MovieReviewResponse movieReviewResponse = null;
                if (jsonObject != null && !TextUtils.isEmpty(jsonObject.toString())) {
                    movieReviewResponse =
                            new Gson().fromJson(jsonObject.toString(), MovieReviewResponse.class);
                }

                if (req != null) {
                    if (movieReviewResponse != null) {
                        req.onSuccess(movieReviewResponse);
                    }
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                DataRequester req = null;
                if (wRequester != null) {
                    req = wRequester.get();
                }
                if (req != null) {
                    req.onFailure(volleyError);
                }
            }
        };

        Uri.Builder builder = Uri.parse(API_BASE_URL).buildUpon();
        builder.appendPath("movie").
                appendPath(String.valueOf(movieId)).
                appendPath("reviews").
                appendQueryParameter("language", language).
                appendQueryParameter("api_key", API_KEY);

        String url = builder.build().toString();

        JsonRequestObject request = new JsonRequestObject(Request.Method.GET,
                url, obj, responseListener, errorListener);
        addToRequestQueue(request, tag);
    }


}
