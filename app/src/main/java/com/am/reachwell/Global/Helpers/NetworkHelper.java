package com.am.reachwell.Global.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.am.reachwell.Global.Depedencies.ActivityContext;
import com.am.reachwell.Global.Depedencies.ApplicationContext;

import javax.inject.Inject;

/**
 * Created by udaypatil on 20/02/18.
 */

public class NetworkHelper {
    Context context;

    @Inject
    public NetworkHelper(@ApplicationContext Context context) {
        this.context = context;
    }

    public boolean checkNetwork() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        boolean isConnected = info != null && info.isConnectedOrConnecting();
        return isConnected;
    }
}
