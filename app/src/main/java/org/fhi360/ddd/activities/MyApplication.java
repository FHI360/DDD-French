package org.fhi360.ddd.activities;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "fr"));
    }
}
