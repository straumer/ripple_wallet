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

public class Balance extends Activity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balance);
        String balance = getIntent().getStringExtra(RippleWallet.TAG);
        String address = getIntent().getStringExtra("address");
        TextView balanceView = (TextView) findViewById(R.id.balance_view);
        TextView accountView = (TextView) findViewById(R.id.account);
        
        double b = Double.parseDouble(balance)/1000000;
        
        balanceView.setText("Balance: " + b + " XRP");
        accountView.setText("Account: " + address);
    }
}
