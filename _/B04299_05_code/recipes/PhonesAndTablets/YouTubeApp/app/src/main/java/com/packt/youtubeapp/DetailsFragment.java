package com.packt.youtubeapp;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URL;

/**
 * Created by mike on 04-07-15.
 */
public class DetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_details, container, false);


        return view;
    }

    public void showDetails (final VideoItem video) {

        TextView tv = (TextView) getView().findViewById(R.id.detail_text);
        tv.setText(video.getDescription());

        getView().findViewById(R.id.detail_button_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlayVideo(video.getId());
            }
        });

        final ImageView iv = (ImageView) getView().findViewById(R.id.detail_image);
        new Thread(new Runnable() {
            public void run() {
                loadThumbnail(video, iv);
            }
        }).start();
    }

    private void loadThumbnail(VideoItem video,final ImageView iv){
        try {
            URL url = new URL(video.getThumbnailURL());
            final Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    iv.setImageBitmap(bmp);
                }
            });
        }
        catch (Exception ex){
            Log.d(this.getClass().toString(), ex.getMessage());
        }
    }

    private void onPlayVideo(String videoId){

        Intent intent = new Intent(getActivity().getApplicationContext(), PlayerActivity.class);
        intent.putExtra("VIDEO_ID", videoId);
        startActivity(intent);
    }
}
