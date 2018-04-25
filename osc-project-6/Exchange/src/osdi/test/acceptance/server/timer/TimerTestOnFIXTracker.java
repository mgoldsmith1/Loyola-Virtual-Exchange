/*******************************************************************************
 * Copyright (c) quickfixengine.org  All rights reserved.
 *
 * This file is part of the QuickFIX FIX Engine
 *
 * This file may be distributed under the terms of the quickfixengine.org
 * license as defined by quickfixengine.org and appearing in the file
 * LICENSE included in the packaging of this file.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING
 * THE WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE.
 *
 * See http://www.quickfixengine.org/LICENSE for licensing information.
 *
 * Contact ask@quickfixengine.org if any conditions of this licensing
 * are not clear to you.
 ******************************************************************************/

package osdi.test.acceptance.server.timer;

import junit.framework.TestCase;
import quickfix.ConfigError;
import quickfix.SessionNotFound;


/**
 * TimerTests Tests Client.java in osdi.client.Client
 * 
 * @param none
 * @author Matthew Goldsmith
 */
public class TimerTestOnFIXTracker extends TestCase {

    TimerTestOnFakeClientInitiator client;

    public void testAcceptorTimer() throws ConfigError, SessionNotFound, InterruptedException {
        //new TimerTestClient().run();
    	new osdi.tracker.FIXTracker().start();//.client.Client();
    }

    protected void setUp() throws Exception {
    	client = new TimerTestOnFakeClientInitiator();
    	client.run();
    	//client.
    }

    protected void tearDown() throws Exception {
    	//client.
    	//client.stop();
    }
}
