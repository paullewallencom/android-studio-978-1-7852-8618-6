package packt.com.getitright.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.games.Games;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import packt.com.getitright.R;
import packt.com.getitright.models.Answer;
import packt.com.getitright.models.Question;
import packt.com.getitright.models.Quiz;
import packt.com.getitright.repositories.QuizRepository;

public class GooglePlayServicesActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String TAG = "GooglePlayServicesActiv";

    private static final String KEY_IN_RESOLUTION = "is_in_resolution";

    /**
     * Request code for auto Google Play Services error resolution.
     */
    private static final int REQUEST_CODE_RESOLUTION = 1;

    /**
     * Google API client.
     */
    private GoogleApiClient mGoogleApiClient;

    /**
     * Determines if the client is in a resolution state, and
     * waiting for resolution intent to return.
     */
    private boolean mIsInResolution;

    private Quiz mQuiz;
    private int mScore;
    private int mQuestionIndex=0;

    private void newGame(){

        mQuiz = new QuizRepository().getQuiz();
        mScore = 0;
        mQuestionIndex = 0;
        displayQuestion(mQuiz.getQuestions().get(mQuestionIndex));
    }

    private void displayQuestion(Question question){

        TextView questionText = (TextView)findViewById(R.id.text);

        displayImage(question);

        questionText.setText(question.getText());

        ArrayList<Answer> answers = question.getPossibleAnswers();
        if (answers == null){
            return;
        }
        setPossibleAnswer(findViewById(R.id.button_1), answers.get(0));
        setPossibleAnswer(findViewById(R.id.button_2), answers.get(1));
        setPossibleAnswer(findViewById(R.id.button_3), answers.get(2));
        setPossibleAnswer(findViewById(R.id.button_4), answers.get(3));
    }

    private void setPossibleAnswer(View v, Answer answer){
        if (v instanceof Button) {
            ((Button) v).setText(answer.getText());
            v.setTag(answer);
        }
    }

    private void displayImage(final Question question){

        new Thread(new Runnable() {
            public void run(){

                try {
                    URL url = new URL(question.getUri());
                    final Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ImageView imageView = (ImageView) findViewById(R.id.image);
                            imageView.setImageBitmap(image);
                        }
                    });

                }
                catch (Exception ex){
                    Log.d(getClass().toString(), ex.getMessage());
                }
            }
        }).start();
    }

    /**
     * Called when the activity is starting. Restores the activity state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mIsInResolution = savedInstanceState.getBoolean(KEY_IN_RESOLUTION, false);
        }

        setContentView(R.layout.activity_google_play_services);

        findViewById( R.id.button_1).setOnClickListener(this);
        findViewById(R.id.button_2).setOnClickListener(this);
        findViewById(R.id.button_3).setOnClickListener(this);
        findViewById(R.id.button_4).setOnClickListener(this);

        findViewById(R.id.button_test).setOnClickListener(this);

        newGame();
    }

    /**
     * Called when the Activity is made visible.
     * A connection to Play Services need to be initiated as
     * soon as the activity is visible. Registers {@code ConnectionCallbacks}
     * and {@code OnConnectionFailedListener} on the
     * activities itself.
     */
    @Override
    protected void onStart() {

        super.onStart();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Games.API)
                    .addScope(Games.SCOPE_GAMES)
                            // Optionally, add additional APIs and scopes if required.
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        mGoogleApiClient.connect();
    }

    /**
     * Called when activity gets invisible. Connection to Play Services needs to
     * be disconnected as soon as an activity is invisible.
     */
    @Override
    protected void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    /**
     * Saves the resolution state.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IN_RESOLUTION, mIsInResolution);
    }

    /**
     * Handles Google Play Services resolution callbacks.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_RESOLUTION:
                retryConnecting();
                break;
        }
    }

    private void retryConnecting() {
        mIsInResolution = false;
        if (!mGoogleApiClient.isConnecting()) {
            mGoogleApiClient.connect();
        }
    }

    /**
     * Called when {@code mGoogleApiClient} is connected.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "GoogleApiClient connected");
        // TODO: Start making API requests.
    }

    /**
     * Called when {@code mGoogleApiClient} connection is suspended.
     */
    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "GoogleApiClient connection suspended");
        retryConnecting();
    }

    /**
     * Called when {@code mGoogleApiClient} is trying to connect but failed.
     * Handle {@code result.getResolution()} if there is a resolution
     * available.
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {

        Log.i(TAG, "GoogleApiClient connection failed: " + result.toString());
        if (!result.hasResolution()) {
            // Show a localized error dialog.
            GooglePlayServicesUtil.getErrorDialog(
                    result.getErrorCode(), this, 0, new OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            retryConnecting();
                        }
                    }).show();
            return;
        }
        // If there is an existing resolution error being displayed or a resolution
        // activity has started before, do nothing and wait for resolution
        // progress to be completed.
        if (mIsInResolution) {
            return;
        }
        mIsInResolution = true;
        try {
            result.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
        } catch (SendIntentException e) {
            Log.e(TAG, "Exception while starting resolution activity", e);
            retryConnecting();
        }
    }

    private int REQUEST_LEADERBOARD = 1;
    private String LEADERBOARD_ID = "CgkIovCGjpgdEAIQAQ";

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_1:
            case R.id.button_2:
            case R.id.button_3:
            case R.id.button_4:
                checkAnswer(v);
                break;

            case R.id.button_test:

                // show leader board
                startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                        LEADERBOARD_ID), REQUEST_LEADERBOARD);
                break;
        }
    }

    private void checkAnswer(@NonNull View v){

        if (v instanceof Button){
            Answer answer = (Answer) v.getTag();

            if (mQuiz.getQuestions().get(mQuestionIndex).getCorrectAnswer().equalsIgnoreCase(answer.getId())){
                onGoodAnswer();
            }
            else{
                onWrongAnswer();
            }
        }
    }

    private void onWrongAnswer(){

        Toast.makeText(this, getString(R.string.incorrect_answer), Toast.LENGTH_SHORT).show();

        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                LEADERBOARD_ID), REQUEST_LEADERBOARD);
    }

    private void onGoodAnswer(){
        mScore+= 1000;
        Games.Leaderboards.submitScore(mGoogleApiClient, LEADERBOARD_ID, mScore);

        Toast.makeText(this, getString(R.string.correct_answer), Toast.LENGTH_SHORT).show();

        mQuestionIndex++;
        if (mQuestionIndex < mQuiz.getQuestions().size()){
            displayQuestion(mQuiz.getQuestions().get(mQuestionIndex));
        }
        else{
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                    LEADERBOARD_ID), REQUEST_LEADERBOARD);
        }
    }


}
