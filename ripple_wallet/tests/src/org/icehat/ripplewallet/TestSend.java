package org.icehat.ripplewallet;

import android.test.ActivityInstrumentationTestCase2;
import android.content.Context;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.lang.InterruptedException;
import java.lang.Thread;
import java.lang.Exception;

import org.json.JSONException;

public class TestSend extends ActivityInstrumentationTestCase2<Send> {

    private static Send send;

    public TestSend() {
        super(Send.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        send = getActivity();
    }
    
    public void testParseBalance() {
        
        String msg = "{\"type\": \"response\",\"status\":\"success\",\"result\":{\"ledger_current_index\":3558755,\"account_data\":{\"LedgerEntryType\":\"AccountRoot\",\"index\":\"F33EDED4A6040C423B1CC53F78E3BA57A5D78B9E557283689D5A59478A22B8A1\",\"PreviousTxnID\":\"2BA059C4B0C1945B397118C054727A88C923A4A2E726C75EC3864E9CBE62F609\",\"Account\":\"r99We1NbpqL43ddt9HWpFgHDUSsnqEgZzH\",\"PreviousTxnLgrSeq\":2981683,\"OwnerCount\":0,\"Flags\":0,\"Sequence\":1,\"Balance\":\"63000000\"}}}";
        String balance = "";
        try {
            balance = send.parseBalance(msg);
        }
        catch (JSONException e) {
            Log.d("RippleWallet", e.toString());
        }
        assertTrue("Returns the right balance.", balance.equals("63000000"));
    }
}
