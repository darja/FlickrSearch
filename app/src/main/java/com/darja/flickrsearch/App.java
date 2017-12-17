package com.darja.flickrsearch;

import android.app.Application;
import com.darja.flickrsearch.util.DPLog;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Boolean debug = (getApplicationInfo().flags & 2) != 0;
        DPLog.setEnabled(debug);
    }
}
