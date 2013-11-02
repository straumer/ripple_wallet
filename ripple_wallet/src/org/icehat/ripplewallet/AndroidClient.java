package org.icehat.ripplewallet;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.ripple.client.Client;
import com.ripple.client.ClientLogger;
import com.ripple.client.transport.impl.JavaWebSocketTransportImpl;

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
}
