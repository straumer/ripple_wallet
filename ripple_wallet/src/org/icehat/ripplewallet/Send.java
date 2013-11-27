package org.icehat.ripplewallet;

import java.util.ArrayList;
import java.lang.Exception;
import java.lang.RuntimeException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import com.ripple.core.types.AccountID;
import com.ripple.core.types.Amount;
import com.ripple.client.transactions.Transaction;
import com.ripple.client.transactions.TransactionMessage.TransactionResult;
import com.ripple.client.Response;

/** Transfer / Send currency to Ripple user
 *
 *  @author Matthías Ragnarsson
 *  @author Pétur Karl Ingólfsson
 *  @author Daniel Eduardo Pinedo Quintero
 *  @author Sigyn Jónsdóttir
 */
public class Send extends Session
{
    private static final int ID_SIGN = 300;
    private static final int ID_SUBMIT = 301;
    private static EditText toAddress;
    private static EditText toValue;
    private static TextView transactionStatus;

    /* Populate spinner TODO
    Spinner contacts;
    ArrayAdapter<String> contactsAdapter;
    ArrayList<String> contactsAddresses = new ArrayList<String>(); // parrallel array
    */

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send);
        toAddress = (EditText) findViewById(R.id.to_address);
        toValue = (EditText) findViewById(R.id.to_value);
        transactionStatus = (TextView) findViewById(R.id.transaction_result);
        // Offer autocomplete or select from list of contacts
         
        // Show recipient address on selected 

        // Populate dropdown with currencies

    }

    /** Sends a given amount of a given currency to the given address.
     * @throws JSONException 
     */
    public void send(View v) throws JSONException {
    	
        JSONObject tx = new JSONObject();
        tx.put("TransactionType", "Payment");
        tx.put("Account", address);
        tx.put("Amount", Integer.parseInt(toValue.getText().toString())*1000000);
        tx.put("Destination", toAddress.getText().toString());
        
        JSONObject json = new JSONObject();
        json.put("id", ID_SIGN);
        json.put("command", "sign");
        json.put("secret", secret);
        json.put("tx_json", tx);
        
        resources.client.sendMessage(json);
    }
    
    /** Takes the final step in actually sending the money.
     */ 
    public static void submitTransaction(String tx_blob) throws JSONException {

        JSONObject json = new JSONObject();
        json.put("id", ID_SUBMIT);
        json.put("command", "submit");
        json.put("tx_blob", tx_blob);
        
        resources.client.sendMessage(json);
    }

    /** Supposed to display a message saying transaction is succesful.
     *
     * Note: Not in use due to thread issues.
     */
    public static void setSuccessStatus(String amount) {
        transactionStatus.setText("Successfully sent " + amount + "drops.");    
    }

    /** For demoing.
     */
    public void insertDemo(View v) {
        toAddress.setText("ramdQDAaNjZM8ZvpTTZpccK4CVh9N81mgF");
        toValue.setText("1");
    }
}
