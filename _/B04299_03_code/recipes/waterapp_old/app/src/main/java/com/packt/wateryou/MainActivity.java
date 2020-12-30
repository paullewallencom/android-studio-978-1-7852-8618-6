package com.packt.wateryou;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.packt.wateryou.adapters.MainAdapter;
import com.packt.wateryou.models.Drink;

import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    private  MainAdapter mAdapter;
    private ArrayList<Drink> mDrinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mDrinks = new ArrayList<Drink>();
        Drink firstDrink = new Drink();
        firstDrink.comments = "I like water with bubbles most of the time...";
        firstDrink.dateAndTime = new Date();
        mDrinks.add(firstDrink);
        Drink secondDrink = new Drink();
        secondDrink.comments = "I also like water without bubbles. It depends on my mood I guess ;-)";
        secondDrink.dateAndTime = new Date();
        mDrinks.add(secondDrink);

        mAdapter = new MainAdapter(this, mDrinks);
        recyclerView.setAdapter(mAdapter);

        findViewById(R.id.main_button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEntry();
            }
        });


    }

    private int REQUEST_NEW_ENTRY = 1;

    private void showEntry(){
        Intent intent = new Intent(this, EntryActivity.class);
        startActivityForResult(intent, REQUEST_NEW_ENTRY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NEW_ENTRY && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Drink newDrink = new Drink();
            newDrink.comments = bundle.getString("comments");
            newDrink.imageUri = bundle.getString("uri");
            newDrink.dateAndTime = new Date();
            mDrinks.add(newDrink);
            mAdapter.notifyDataSetChanged();
        }


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
