package de.m0ep.socc.shop.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import de.m0ep.socc.shop.SOCCShopApplication;

public class ApplicationWindow {

    private JFrame frmSoccShop;
    private SOCCShopApplication app;

    private ConnectorInternalFrame connectorFrame;
    private JDesktopPane desktopPane;

    /**
     * Create the application.
     * 
     * @param soccShopApplication
     */
    public ApplicationWindow(SOCCShopApplication soccShopApplication) {
	initialize();
	this.app = soccShopApplication;
    }

    public void run() {
	frmSoccShop.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
	frmSoccShop = new JFrame();
	frmSoccShop.addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent e) {
		app.save();
	    }
	});
	frmSoccShop.setTitle("SOCC Shop");
	frmSoccShop.setBounds(200, 100, 707, 549);
	frmSoccShop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JMenuBar menuBar = new JMenuBar();
	frmSoccShop.setJMenuBar(menuBar);

	JMenu mnSocc = new JMenu("SOCC");
	menuBar.add(mnSocc);

	JMenuItem mntmClose = new JMenuItem("Close");
	mntmClose.setIcon(new ImageIcon(ApplicationWindow.class
		.getResource("/images/door_open.png")));
	mnSocc.add(mntmClose);

	JMenu mnWindow = new JMenu("Window");
	menuBar.add(mnWindow);

	JMenuItem mntmConnectors = new JMenuItem("Connectors");
	mnWindow.add(mntmConnectors);
	mntmConnectors.setIcon(new ImageIcon(ApplicationWindow.class
		.getResource("/images/connect.png")));

	JMenuItem mntmPipes = new JMenuItem("Pipes");
	mnWindow.add(mntmPipes);
	mntmPipes.setIcon(new ImageIcon(ApplicationWindow.class
		.getResource("/images/arrow_switch.png")));

	JMenuItem mntmSioc = new JMenuItem("SIOC");
	mnWindow.add(mntmSioc);
	mntmConnectors.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (null == connectorFrame) {
		    connectorFrame = new ConnectorInternalFrame(app);
		    connectorFrame
			    .addInternalFrameListener(new InternalFrameAdapter() {
				@Override
				public void internalFrameClosed(
					InternalFrameEvent e) {
				    connectorFrame = null;
				}
			    });
		    connectorFrame.setVisible(true);
		    desktopPane.add(connectorFrame);
		}
	    }
	});

	desktopPane = new JDesktopPane();
	JScrollPane scrollPane = new JScrollPane(desktopPane);
	frmSoccShop.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}
