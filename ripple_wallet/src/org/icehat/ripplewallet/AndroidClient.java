package org.icehat.ripplewallet;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.ripple.client.Client;
import com.ripple.client.ClientLogger;
import com.ripple.client.enums.Message;
import com.ripple.client.transport.impl.JavaWebSocketTransportImpl;

import org.json.JSONException;
import org.json.JSONObject;

/** A websocket client to connect to server and handle messages from/to
 *  server.
 *
 *  @author Matthías Ragnarsson
 *  @author Pétur Karl Ingólfsson
 *  @author Daniel Eduardo Pinedo Quintero
 *  @author Sigyn Jónsdóttir
 */
public class AndroidClient extends Client {

    Handler handler;
    public static String TAG = "RippleWallet";
    String account_data = "";
    String account_lines = "";
    String tx_blob = "";
    String error = "";

    public AndroidClient() {
        super(new JavaWebSocketTransportImpl());
    }

    @Override
    public void connect(final String uri) {
        HandlerThread handlerThread = new HandlerThread("android client thread") {
            @Override
            protected void onLooperPrepared() {
                handler = new Handler(getLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        AndroidClient.super.connect(uri);
                    }
                });
            }
        };
        handlerThread.start();
    }

    @Override
    public void onConnected() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                connected = true;
                Log.d("RippleWallet", "Connected to server!");
            }
        });
    }

    @Override
    public void onMessage(final JSONObject msg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                AndroidClient.super.onMessage(msg);
                Log.d(TAG, "Message arrived from server:\n" + msg);
                 try {
					handleMessage(msg);
				} catch (JSONException e) {
					Log.d(TAG, e.toString());
				}
            }
        });
    }

    public void runPrioritized(Runnable runnable) {
        handler.postAtFrontOfQueue(
                runnable
        );
    }

    public void run(Runnable runnable) {
        handler.post(runnable);
    }
    
    public void handleMessage(final JSONObject msg) throws JSONException{
    	String status = "";
    	status = msg.get("status").toString();
    	if (status.equals("success")) {
    		if (msg.getJSONObject("result").has("account_data")) {
    			account_data = msg.toString();
    		}
    		else if (msg.getJSONObject("result").has("lines")) {
    			account_lines = msg.toString();
    		}
            if(msg.get("id").equals(300)) {
                // Send money response
                String tx_blob = msg.getJSONObject("result").toString();
                Log.d(TAG, "TX_BLOB: "+tx_blob );
                JSONObject tx_blog_json = new JSONObject(tx_blob);
                Send.submitTransaction(tx_blob);   
            }
    	}
    	else {
    		error = msg.get("error").toString();
    		Log.d(TAG, error);
    	}
    }
}
