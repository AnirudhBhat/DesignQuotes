package com.abhat.designquotes;

import android.app.Application;
import android.content.Context;

/**
 * Created by Anirudh on 18/4/17.
 */

public class App extends Application {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context context) {
        mContext = context;
    }
}
