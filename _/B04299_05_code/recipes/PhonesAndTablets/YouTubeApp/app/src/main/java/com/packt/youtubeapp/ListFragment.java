package com.packt.youtubeapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by mike on 04-07-15.
 */
public class ListFragment extends Fragment {

    private static YouTube mYoutube;

    private YouTube.Search.List mYouTubeList;
    private ListView mListView;
    private VideoAdapter mAdapter;
    private List<VideoItem> mVideos;

    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_list, container, false);

        mListView = (ListView)view.findViewById(R.id.main_video_list_view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VideoItem video = mVideos.get(i);
                onVideoClicked(video);
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);

        new Thread(new Runnable() {
            public void run(){
                loadVideos();
            }
        }).start();


    }

    private void loadVideos(){

        mYoutube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {}
        }).setApplicationName(getString(R.string.app_name)).build();

        try{
            mYouTubeList = mYoutube.search().list("id,snippet");
            mYouTubeList.setKey("AIzaSyCsgBbynpTafeUZPyBPm90_nts3364uweY");
            mYouTubeList.setType("video");
            mYouTubeList.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");

            mVideos =search("android development");
            mAdapter = new VideoAdapter(getActivity(), R.layout.adapter_video, (ArrayList<VideoItem>) mVideos);

            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    mListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            });


        } catch (IOException e) {
            Log.d(this.getClass().toString(), "Could not initialize: " + e);
        }

    }

    private void onVideoClicked(VideoItem video) {
        DetailsFragment detailsFragment = (DetailsFragment) getFragmentManager().findFragmentByTag(MainActivity.TAG_DETAILS_FRAGMENT);

        if (detailsFragment != null){

            detailsFragment.showDetails(video);
        }
        else {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            Bundle extras = new Bundle();
            extras.putString("videoId", video.getId());
            extras.putString("videoDescription", video.getDescription());
            extras.putString("videoThumb", video.getThumbnailURL());
            intent.putExtras(extras);
            this.startActivity(intent);
        }
    }

    public List<VideoItem> search(String keywords){
        mYouTubeList.setQ(keywords);
        try{
            SearchListResponse response = mYouTubeList.execute();
            List<SearchResult> results = response.getItems();

            List<VideoItem>  items = new ArrayList<VideoItem>();
            for(SearchResult result:results){
                VideoItem item = new VideoItem();
                item.setTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                item.setId(result.getId().getVideoId());
                items.add(item);
            }
            return items;
        }catch(IOException e){
            Log.d("YC", "Could not search: " + e);
            return null;
        }
    }

}
