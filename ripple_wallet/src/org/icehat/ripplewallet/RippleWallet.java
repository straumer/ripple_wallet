package org.icehat.ripplewallet;

import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.codebutler.android_websockets.WebSocketClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/** Starting activity with a log in screen.
 *
 *  @author Matthías Ragnarsson
 */
public class RippleWallet extends Activity
{
    private static final String TAG = "RippleWallet";
    private static final String rippleServerURI = "wss://s1.ripple.com";
    private WebSocketClient client;
    List<BasicNameValuePair> extraHeaders;
    private Activity activity;
    private static final int ID_ACCOUNT_INFO = 100;

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

                try {
                    String balance = parseBalance(message);
                    Log.d(TAG, String.format("Balance is: %s", balance));
                    toBalance(balance);
                } catch (JSONException e) {
                    Log.e(TAG, "Parsing error.");
                }



                /*
                JSONObject object = null;
                try {
                    object = new JSONObject(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final JSONObject jsonobject = object;

                int temp_id = 0;
                JSONObject temp_result = new JSONObject();
                try {
                    temp_id = jsonobject.getInt("id");
                    temp_result = jsonobject.getJSONObject("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final int transaction_id = temp_id;
                final JSONObject result = temp_result;
                Log.d(TAG, "Got result: " + result);
                */

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
     */
    public String parseBalance(String message) throws JSONException{
        JSONObject json = new JSONObject(message);
        return json.getJSONObject("result").getJSONObject("account_data").get("Balance").toString();
    }

    /** Shift to Balance Activity with balance in Intent.
     *
     *  @param balance Balance of the account.
     */
    public void toBalance(String balance) {
    }

    /** Sends a request for account information to server.
     *  
     *  @param address Address of account.
     */
    public void getAccountInfo(String address) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("command", "account_info");
        json.put("account", address);
        client.send(json.toString());
    }

    /** Logs a user into a wallet with an address and password
     *  specified in the activity's address and password fields.\ When
     *  that occurs, appropriate information is stored and the activity
     *  switches to wallet's balance activity.\ If the address or password
     *  are invalid, an error message is displayed on screen.
     *
     *  Note: Method in the making, doing tests.
     * @throws JSONException
     */
    public void logIn(View view) {

        // Get text fields.
        EditText address = (EditText)findViewById(R.id.address);
        EditText password = (EditText)findViewById(R.id.password);
        TextView login_msg = (TextView)findViewById(R.id.login_msg);
        
        String dummy_address = "rLyDQiKG4j5rQUSuJvvNu5rTjtMZW96FhB";

        try {
            // Build json.
            /*
            JSONObject json = new JSONObject();
            json.put("command", "subscribe");
            json.put("id", 0);
            JSONArray arr = new JSONArray();
            arr.put("ledger");
            json.put("streams", arr);
            */
            //client.send(json.toString());

            //getAccountInfo(address.getText().toString());
            Log.d(TAG, "Attempting to log in to address: " + dummy_address);
            getAccountInfo(dummy_address); // Daniel's account.

        } catch(JSONException e) {
            Log.e(TAG, e.toString());
            login_msg.setText("Error: " + e.toString());
        }
    }

}
