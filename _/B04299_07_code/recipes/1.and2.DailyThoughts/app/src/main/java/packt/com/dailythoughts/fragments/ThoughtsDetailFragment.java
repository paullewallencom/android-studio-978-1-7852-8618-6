package packt.com.dailythoughts.fragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;

import packt.com.dailythoughts.R;
import packt.com.dailythoughts.providers.ThoughtsProvider;

/**
 * Created by mike on 27-07-15.
 */
public class ThoughtsDetailFragment extends Fragment {

    public interface DetailFragmentListener {
        void onSave();
    }

    private DetailFragmentListener mDetailFragmentListener;

    public void setDetailFragmentListener(DetailFragmentListener listener){
        mDetailFragmentListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_thoughts_detail, container, false);

        view.findViewById(R.id.thoughts_detail_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addThought();
            }
        });
        return view;
    }

    private void addThought(){
        EditText thoughtsEdit = (EditText)getView().findViewById(R.id.thoughts_edit_thoughts);
        RatingBar happinessRatingBar = (RatingBar)getView().findViewById(R.id.thoughs_rating_bar_happy);

        ContentValues values = new ContentValues();
        values.put(ThoughtsProvider.THOUGHTS_NAME, thoughtsEdit.getText().toString());
        values.put(ThoughtsProvider.THOUGHTS_HAPPINESS, happinessRatingBar.getRating());
        getActivity().getContentResolver().insert(ThoughtsProvider.CONTENT_URI, values);

        thoughtsEdit.setText("");
        happinessRatingBar.setRating(0);

        if (mDetailFragmentListener != null){
            mDetailFragmentListener.onSave();
        }
    }
}
