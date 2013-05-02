package de.m0ep.socc.shop.ui;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.google.common.base.Preconditions;

import de.m0ep.socc.shop.SOCCShopApplication;

public class FOAFInternalFrame extends JInternalFrame {
    private static final long serialVersionUID = -378598547966313078L;

    private SOCCShopApplication app;
    private JSplitPane splitPane;
    private JPanel panel;
    private JList<String> list;
    private JTextField txtPersonFilter;

    /**
     * Create the frame.
     * 
     * @param app
     */
    public FOAFInternalFrame(SOCCShopApplication app) {
	setResizable(true);
	this.app = Preconditions.checkNotNull(app, "App can not be null");
	setClosable(true);
	setIconifiable(true);
	setMaximizable(true);
	setTitle("FOAF Viewer");
	setBounds(100, 100, 450, 300);
	setFrameIcon(new ImageIcon(ApplicationWindow.class
		.getResource("/images/group.png")));
	getContentPane().setLayout(new BorderLayout());

	splitPane = new JSplitPane();
	getContentPane().add(splitPane, BorderLayout.CENTER);

	panel = new JPanel();
	splitPane.setLeftComponent(panel);
	panel.setLayout(new BorderLayout(0, 0));

	list = new JList<String>();
	list.setBorder(new EmptyBorder(0, 0, 4, 0));
	panel.add(list, BorderLayout.CENTER);

	txtPersonFilter = new JTextField();
	txtPersonFilter.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyTyped(KeyEvent event) {
		updateFilter(txtPersonFilter.getText());
	    }

	});
	txtPersonFilter.setToolTipText("Filter Persons");
	txtPersonFilter.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
		null));
	panel.add(txtPersonFilter, BorderLayout.SOUTH);
	txtPersonFilter.setColumns(10);
    }

    public SOCCShopApplication getApp() {
	return app;
    }

    private void updateFilter(String text) {
	// TODO Auto-generated method stub

    }
}
