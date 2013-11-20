package org.icehat.ripplewallet;

import android.app.Application;
import android.util.Log;
import android.net.ConnectivityManager;
import android.content.Context;
import android.net.NetworkInfo;

import com.ripple.client.blobvault.BlobVault;

/** Keeps track of global variables and makes them accessible everywhere.
 *  
 *  @author Matthías Ragnarsson
 *  @author Pétur Karl Ingólfsson
 *  @author Daniel Eduardo Pinedo Quintero
 *  @author Sigyn Jónsdóttir
 */
public class SharedResources extends Application {

    public static AndroidClient client;
    public static BlobVault paywardBlobVault;
    public static ConnectivityManager connectivityManager;
    private static Context context;
    
    /** Called before all other processes as application starts.
     */
    @Override
    public void onCreate() {
        Log.d(getString(R.string.log_tag), "Application starting up...");
        context = getApplicationContext();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        paywardBlobVault = new BlobVault(getString(R.string.payward_blobvault));
        client = new AndroidClient();
        if (networkUp()) connectClient();
    }

    /** Determine whether network connection and websocket client are up.
     *
     *  @return True if network actions are executable. 
     */
    public boolean isConnected() {
        return client.connected && networkUp();
    }

    /** Determine whether network connection is up.
     * 
     *  @return True if network connection of device is up.
     */
    public boolean networkUp() {
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean networkUp = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return networkUp;
    }

    /** Connects client to server.
     */
    public void connectClient() {
        client.connect(getString(R.string.ripple_server));
    }
}
