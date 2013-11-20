package org.icehat.ripplewallet;

import android.test.ApplicationTestCase;
import java.util.concurrent.CountDownLatch;
import java.lang.InterruptedException;
import java.lang.Thread;
import android.net.wifi.WifiManager;
import android.content.Context;
import android.util.Log;

public class TestSharedResources extends ApplicationTestCase<SharedResources> {

    private static SharedResources resources;
    private static WifiManager wifi;

    public TestSharedResources() {
        super(SharedResources.class);
    }

    protected void setUp() {
        createApplication();
        resources = getApplication();
        wifi = (WifiManager) resources.getSystemService(Context.WIFI_SERVICE);
    }

    /** Checks whether turning the wifi on and off affects isConnected() correctly.
     *  It takes 8 extra seconds due to asynchrony. Will be like that until a better
     *  way is found.
     */
    public void testIsConnected() {

        try {
            wifi.setWifiEnabled(false);
            Thread.sleep(2000);
            assertFalse("Network operations available.", resources.isConnected());

            wifi.setWifiEnabled(true);
            Thread.sleep(6000);
            assertTrue("Network operations available.", resources.isConnected());
        }
        catch (InterruptedException e) {
            Log.e("RippleWallet", e.toString());
        }
    }
}
