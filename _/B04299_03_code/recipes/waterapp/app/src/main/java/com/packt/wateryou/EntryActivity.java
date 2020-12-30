package com.packt.wateryou;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.Date;

import com.packt.wateryou.models.Drink;

/**
 * Created by mike on 29-05-15.
 */
public class EntryActivity extends Activity {

    private Uri mUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        findViewById(R.id.entry_image_button_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        findViewById(R.id.entry_button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEntry();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("edit_drink")) {
            Drink editableDrink = intent.getParcelableExtra("edit_drink");
            EditText editComment = (EditText)findViewById(R.id.entry_edit_text_comment);
            editComment.setText(editableDrink.comments);
            if (editableDrink.imageUri != null) {
                mUri = Uri.parse(editableDrink.imageUri);
                Bitmap bitmap = getBitmapFromUri();
                ImageView preview = (ImageView) findViewById(R.id.entry_image_view_preview);
                preview.setImageBitmap(bitmap);
            }
        }
    }

    private void submitEntry(){
        EditText editComment = (EditText)findViewById(R.id.entry_edit_text_comment);
        Intent intent = new Intent();
        intent.putExtra("comments", editComment.getText().toString());
        if (mUri != null) {
            intent.putExtra("uri", "file://" + mUri.getPath().toString());
        }
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private int REQUEST_IMAGE_CAPTURE = 1;

    private void takePicture(){
        File  filePhoto = new File(Environment.getExternalStorageDirectory(),
                String.valueOf(new Date().getTime())+"selfie.jpg");
        mUri = Uri.fromFile(filePhoto);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bitmap bitmap = getBitmapFromUri();
            ImageView preview = (ImageView)findViewById(R.id.entry_image_view_preview);
            preview.setImageBitmap(bitmap);
        }
    }

    public Bitmap getBitmapFromUri() {
        getContentResolver().notifyChange(mUri, null);
        ContentResolver cr = getContentResolver();
        Bitmap bitmap;
        try {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mUri);
            return bitmap;
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}


