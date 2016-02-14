package com.peliks.bucketdrops;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Felix on 2/14/2016.
 */
public class AppBucketDrops extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }
}
