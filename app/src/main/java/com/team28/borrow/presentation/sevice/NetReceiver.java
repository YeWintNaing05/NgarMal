package com.team28.borrow.presentation.sevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            if (info.isConnected()) {
                //start service

                Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
            } else {
                //stop service
                Toast.makeText(context, "Stop", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
