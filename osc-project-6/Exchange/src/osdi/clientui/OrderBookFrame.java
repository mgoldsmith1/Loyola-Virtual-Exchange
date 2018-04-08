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

package osdi.clientui;

import java.awt.Dimension;

import javax.swing.*;

import osdi.tracker.FIXTracker;
import osdi.trackerui.FIXTrackerFrame;

public class OrderBookFrame extends JFrame {

	public OrderBookFrame() {
		super("Order Books Frame");
		prepareOrderBookGUI();
		pack();
		setLocationRelativeTo(null);
	}

	private void prepareOrderBookGUI() {
		
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FIXTracker fixtracker = new FIXTracker();
                fixtracker.start();
				FIXTrackerFrame fixFrame = new FIXTrackerFrame();
				JFrame frame = fixFrame;
				frame.setVisible(true);
               // JPanel panel = new JPanel();
        		//frame.setPreferredSize(new Dimension(800, 800));
        	//	getContentPane().add(frame);
            }
        });
		
		
		
		//JPanel panel = new JPanel();
		//panel.setPreferredSize(new Dimension(800, 800));
		//getContentPane().add(panel);

	}
}
