package org.icehat.ripplewallet;

import javax.websocket.ClientEndpoint;
import org.glassfish.tyrus.client.ClientManager;
import android.util.Log;

/** Handles the process of logging into wallet by talking
 *  to server.
 *
 *  @author Matthías Ragnarsson
 */
public class LogIn {

    private static final String TAG = "RippleLogIn";

    /** Prints some information to system log.\ Its purpose is
     *  for checking strings sent from server.
     */
    public void printToLog() {
       Log.i(TAG, "Testi!"); 
    }
}
