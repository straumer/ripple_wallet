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
    public static EditText walletName;
    public static EditText passphrase;
    public static TextView loginMessage; 

    /** Shows the log in screen, initializes the websocket client and
     *  establishes a websocket connection with the Ripple server.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        walletName = (EditText) findViewById(R.id.wallet_name);
        passphrase = (EditText) findViewById(R.id.passphrase);
        loginMessage = (TextView) findViewById(R.id.login_message);

        /*
        client = new WebSocketClient(URI.create(rippleServerURI), new WebSocketClient.Listener() {

            @Override
            public void onConnect() {
                Log.d(getString(R.string.log_tag), "Connected!");
            }

            @Override
            public void onMessage(String message) {
                Log.d(getString(R.string.log_tag), String.format("JSON received... %s", message));
                TextView login_msg = (TextView)findViewById(R.id.login_msg);
                try {
                    String balance = parseBalance(message);
                    Log.d(getString(R.string.log_tag), String.format("Balance is: %s", balance));
                    toBalance(balance);
                } catch (JSONException e) {
                    Log.e(getString(R.string.log_tag), "Parsing error.");
                    login_msg.setText("Address not found!");
                }
            }

            @Override
            public void onMessage(byte[] data) {
                Log.d(getString(R.string.log_tag), "Got binary message!");
            }

            @Override
            public void onDisconnect(int code, String reason) {
                Log.d(getString(R.string.log_tag), String.format("Disconnected! Code: %d Reason: %s", code, reason));
            }

            @Override
            public void onError(Exception error) {
                Log.e(getString(R.string.log_tag), "Error!", error);
            }
        }, extraHeaders);
        client.connect();
        */
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

    /** Shifts to Balance Activity with balance in Intent.
     *
     *  @param balance Balance of the account.
     */
    public void toBalance(String balance) {
        Intent intent = new Intent(this, Balance.class);
        intent.putExtra("address", walletName.getText().toString());
        intent.putExtra(getString(R.string.log_tag), balance);       
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
    	Log.d(getString(R.string.log_tag), "PopAddress: "+ walletName.getText().toString());
    }
    
    private class GetBlobTask extends AsyncTask<String, String, JSONObject> {
        
        @Override
        protected void onPostExecute(final JSONObject blob) {
            blobDownloadTask = null;
            if (blob == null) {
                threadSafeSetStatus("Failed to retrieve blob!");
                showOnlyLogin();
                return;
            }
            threadSafeSetStatus("Retrieved blob!");

            try {
                masterSeed = blob.getString("master_seed");
                populateContactsSpinner(blob.optJSONArray("contacts"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            client.runPrioritized(getAccount);
            setSubmitToPay();
        }

        /**
         * Thread: ui thread
         */
        @Override
        protected void onPreExecute() {
            hideAllButStatus();
        }

        /**
         * Thread: own
         */
        @Override
        protected JSONObject doInBackground(String... credentials) {
            try {
                String username = credentials[0];
                String password = credentials[1];
                return blobVault.getBlob(username, password);
            } catch (Exception e) {
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
        try {
            getAccountInfo(walletName.getText().toString());
            Log.d(getString(R.string.log_tag), "Attempting to log in to walletName: " + walletName.toString());
        } catch(JSONException e) {
            Log.e(getString(R.string.log_tag), e.toString());
            loginMessage.setText("Error: " + e.toString());
        }
    }
}
