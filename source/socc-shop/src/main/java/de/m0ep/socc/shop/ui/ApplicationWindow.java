package de.m0ep.socc.shop.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

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

	JMenuItem mntmConnectors = new JMenuItem("Connectors");
	mntmConnectors.setIcon(new ImageIcon(ApplicationWindow.class
		.getResource("/images/connect.png")));
	mnSocc.add(mntmConnectors);

	JMenuItem mntmPipes = new JMenuItem("Pipes");
	mntmPipes.setIcon(new ImageIcon(ApplicationWindow.class
		.getResource("/images/arrow_switch.png")));
	mnSocc.add(mntmPipes);

	JMenuItem mntmClose = new JMenuItem("Close");
	mntmClose.setIcon(new ImageIcon(ApplicationWindow.class
		.getResource("/images/door_open.png")));
	mnSocc.add(mntmClose);

	JSplitPane splitPane = new JSplitPane();
	frmSoccShop.getContentPane().add(splitPane, BorderLayout.CENTER);

	JPanel panel = new JPanel();
	panel.setMinimumSize(new Dimension(100, 10));
	splitPane.setLeftComponent(panel);
	SpringLayout sl_panel = new SpringLayout();
	panel.setLayout(sl_panel);

	JLabel lblSites = new JLabel("Sites");
	lblSites.setHorizontalAlignment(SwingConstants.CENTER);
	sl_panel.putConstraint(SpringLayout.NORTH, lblSites, 0,
		SpringLayout.NORTH, panel);
	sl_panel.putConstraint(SpringLayout.WEST, lblSites, 0,
		SpringLayout.WEST, panel);
	sl_panel.putConstraint(SpringLayout.EAST, lblSites, 0,
		SpringLayout.EAST, panel);
	panel.add(lblSites);

	JList list = new JList();
	sl_panel.putConstraint(SpringLayout.NORTH, list, 6, SpringLayout.SOUTH,
		lblSites);
	sl_panel.putConstraint(SpringLayout.WEST, list, 0, SpringLayout.WEST,
		panel);
	sl_panel.putConstraint(SpringLayout.SOUTH, list, 0, SpringLayout.SOUTH,
		panel);
	sl_panel.putConstraint(SpringLayout.EAST, list, 0, SpringLayout.EAST,
		panel);
	panel.add(list);
	mntmConnectors.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		ConnectorPreferencesDialog dialog = new ConnectorPreferencesDialog(
			app);
		dialog.showDialog();
	    }
	});
    }
}
