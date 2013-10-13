package org.icehat.ripplewallet;

import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.codebutler.android_websockets.WebSocketClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/** Starting activity with a log in screen. 
 *  
 *  @author Matthías Ragnarsson
 */
public class RippleWallet extends Activity
{
    private static final String TAG = "RippleWallet";
    private static final String rippleServerURI = "wss://s1.ripple.com";
    private WebSocketClient client;
    List<BasicNameValuePair> extraHeaders;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        client = new WebSocketClient(URI.create(rippleServerURI), new WebSocketClient.Listener() {
            @Override
            public void onConnect() {
                Log.d(TAG, "Connected!");
            }

            @Override
            public void onMessage(String message) {
                Log.d(TAG, String.format("Got string message! %s", message));
            }

            @Override
            public void onMessage(byte[] data) {
                Log.d(TAG, "Got binary message!");
            }

            @Override
            public void onDisconnect(int code, String reason) {
                Log.d(TAG, String.format("Disconnected! Code: %d Reason: %s", code, reason));
            }

            @Override
            public void onError(Exception error) {
                Log.e(TAG, "Error!", error);
            }
        }, extraHeaders);
        client.connect();
    }

    /** Logs a user into a wallet with an address and password
     *  specified in the activity's address and password fields.\ When
     *  that occurs, appropriate information is stored and the activity
     *  switches to wallet's balance activity.\ If the address or password 
     *  are invalid, an error message is displayed on screen.
     *
     *  Note: Method in the making, doing tests.
     */
    public void logIn(View view) {
        Log.i(TAG, "Button clicked");
        try {
            JSONObject json = new JSONObject();
            json.put("command", "subscribe");
            json.put("id", 0);
            JSONArray arr = new JSONArray();
            arr.put("ledger");
            json.put("streams", arr);
            client.send(json.toString());
        } catch(JSONException e) {
            Log.e(TAG, e.toString());
        }
    }
}
