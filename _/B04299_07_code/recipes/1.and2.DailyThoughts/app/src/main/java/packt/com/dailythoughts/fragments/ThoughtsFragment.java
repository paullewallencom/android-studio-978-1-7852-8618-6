package packt.com.dailythoughts.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import packt.com.dailythoughts.R;
import packt.com.dailythoughts.providers.ThoughtsProvider;

/**
 * Created by mike on 27-07-15.
 */
public class ThoughtsFragment extends Fragment  implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private ListView mListView;
    private SimpleCursorAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_thoughts, container, false);
        mListView = (ListView)view.findViewById(R.id.thoughts_list);
        getData();
        return view;
    }

    private void getData(){
        String[] projection = new String[] { ThoughtsProvider.THOUGHTS_ID, ThoughtsProvider.THOUGHTS_NAME, ThoughtsProvider.THOUGHTS_HAPPINESS};
        int[] target = new int[] { R.id.adapter_thought_id, R.id.adapter_thought_title, R.id.adapter_thought_rating };
        getLoaderManager().initLoader(0, null, this);
        mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.adapter_thought, null, projection, target, 0);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[] { ThoughtsProvider.THOUGHTS_ID, ThoughtsProvider.THOUGHTS_NAME, ThoughtsProvider.THOUGHTS_HAPPINESS};
        String sortBy = "_id DESC";
        CursorLoader cursorLoader = new CursorLoader(getActivity(), ThoughtsProvider.CONTENT_URI, projection, null, null, sortBy);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
