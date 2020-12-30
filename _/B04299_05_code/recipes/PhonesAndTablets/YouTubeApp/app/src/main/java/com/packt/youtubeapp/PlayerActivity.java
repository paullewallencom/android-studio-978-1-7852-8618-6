package com.packt.youtubeapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import com.google.api.services.youtube.YouTube;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.packt.youtubeapp.R.layout.activity_player;

/**
 * Created by mike on 03-07-15.
 */

public class PlayerActivity extends Activity implements View.OnClickListener {

    private static String DeveloperKey = "AIzaSyCsgBbynpTafeUZPyBPm90_nts3364uweY";

    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;

    private static final String VIDEO_ID = "cdgQpa1pUUE";
    private static final String PLAYLIST_ID =  "7E952A67F31C58A3";
    private static final ArrayList<String> VIDEO_IDS = new ArrayList<String>(Arrays.asList(
            new String[]{"cdgQpa1pUUE", "8aCYZ3gXfy8", "zMabEyrtPRg"}));

    private Button playVideoButton;
    private Button playPlaylistButton;
    private Button playVideoListButton;

    private EditText startIndexEditText;
    private EditText startTimeEditText;
    private CheckBox autoplayCheckBox;
    private CheckBox lightboxModeCheckBox;

    private String mSelectedVideoId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        playVideoButton = (Button) findViewById(R.id.start_video_button);
        playPlaylistButton = (Button) findViewById(R.id.start_playlist_button);
        playVideoListButton = (Button) findViewById(R.id.start_video_list_button);
        startIndexEditText = (EditText) findViewById(R.id.start_index_text);
        startTimeEditText = (EditText) findViewById(R.id.start_time_text);
        autoplayCheckBox = (CheckBox) findViewById(R.id.autoplay_checkbox);
        lightboxModeCheckBox = (CheckBox) findViewById(R.id.lightbox_checkbox);

        playVideoButton.setOnClickListener(this);
        playPlaylistButton.setOnClickListener(this);
        playVideoListButton.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();

        mSelectedVideoId = extras.getString("VIDEO_ID");
    }

    @Override
    public void onClick(View v) {

        int startIndex = parseInt(startIndexEditText.getText().toString(), 0);
        int startTimeMillis = parseInt(startTimeEditText.getText().toString(), 0) * 1000;
        boolean autoplay = autoplayCheckBox.isChecked();
        boolean lightboxMode = lightboxModeCheckBox.isChecked();

        Intent intent = null;
        if (v == playVideoButton) {
            intent = YouTubeStandalonePlayer.createVideoIntent(
                    this, DeveloperKey, mSelectedVideoId, startTimeMillis, autoplay, lightboxMode);
        }

        else if (v == playPlaylistButton) {
            intent = YouTubeStandalonePlayer.createPlaylistIntent(this, DeveloperKey,
                    PLAYLIST_ID, startIndex, startTimeMillis, autoplay, lightboxMode);
        }

        else if (v == playVideoListButton) {
            intent = YouTubeStandalonePlayer.createVideosIntent(this, DeveloperKey,
                    VIDEO_IDS, startIndex, startTimeMillis, autoplay, lightboxMode);
        }

        if (intent != null) {
            if (canResolveIntent(intent)) {
                startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
            }

            else {
                // Could not resolve the intent - must need to install or update the YouTube API service.
                YouTubeInitializationResult.SERVICE_MISSING
                        .getErrorDialog(this, REQ_RESOLVE_SERVICE_MISSING).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_START_STANDALONE_PLAYER && resultCode != RESULT_OK) {
            YouTubeInitializationResult errorReason =
                    YouTubeStandalonePlayer.getReturnedInitializationResult(data);
            if (errorReason.isUserRecoverableError()) {
                errorReason.getErrorDialog(this, 0).show();
            } else {
                String errorMessage =
                        String.format(getString(R.string.error_player), errorReason.toString());
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean canResolveIntent(Intent intent) {
        List<ResolveInfo> resolveInfo = getPackageManager().queryIntentActivities(intent, 0);
        return resolveInfo != null && !resolveInfo.isEmpty();
    }

    private int parseInt(String text, int defaultValue) {
        if (!TextUtils.isEmpty(text)) {
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                // fall through
            }
        }
        return defaultValue;
    }

}
