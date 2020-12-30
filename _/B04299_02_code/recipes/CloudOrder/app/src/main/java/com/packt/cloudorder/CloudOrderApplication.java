package com.packt.cloudorder;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class CloudOrderApplication extends Application{

    @Override
    public void onCreate(){
        super.onCreate();
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(CloudOrder.class);
        Parse.initialize(this, "ySteXWLAa89pqoU58bgDLGfUn0EOHTtqUndgTqob", "N3MzBlcwxUMzUIneAJ7jqwzNVjN7FsRMYIq9NWs1");
    }
}
