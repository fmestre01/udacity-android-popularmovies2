package udacity.com.popularmovies2.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import udacity.com.popularmovies2.R;
import udacity.com.popularmovies2.fragment.DetailFragment;


public class MovieDetailActivity extends AppCompatActivity {

    public static final String DETAIL_FRAGMENT_TAG = "DF";
    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if (savedInstanceState != null) {
            return;
        } else {
            addDetailFragment();
        }
    }

    public void addDetailFragment() {
        if (!isFinishing()) {
            DetailFragment detailFragment = DetailFragment.newInstance(getIntent().getExtras());

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.detail_container, detailFragment, DETAIL_FRAGMENT_TAG)
                    .commit();
        }
    }
}
