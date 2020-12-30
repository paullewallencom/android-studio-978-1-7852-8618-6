package packt.com.dailythoughts.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import packt.com.dailythoughts.R;
import packt.com.dailythoughts.providers.ThoughtsProvider;

/**
 * Created by mike on 27-07-15.
 */
public class ThoughtsKpiFragment extends Fragment   implements
        LoaderManager.LoaderCallbacks<Cursor> {

    public static int LOADER_COUNT_THOUGHTS = 1;
    public static int LOADER_AVG_RATING = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_thoughts_kpi, container, false);
        getKpis();
        return view;
    }

    private void getKpis(){
        getLoaderManager().initLoader(LOADER_COUNT_THOUGHTS, null, this);
        getLoaderManager().initLoader(LOADER_AVG_RATING, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == LOADER_COUNT_THOUGHTS) {
            String[] projection = new String[] {"COUNT(*) AS kpi"};
            android.content.CursorLoader cursorLoader = new android.content.CursorLoader(getActivity(), ThoughtsProvider.CONTENT_URI, projection, null, null, null);
            return cursorLoader;
        }
        else {
            String[] projection = new String[] {"AVG(happiness) AS kpi"};
            android.content.CursorLoader cursorLoader = new android.content.CursorLoader(getActivity(),ThoughtsProvider.CONTENT_URI, projection, null, null, null);
            return cursorLoader;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null || !data.moveToNext()) {
            return;
        }

        if (loader.getId() == LOADER_COUNT_THOUGHTS) {
            setCountedThoughts(data.getInt(0));

        }
        else{
            setAvgHappiness(data.getFloat(0));
        }
    }

    private void setCountedThoughts(final int counted){
        if (getActivity()==null){
            return;
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView countText = (TextView)getView().findViewById(R.id.thoughts_kpi_count);
                countText.setText(String.valueOf(counted));
            }
        });
    }

    private void setAvgHappiness(final float avg){
        if (getActivity()==null){
            return;
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RatingBar ratingBar = (RatingBar)getView().findViewById(R.id.thoughts_rating_bar_happy);
                ratingBar.setRating(avg);
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
