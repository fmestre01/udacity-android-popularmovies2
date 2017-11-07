package udacity.com.popularmovies2;

import android.app.Application;
import android.util.Log;

import udacity.com.popularmovies2.network.DataManager;

public class PopularMoviesApplication extends Application {

    private static final String TAG = PopularMoviesApplication.class
            .getSimpleName();
    private DataManager mDataMan;

    @Override
    public void onCreate() {
        super.onCreate();
        initApp();
        Log.v(TAG, "App init");
    }

    private void initApp() {
        mDataMan = DataManager.getInstance(PopularMoviesApplication.this);
        mDataMan.init();
    }

    public synchronized DataManager getDataManager() {
        return mDataMan;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mDataMan != null) {
            mDataMan.terminate();
        }
    }
}
