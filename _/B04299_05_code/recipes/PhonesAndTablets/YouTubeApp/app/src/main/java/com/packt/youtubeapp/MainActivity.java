package com.packt.youtubeapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static YouTube mYoutube;

    private List<VideoItem> mVideos;
    private YouTube.Search.List query;
    private ListView mListView;
    private VideoAdapter mAdapter;

    private DetailsFragment mDetailsFragment;
    private ListFragment mListFragment;

    public static String TAG_LIST_FRAGMENT = "LIST";
    public static String TAG_DETAILS_FRAGMENT = "DETAILS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListFragment = new ListFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.main_container_for_list_fragment, mListFragment, TAG_LIST_FRAGMENT);//.commit();

        if (findViewById(R.id.main_container_for_detail_fragment)!= null){
            mDetailsFragment = new DetailsFragment();
           // getFragmentManager().beginTransaction();
            ft.add(R.id.main_container_for_detail_fragment, mDetailsFragment, TAG_DETAILS_FRAGMENT);//.commit();
        }
        ft.commit();
/*
        mListView = (ListView)findViewById(R.id.main_video_list_view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VideoItem video = (VideoItem) mVideos.get(i);
                onVideoClicked(video);
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        mYoutube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {}
        }).setApplicationName(getString(R.string.app_name)).build();

        try{
            query = mYoutube.search().list("id,snippet");
            query.setKey("AIzaSyCsgBbynpTafeUZPyBPm90_nts3364uweY");
            query.setType("video");
            query.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");

            mVideos =search("android development");
            for (VideoItem vi : mVideos){
                Log.d("TEST", vi.getTitle() + " " + vi.getThumbnailURL() + " " + vi.getId() + " " + vi.getDescription());
            }

            mAdapter = new VideoAdapter(this, R.layout.adapter_video, (ArrayList<VideoItem>) mVideos);
            mListView.setAdapter(mAdapter);

        }
        catch(IOException e){
            Log.d("YC", "Could not initialize: " + e);
        }
*/
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


// Chrome casting may be an issue (if so leave it out)

// For TV use YouTube Ffragment

// http://stackoverflow.com/questions/18115563/open-chromecast-youtube-video-from-my-android-app
