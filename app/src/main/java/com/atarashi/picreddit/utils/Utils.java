package com.atarashi.picreddit.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by avnee on 4/2/2017.
 */

public class Utils {
        public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
