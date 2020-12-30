package packt.com.thebad;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ArrayList<BadMovie> mBadMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBadMovies = new ArrayList<BadMovie>();

        for (int iRepeat=0;iRepeat<=20000;iRepeat++) {
            mBadMovies.add(new BadMovie("Popstar", "Comedy", "2000", "Paulo Segio de Almeida", "Xuxa Meneghel, Luighi Baricelli", "https://coversblog.files.wordpress.com/2009/03/xuxa-popstar.jpg"));
            mBadMovies.add(new BadMovie("Bimbos in Time", "Comedy", "1993", "Todd Sheets", "Jenny Admire, Deric Bernier", "http://i.ytimg.com/vi/bCHdQ1MB1D4/maxresdefault.jpg"));
            mBadMovies.add(new BadMovie("Chocolat", "Comedy", "2013", "Unknown", "Blue Cheng-Lung Lan, Masami Nagasawa", "http://i.ytimg.com/vi/EPlbiYD1MmM/maxresdefault.jpg"));
            mBadMovies.add(new BadMovie("La boda o la vida", "1974", "year", "Rafael Romero Marchent", "Manola Codeso, La Polaca", "http://monedasycolecciones.com/10655-thickbox_default/la-boda-o-la-vida.jpg"));
            mBadMovies.add(new BadMovie("Spudnuts", "Comedy", "2005", "Eric Hurt", "Brian Ashworth, Dave Brown, Mendy St. Ours", "http://lipponhomes.com/wp-content/uploads/2014/03/DSCN0461.jpg"));
        }
        //source: www.imdb.com


        MainAdapter adapter = new MainAdapter(this, R.layout.adapter, mBadMovies);
        ((ListView)findViewById(R.id.main_list)).setAdapter(adapter);
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

