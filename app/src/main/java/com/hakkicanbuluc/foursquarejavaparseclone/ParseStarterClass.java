package com.hakkicanbuluc.foursquarejavaparseclone;

import android.app.Application;

import com.parse.Parse;

public class ParseStarterClass  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        Parse.initialize(new Parse.Configuration.Builder(this)
        .applicationId("8h3Pqt1BDUyMhOz4YIiLzffCwb2vTp3mpntXnNn4")
        .clientKey("2iLbFjAHSLRGnjF8yvRbFgYSjl61VnwUI1LFE4et")
        .server("https://parseapi.back4app.com/")
        .build());
    }
}
