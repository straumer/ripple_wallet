package org.icehat.ripplewallet;

import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.content.Context;
import android.os.AsyncTask;

import com.codebutler.android_websockets.WebSocketClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.message.BasicNameValuePair;

/** Starting activity with a log in screen.\ It contains a Websocket
 *  client that detects incoming JSON messages from the server and decides
 *  what to do with them.\ It also contains methods to extract relevant
 *  information from them and pass it on into other activities.
 *
 *  @author Matthías Ragnarsson
 *  @author Pétur Karl Ingólfsson
 *  @author Daniel Eduardo Pinedo Quintero
 *  @author Sigyn Jónsdóttir
 *  @version Thu Oct 17 16:33:37 GMT 2013
 */
public class RippleWallet extends Activity
{
    private WebSocketClient client;
    public SharedResources resources;
    public static EditText walletName;
    public static EditText passphrase;
    public static TextView loginMessage; 
    public static GetBlobTask getBlobTask;
    public static String TAG;

    /** Shows the log in screen, initializes the websocket client and
     *  establishes a websocket connection with the Ripple server.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TAG = getString(R.string.log_tag);
        resources = (SharedResources) getApplicationContext();
        walletName = (EditText) findViewById(R.id.wallet_name);
        passphrase = (EditText) findViewById(R.id.passphrase);
        loginMessage = (TextView) findViewById(R.id.login_message);
    }


    /** Extracts and returns the string inside a JSON response to account_info call.
     *
     *  @param message JSON response to account_info. 
     *  @return The amount of XRPs specified in message.
     */
    public String parseBalance(String message) throws JSONException{
        JSONObject json = new JSONObject(message);
        return json.getJSONObject("result")
                   .getJSONObject("account_data")
                   .get("Balance").toString();
    }

    /** Shifts to Balance Activity with blob in Intent.
     *
     *  @param balance Balance of the account.
     */
    public void toBalance(JSONObject blob) {
        Intent intent = new Intent(this, Balance.class);
        intent.putExtra("blob", blob.toString()); // Find a way bypass string conversion later.
        intent.putExtra("login", true);
        startActivity(intent);
    }

    /** Sends a request for account information to the server.
     *  
     *  @param address Address of account.
     */
    public void getAccountInfo(String address) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("command", "account_info");
        json.put("account", address);
        client.send(json.toString());
    }
    
    /** Populates address field with dummy address for easier demoing
     */
    public void populateAddress(View view) {
    	String dummy_address = "rLyDQiKG4j5rQUSuJvvNu5rTjtMZW96FhB";
    	walletName.setText(dummy_address);
    	Log.d(TAG, "PopAddress: "+ walletName.getText().toString());
    }
    
    /** A task to get the blob asynchronously from the blobvault.
     */
    private class GetBlobTask extends AsyncTask<String, String, JSONObject> {
        
        @Override
        protected void onPostExecute(final JSONObject blob) {
            getBlobTask = null;
            if (blob == null) {
                Log.d(TAG, "Failed blob retrieval. Returned null.");
                return;
            }
            Log.d(TAG, "Successful blob retrieval:\n" + blob.toString());
            Log.d(TAG, "Now logged in.");
            toBalance(blob);
        }

        @Override
        protected JSONObject doInBackground(String... credentials) {
            try {
                return resources.paywardBlobVault.getBlob(credentials[0], credentials[1]);
            } catch (Exception e) {
                Log.d(TAG, "Invalid wallet name or passphrase.");
                return null;
            }
        }
    }
    
    /** Logs a user into a wallet with an address and password
     *  specified in the activity's address and password fields.\ When
     *  that occurs, appropriate information is stored and the activity
     *  switches to wallet's balance activity.\ If the address or password
     *  are invalid, an error message is displayed on screen.\
     *  </p>
     *  Note: Method in the making, now used only to show account balance.
     *
     *  @throws JSONException
     */
    public void logIn(View view) {

        getBlobTask = new GetBlobTask();
        String walletName = this.walletName.getText().toString();
        String passphrase = this.passphrase.getText().toString();
        getBlobTask.execute(walletName, passphrase);
        Log.d(TAG, "Getting blob with " + "walletName: " + walletName + ", passphrase: " + passphrase);
         
        /*
        try {
            getAccountInfo(walletName.getText().toString());
            Log.d(TAG, "Attempting to log in to walletName: " + walletName.toString());
        } catch(JSONException e) {
            Log.e(TAG, e.toString());
            loginMessage.setText("Error: " + e.toString());
        }
        */
    }
}
