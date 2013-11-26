package org.icehat.ripplewallet;

import android.test.ApplicationTestCase;
import java.util.concurrent.CountDownLatch;
import java.lang.InterruptedException;
import java.lang.Thread;
import android.content.Context;
import android.util.Log;

public class TestSession extends ApplicationTestCase<SharedResources> {

    private static SharedResources resources;

    public TestSharedResources() {
        super(SharedResources.class);
    }

    protected void setUp() {
        createApplication();
        resources = getApplication();
        Session session = new Session();
    }

    /** Tests whether logout functions works properly
     */
    public void testLogout() {        
    	session.logOut();
    	assertTrue(session.blob, null);
    	assertTrue(session.address, null);
    	assertTrue(session.secret, null);
    }

    public void testParseBalance(){
        
    	String msg = '{"type":
    					"response",
    					"status":"success",
    					"result":
						{	"ledger_current_index":3558755,
							"account_data":
							{	"LedgerEntryType":"AccountRoot",
								"index":"F33EDED4A6040C423B1CC53F78E3BA57A5D78B9E557283689D5A59478A22B8A1",
								"PreviousTxnID":"2BA059C4B0C1945B397118C054727A88C923A4A2E726C75EC3864E9CBE62F609",
								"Account":"r99We1NbpqL43ddt9HWpFgHDUSsnqEgZzH",
								"PreviousTxnLgrSeq":2981683,
								"OwnerCount":0,
								"Flags":0,
								"Sequence":1,
								"Balance":"63000000"
							}
						}
					}';
        String balance = session.parseBalance(msg);
        
        assertTrue(balance, "63000000");
    }
}
