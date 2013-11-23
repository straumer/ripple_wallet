package org.icehat.ripplewallet;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONException;

/** Shows the account and the XRP balance RippleWallet passed it in
 *  Intent.
 *
 *  @author Matthías Ragnarsson
 *  @author Pétur Karl Ingólfsson
 *  @author Daniel Eduardo Pinedo Quintero
 *  @author Sigyn Jónsdóttir
 *  @version Thu Oct 17 16:54:38 GMT 2013
 */
public class Balance extends Account
{
    public static String TAG;
    public static String address;

    /** Fills the relevant fields with information from RippleWallet
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balance);
        TAG = getString(R.string.log_tag);

        if (getIntent().getBooleanExtra("login", false)) {
            try {
                super.blob = new JSONObject(getIntent().getStringExtra("blob")); 
                Log.d(TAG, "Now logged in. Blob stored.");
            }
            catch (JSONException e) {
                Log.d(TAG, e.toString());
            }
        }

        // Gets address from blob
        try {
			address = blob.getString("account_id");
		}
        catch (JSONException e) {
			Log.d(TAG, e.toString());
		}
        
        // Use the address to get XRP and IOUs balances
        try {
            this.getAccountInfo(address);
            this.getAccountLines(address);
        }
        catch (JSONException e) {
            Log.d(TAG, e.toString());
        }
        
        // Wait until the messages arrive
        while (resources.client.account_lines.equals("") ||
        		resources.client.account_data.equals("")) {
        	if (!resources.client.error.equals("")) break;
        }
        //Log.d(TAG, resources.client.account_data);
        
        /* Stuff for reference
        String balance = getIntent().getStringExtra(getString(R.string.log_tag));
        String address = getIntent().getStringExtra("address");*/
        
        String balance = "0";
		try {
			balance = parseBalance(resources.client.account_data.toString());
		} catch (JSONException e) {
			Log.d(TAG, e.toString());
		}
        TextView balanceView = (TextView) findViewById(R.id.balance_view);
        TextView accountView = (TextView) findViewById(R.id.account);
        
        double b = Double.parseDouble(balance)/1000000;
        
        balanceView.setText("Balance: " + b + " XRP");
        accountView.setText("Account: " + address);
        
        // TODO: Parsing account_lines and display them dynamicaly
    }
}
