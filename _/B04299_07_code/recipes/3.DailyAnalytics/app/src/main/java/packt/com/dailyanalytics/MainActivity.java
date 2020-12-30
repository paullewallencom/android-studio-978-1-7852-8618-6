package packt.com.dailyanalytics;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity  implements
        LoaderManager.LoaderCallbacks<Cursor>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLoaderManager().initLoader(0, null, this);
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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.parse("content://com.packt.dailythoughts/thoughts");

        String[] projection = new String[] { "_id", "name", "happiness"};
        String sortBy = "name";
        CursorLoader cursorLoader = new android.content.CursorLoader(this,uri, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        final StringBuilder builder = new StringBuilder();
        builder.append("I know what you are thinking of... \n\n");
        while ( (data.moveToNext())){

            String onYourMind = data.getString(1);
            builder.append("You think of "+onYourMind+". ");
            if (data.getInt(2) <= 2){
                builder.append("You are sad about this...");
            }
            if (data.getInt(2) >= 4) {
                builder.append("That makes you happy!");
            }
            builder.append("\n");
        }
        builder.append("\n Well, am I close? ;-)");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView countText = (TextView) findViewById(R.id.main_kpi_count);
                countText.setText(String.valueOf(builder.toString()));
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
