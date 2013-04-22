package de.m0ep.socc.shop.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import de.m0ep.socc.shop.SOCCShopApplication;

public class ApplicationWindow {

    private JFrame frame;
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
	frame.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
	frame = new JFrame();
	frame.setBounds(100, 100, 450, 300);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JMenuBar menuBar = new JMenuBar();
	frame.setJMenuBar(menuBar);

	JMenu mnFile = new JMenu("File");
	menuBar.add(mnFile);

	JMenu mnEdit = new JMenu("Edit");
	menuBar.add(mnEdit);

	JMenuItem mntmProperties = new JMenuItem("Properties");
	mntmProperties.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		PropertiesDialog dialog = new PropertiesDialog(app);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	    }
	});
	mnEdit.add(mntmProperties);
    }

}
