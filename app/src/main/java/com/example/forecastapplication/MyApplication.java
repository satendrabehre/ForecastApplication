package com.example.forecastapplication;

import android.app.Application;
import android.content.Context;
import android.location.Location;

import androidx.room.Room;

import com.example.forecastapplication.di.AppComponent;
import com.example.forecastapplication.di.AppModule;
import com.example.forecastapplication.di.DaggerAppComponent;


public class MyApplication extends Application {

    //public static Context mContext;
    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        //initApplication();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        appComponent.inject(this);
    }
    /*private void initApplication() {
        mContext = getApplicationContext();
    }*/

    public AppComponent getAppComponent(){
        return appComponent;
    }

}
