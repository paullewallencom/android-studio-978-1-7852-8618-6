package com.packt.cloudorder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.packt.cloudorder.widget.SignatureView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 10-05-15.
 */
public class SignatureActivity  extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        findViewById(R.id.signature_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSignature();
            }
        });


    }

    private void sendSignature() {
        final Activity activity = this;
        Bundle extras = getIntent().getExtras();
        final String orderId = extras.getString("orderId");
        SignatureView signatureView = (SignatureView)findViewById(R.id.signature_view);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        signatureView.getSignatureBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();
        final ParseFile file = new ParseFile("signature.jpg", data);

        file.saveInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    ParseObject order = ParseObject.createWithoutData("CloudOrder",orderId);
                    order.put("signature", file);
                    order.put("status", 10);

                    order.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e==null){
                                Toast.makeText(activity, "Signature has been sent!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
