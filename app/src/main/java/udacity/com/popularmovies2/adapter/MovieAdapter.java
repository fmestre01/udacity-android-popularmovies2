package udacity.com.popularmovies2.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.com.popularmovies2.R;
import udacity.com.popularmovies2.activity.MainActivity;
import udacity.com.popularmovies2.activity.MovieDetailActivity;
import udacity.com.popularmovies2.fragment.DetailFragment;
import udacity.com.popularmovies2.movieresponse.Movie;
import udacity.com.popularmovies2.utils.PicassoUtil;

import static udacity.com.popularmovies2.activity.MovieDetailActivity.DETAIL_FRAGMENT_TAG;
import static udacity.com.popularmovies2.utils.Const.ARG_MOVIE_DETAIL;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static Context context;
    private static List<Movie> dataSet;
    private int lastAnimatedItemPosition = -1;
    private boolean mTwoPane;

    public MovieAdapter(Context c, List<Movie> data, boolean twoPane) {
        context = c;
        dataSet = data;
        mTwoPane = twoPane;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovieViewHolder cusHolder = (MovieViewHolder) holder;
        cusHolder.mTVTitle.setText(dataSet.get(position).getTitle());
        cusHolder.mTVRating.setText(String.valueOf(dataSet.get(position).getVoteAverage()));
        String completePosterPath = dataSet.get(position).getPosterPath();
        PicassoUtil.with(context).load(completePosterPath).
                placeholder(R.mipmap.placeholder)
                .error(R.mipmap.placeholder)
                .into(cusHolder.mIVThumbNail);
        cusHolder.mIVThumbNail.setVisibility(View.VISIBLE);
        setEnterAnimation(cusHolder.mCardView, position);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    private void setEnterAnimation(View viewToAnimate, int position) {
        if (position > lastAnimatedItemPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.translate_up);
            viewToAnimate.startAnimation(animation);
            lastAnimatedItemPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(final RecyclerView.ViewHolder holder) {
        ((MovieViewHolder) holder).mCardView.clearAnimation();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.tv_title)
        TextView mTVTitle;
        @BindView(R.id.card_view)
        CardView mCardView;
        @BindView(R.id.iv_thumbnail)
        ImageView mIVThumbNail;
        @BindView(R.id.tv_rating)
        TextView mTVRating;


        public MovieViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.mIVThumbNail.setOnClickListener(this);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int itemPosition = getAdapterPosition();
            if (itemPosition != RecyclerView.NO_POSITION) {
                Movie movie = dataSet.get(itemPosition);

                switch (view.getId()) {
                    case R.id.iv_thumbnail:

                        Bundle bundle = new Bundle();
                        bundle.putParcelable(ARG_MOVIE_DETAIL, movie);

                        if (mTwoPane) {
                            addDetailFragmentForTwoPane(bundle);
                        } else {
                            Intent movieDetailIntent = new Intent(context, MovieDetailActivity.class);
                            movieDetailIntent.putExtras(bundle);
                            context.startActivity(movieDetailIntent);
                        }
                        break;

                    default:
                        Toast.makeText(context, "You clicked at position " + itemPosition +
                                " on movie thumbnail : " +
                                movie.getTitle(), Toast.LENGTH_SHORT).show();
                }
            }

        }

        public void addDetailFragmentForTwoPane(Bundle bundle) {
            DetailFragment detailFragment = DetailFragment.newInstance(bundle);
            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.support.design.R.anim.abc_grow_fade_in_from_bottom,
                            android.support.design.R.anim.abc_shrink_fade_out_from_bottom)
                    .replace(R.id.detail_container, detailFragment, DETAIL_FRAGMENT_TAG)
                    .commit();
        }

        @Override
        public boolean onLongClick(View v) {
            int itemPosition = getAdapterPosition();
            return true;
        }
    }
}
