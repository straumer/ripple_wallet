package org.icehat.ripplewallet;

import android.app.Application;
import android.util.Log;

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
    
    /** Called before all other processes as application starts.
     */
    @Override
    public void onCreate() {
        Log.d(getString(R.string.log_tag), "Application starting up...");
        paywardBlobVault = new BlobVault(getString(R.string.payward_blobvault));
        client = new AndroidClient();
        client.connect(getString(R.string.ripple_server));
    }
}
