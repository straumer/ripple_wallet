package org.icehat.ripplewallet;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
    /** Fills the relevant fields with information from RippleWallet
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("login", false)) logIn();
        setContentView(R.layout.balance);
        
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
        
        // Dinamic display test. Still need to add the real data
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.balance, null);

        // Find the ScrollView 
        ScrollView sv = (ScrollView) v.findViewById(R.id.scrollView1);

        // Create a LinearLayout element
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        TextView tv[] = new TextView[50];
        for (int i = 0; i < 50; i++) {
                // Add text
                tv[i] = new TextView(this);
                tv[i].setText("my text " + i);
                ll.addView(tv[i]);
        }
                // Add the LinearLayout element to the ScrollView
                sv.addView(ll);
                // Display the view
                setContentView(v);
    }
}
