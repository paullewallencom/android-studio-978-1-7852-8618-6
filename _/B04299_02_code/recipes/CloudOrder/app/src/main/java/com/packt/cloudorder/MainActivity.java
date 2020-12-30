package com.packt.cloudorder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("CloudOrder");
        query.findInBackground(new FindCallback<ParseObject>(){
            public void done(List<ParseObject> items, ParseException e) {

                Object result = items;
                if (e == null){
                    ArrayList<CloudOrder> orders = (ArrayList<CloudOrder>) result;
                    Log.i("TEST", String.format("%d objects found", orders.size()));
                    CloudOrderAdapter adapter = new CloudOrderAdapter(getApplicationContext(), R.layout.adapter_main, orders);
                    ListView listView = (ListView)findViewById(R.id.main_list_orders);
                    listView.setAdapter(adapter);;
                }
            }
        });

        ((ListView)findViewById(R.id.main_list_orders)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView)findViewById(R.id.main_list_orders);
                CloudOrder order = (CloudOrder)listView.getAdapter().getItem(position);
                gotoSignatureActivity(order);
            }
        });
    }

    private void gotoSignatureActivity(CloudOrder order){
        Intent intent = new Intent(this, SignatureActivity.class);
        Bundle extras = new Bundle();
        extras.putString("orderId", order.getObjectId());
        intent.putExtras(extras);
        this.startActivity(intent);
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
