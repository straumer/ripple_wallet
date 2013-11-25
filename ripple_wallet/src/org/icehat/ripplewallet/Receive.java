package org.icehat.ripplewallet;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.JSONException;

/** Transfer / Send currency to Ripple user
 *
 *  @author Matthías Ragnarsson
 *  @author Pétur Karl Ingólfsson
 *  @author Daniel Eduardo Pinedo Quintero
 *  @author Sigyn Jónsdóttir
 */

public class Receive extends Account
{
    private static TextView addressView; 

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive);
        addressView = (TextView) findViewById(R.id.address);
        addressView.setText("Address: " + address);
    }
}
