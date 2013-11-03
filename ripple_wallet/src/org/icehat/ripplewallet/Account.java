package org.icehat.ripplewallet;

import android.app.Activity;
import android.os.Bundle;

import org.json.JSONObject;

/** Keeps wallet information for the logged in session.
 *  Activities that deal with the account inherit from this class.
 *
 *  @author Matthías Ragnarsson
 *  @author Pétur Karl Ingólfsson
 *  @author Daniel Eduardo Pinedo Quintero
 *  @author Sigyn Jónsdóttir
 */
public class Account extends Activity {

    protected static JSONObject blob;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
