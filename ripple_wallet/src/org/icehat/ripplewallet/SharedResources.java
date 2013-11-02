package org.icehat.ripplewallet;

import android.app.Application;
import com.ripple.client.blobvault.BlobVault;

/** Keeps track of global variables and makes them accessable everywhere.
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
    onCreate() {
    }
}
