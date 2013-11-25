package org.icehat.ripplewallet;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONException;

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
    protected static SharedResources resources;
    protected static String address;
    protected static String secret;
    protected static  String TAG;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * On selecting action bar icons
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        switch (item.getItemId()) {
        case R.id.send:
            startActivity(new Intent(this, Send.class));
            return true;
        case R.id.receive:
            startActivity(new Intent(this, Receive.class));
            return true;
        case R.id.balance:
            startActivity(new Intent(this, Balance.class));
            return true;
        case R.id.logout:
            logOut();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    /** Sets up variables for the logged in session.
     */
    protected void logIn() {
        TAG = getString(R.string.log_tag);
        resources = (SharedResources) getApplicationContext();
        try {
            this.blob = new JSONObject(getIntent().getStringExtra("blob")); 
            this.address = this.blob.getString("account_id");
            this.secret = this.blob.getString("master_seed");
            Log.d(TAG, "Now logged in. Blob stored.");
        }
        catch (JSONException e) {
            Log.d(TAG, e.toString());
        }
    }

    /** Cleans up logged in session and goes back to login screen.
     */
    private void logOut() {
        blob = null;
        address = null;
        secret = null;
        startActivity(new Intent(this, RippleWallet.class));
    }
    
    /** Sends a request for account information to the server.
     *  Note: Currently not working. Here for future implementation.
     *  
     *  @param address Address of account.
     */
    public void getAccountInfo(String address) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("command", "account_info");
        json.put("account", address);
        resources.client.sendMessage(json);
    }
    
    /** Gets IOUs balance and trust line addresses with respective limits.
     *  @param address Address of account.
     */
    public void getAccountLines(String address) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("command", "account_lines");
        json.put("account", address);
        resources.client.sendMessage(json);
    }
    
    /** Extracts and returns the string inside a JSON response to account_info call.
     *  Note: Currently not in use. Here for future implementation.
     *
     *  @param message JSON response to account_info. 
     *  @return The amount of XRPs specified in message.
     */
    public static String parseBalance(String message) throws JSONException {
        JSONObject json = new JSONObject(message);
        return json.getJSONObject("result")
                   .getJSONObject("account_data")
                   .get("Balance").toString();
    }
}
