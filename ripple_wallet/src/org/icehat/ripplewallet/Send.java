package org.icehat.ripplewallet;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONException;

/** Transfer / Send currency to Ripple user
 *
 *  @author Matthías Ragnarsson
 *  @author Pétur Karl Ingólfsson
 *  @author Daniel Eduardo Pinedo Quintero
 *  @author Sigyn Jónsdóttir
 */

public class Send extends Account
{
    public static String TAG;
    public static String address;
    public SharedResources resources;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        TAG = getString(R.string.log_tag);

    	// Show balance

    	// Offer autocomplete or select from list of contacts
    	 
        // Show recipient address on selected 

    	// Populate dropdown with currencies

	   	// Handle currency input and find path

	   	// Display Error messages
    }
}
