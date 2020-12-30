package packt.com.whitelabelmessenger;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This is the main activity where all things are happening
 */
public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_button_send).setOnClickListener(this);
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

    @Override
    public void onClick(View v) {

        String phoneNumber = ((EditText) findViewById(R.id.main_edit_phone_number)).getText().toString();
        String message = getString(R.string.yeah);

        if (Constants.isTestSMS) {
            Toast.makeText(this, String.format("TEST Send %s to %s", message, phoneNumber), Toast.LENGTH_SHORT).show();
        } else {
            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.SEND_SMS},
                        REQUEST_PERMISSION_SEND_SMS);
            }
        }
    }

    private final int REQUEST_PERMISSION_SEND_SMS = 1;

    private void sendSms(){
        String phoneNumber = ((EditText) findViewById(R.id.main_edit_phone_number)).getText().toString();
        String message = getString(R.string.yeah);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, getString(R.string.yeah), null, null);
        Toast.makeText(this, String.format("Send %s to %s", getString(R.string.yeah), phoneNumber), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {

        switch (requestCode) {
            case REQUEST_PERMISSION_SEND_SMS: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSms();
                }
                else {
                    findViewById(R.id.main_edit_phone_number).setEnabled(false);
                    findViewById(R.id.main_button_send).setEnabled(false);
                    Toast.makeText(this, getString(R.string.no_sms_permission), Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
