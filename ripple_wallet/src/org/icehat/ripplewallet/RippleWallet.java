package org.icehat.ripplewallet;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/** Starting activity with a log in screen. 
 *  
 *  @author Matthías Ragnarsson
 */
public class RippleWallet extends Activity
{
    private LogIn li = new LogIn();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
        this.li.printToLog();
    }
}
