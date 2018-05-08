package com.support.johnpig.healthmanager;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    static final String NAME = "AppDatabase";
    static final int VERSION = 1;
}
