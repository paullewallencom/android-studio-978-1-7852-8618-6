package com.packt.youtubeapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

/**
 * Created by mike on 03-07-15.
 */
public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();

        final String id = extras.getString("videoId");
        String description = extras.getString("videoDescription");
        String thumb = extras.getString("videoThumb");


        TextView tv = (TextView)findViewById(R.id.detail_text);
        ImageView iv = (ImageView)findViewById(R.id.detail_image);

        try {
            URL url = new URL(thumb);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            iv.setImageBitmap(bmp);
        }
        catch (Exception ex){

        }

        tv.setText(description);

        findViewById(R.id.detail_button_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlayVideo(id);
            }
        });
    }

    private void onPlayVideo(String videoId){

        Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
        intent.putExtra("VIDEO_ID", videoId);
        startActivity(intent);
    }


}
