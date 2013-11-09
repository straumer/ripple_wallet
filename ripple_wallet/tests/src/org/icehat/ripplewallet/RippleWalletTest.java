package org.icehat.ripplewallet;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class org.icehat.ripplewallet.RippleWalletTest \
 * org.icehat.ripplewallet.tests/android.test.InstrumentationTestRunner
 */
public class RippleWalletTest extends ActivityInstrumentationTestCase2<RippleWallet> {

    public RippleWalletTest() {
        super("org.icehat.ripplewallet", RippleWallet.class);
    }

}
