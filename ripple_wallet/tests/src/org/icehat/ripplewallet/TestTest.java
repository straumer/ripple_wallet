package org.icehat.ripplewallet;

import junit.framework.TestCase;

/** Just for testing this testing framework itself
 *  to get a feel for it.
 */
public class TestTest extends TestCase {

    protected double fValue1;
    protected double fValue2;

    protected void setUp() {
       fValue1= 2.0;
       fValue2= 3.0;
    }

    public void testAdd() {
       double result= fValue1 + fValue2;
       assertTrue(result == 5.0);
    }
}
