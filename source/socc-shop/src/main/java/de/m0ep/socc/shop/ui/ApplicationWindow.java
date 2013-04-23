package de.m0ep.socc.shop.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import de.m0ep.socc.shop.SOCCShopApplication;

public class ApplicationWindow {

    private JFrame frmSoccShop;
    private SOCCShopApplication app;

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
	frmSoccShop.setTitle("SOCC Shop");
	frmSoccShop.setBounds(200, 100, 450, 300);
	frmSoccShop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JMenuBar menuBar = new JMenuBar();
	frmSoccShop.setJMenuBar(menuBar);

	JMenu mnFile = new JMenu("SOCC");
	menuBar.add(mnFile);

	JMenuItem mntmProperties = new JMenuItem("Preferences");
	mntmProperties.setIcon(new ImageIcon(ApplicationWindow.class
		.getResource("/images/cog.png")));
	mnFile.add(mntmProperties);

	JMenuItem mntmClose = new JMenuItem("Close");
	mntmClose.setIcon(new ImageIcon(ApplicationWindow.class
		.getResource("/images/door_open.png")));
	mnFile.add(mntmClose);
	mntmProperties.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		PreferencesDialog dialog = new PreferencesDialog(app);
		dialog.showDialog();
	    }
	});
    }

}
