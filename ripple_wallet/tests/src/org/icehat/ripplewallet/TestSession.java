package org.icehat.ripplewallet;

import android.test.ActivityInstrumentationTestCase2;
import android.content.Context;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.lang.InterruptedException;
import java.lang.Thread;
import java.lang.Exception;

import org.json.JSONException;

public class TestSession extends ActivityInstrumentationTestCase2<Session> {

    private static Session session;

    public TestSession() {
        super(Session.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        session = getActivity();
    }
}
