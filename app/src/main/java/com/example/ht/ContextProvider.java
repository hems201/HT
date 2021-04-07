package com.example.ht;

import android.app.Application;
import android.content.Context;

public class ContextProvider extends Application {
    // Keeps a reference of the application context
    // https://www.myandroidsolutions.com/2013/04/27/android-get-application-context/
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }

    // Returns the application context
    public static Context getContext() {
        return context;
    }
}
