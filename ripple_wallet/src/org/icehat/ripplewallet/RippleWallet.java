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
    public static final String TAG = "RippleWallet";
    private static final String rippleServerURI = "wss://s1.ripple.com";
    private WebSocketClient client;
    List<BasicNameValuePair> extraHeaders;
    public EditText address;
    private static final int ID_ACCOUNT_INFO = 100;

    /** Shows the log in screen, initializes the websocket client and
     *  establishes a websocket connection with the Ripple server.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        client = new WebSocketClient(URI.create(rippleServerURI), new WebSocketClient.Listener() {

            @Override
            public void onConnect() {
                Log.d(TAG, "Connected!");
            }

            @Override
            public void onMessage(String message) {
                Log.d(TAG, String.format("JSON received... %s", message));
                TextView login_msg = (TextView)findViewById(R.id.login_msg);
                try {
                    String balance = parseBalance(message);
                    Log.d(TAG, String.format("Balance is: %s", balance));
                    toBalance(balance);
                } catch (JSONException e) {
                    Log.e(TAG, "Parsing error.");
                    login_msg.setText("Address not found!");
                }
            }

            @Override
            public void onMessage(byte[] data) {
                Log.d(TAG, "Got binary message!");
            }

            @Override
            public void onDisconnect(int code, String reason) {
                Log.d(TAG, String.format("Disconnected! Code: %d Reason: %s", code, reason));
            }

            @Override
            public void onError(Exception error) {
                Log.e(TAG, "Error!", error);
            }
        }, extraHeaders);
        client.connect();
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
        intent.putExtra("address", address.getText().toString());
        intent.putExtra(TAG, balance);       
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
    public void populateAddress(View view){
    	address = (EditText) findViewById(R.id.address);
    	String dummy_address = "rLyDQiKG4j5rQUSuJvvNu5rTjtMZW96FhB";
    	address.setText(dummy_address);
    	Log.d(TAG, "PopAddress: "+ address.getText().toString());
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

        address = (EditText)findViewById(R.id.address);
        //EditText password = (EditText)findViewById(R.id.password);
        TextView login_msg = (TextView)findViewById(R.id.login_msg);

        try {
            getAccountInfo(address.getText().toString());
            Log.d(TAG, "Attempting to log in to address: " + address.toString());
        } catch(JSONException e) {
            Log.e(TAG, e.toString());
            login_msg.setText("Error: " + e.toString());
        }
    }
}
