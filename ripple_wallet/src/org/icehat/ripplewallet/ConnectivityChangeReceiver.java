package org.icehat.ripplewallet;

import android.content.BroadcastReceiver;
import android.net.ConnectivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/** Detects network changes via system broadcast and takes appropriate actions.
 *
 *  @author Matthías Ragnarsson
 *  @author Pétur Karl Ingólfsson
 *  @author Daniel Eduardo Pinedo Quintero
 *  @author Sigyn Jónsdóttir
 */
public class ConnectivityChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean disconnected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
        if (!disconnected) {
            SharedResources resources = (SharedResources) context.getApplicationContext(); 
            resources.connectClient();
            Log.d("RippleWallet", "Connection is back on.");
        }
        else {
            Log.d("RippleWallet", "Connection went down.");
        }
    }
}
