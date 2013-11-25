package org.icehat.ripplewallet;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.util.Log;

import org.json.JSONArray;
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
    public static String from_address;

    protected static JSONObject blob;
    public SharedResources resources;
    private static final int ID_SIGN = 300;

    /* Populate spinner TODO
    Spinner contacts;
    ArrayAdapter<String> contactsAdapter;
    ArrayList<String> contactsAddresses = new ArrayList<String>(); // parrallel array
    */

    public static EditText to_address;
    public static EditText to_value;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.send);
        TAG = getString(R.string.log_tag);


        // Gets address from blob
        try {
            from_address = this.blob.getString("account_id");
        }
        catch (JSONException e) {
            Log.d(TAG, e.toString());
        }
        // Offer autocomplete or select from list of contacts
         
        // Show recipient address on selected 

        // Populate dropdown with currencies

    }

    // to be finished
    public void sendCurrency() throws NumberFormatException, JSONException{

        to_address = (EditText) findViewById(R.id.to_address);
        to_value = (EditText) findViewById(R.id.to_value);
        // get currency
        //submitTransaction(from_address, "", Integer.parseInt(to_value.getText().toString()), to_address.getText().toString());
    }

    /* Simply send the json way 
    public void submitTransaction(String from_address, String secret, int amount, String to_address) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", ID_SIGN);
        json.put("command", "sign");
        json.put("secret", secret);
        
        JSONObject tx = new JSONObject();
        tx.put("TransactionType", "Payment");
        tx.put("Account", from_address);
        tx.put("Amount", amount);
        tx.put("Destination", to_address);
        
        json.put("tx_json", tx);
        
        resources.client.sendMessage(json);
    }
  */  
}
