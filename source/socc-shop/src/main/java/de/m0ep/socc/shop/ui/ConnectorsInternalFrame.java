package de.m0ep.socc.shop.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.shop.SOCCShopApplication;

public class ConnectorsInternalFrame extends JInternalFrame {
    private static final long serialVersionUID = -8822621555961769296L;

    public static final String REMOVE_CONNECTOR_ACTION_STRING = "RemoveConnector";

    private SOCCShopApplication app;

    private JList<String> listConnectors;
    private DefaultListModel<String> listConnectorsModel;

    /**
     * Create the frame.
     * 
     * @param app
     */
    public ConnectorsInternalFrame(final SOCCShopApplication app) {
	setFrameIcon(new ImageIcon(
		ConnectorsInternalFrame.class
			.getResource("/images/connect.png")));
	setIconifiable(true);
	setMinimumSize(new Dimension(250, 150));
	setClosable(true);
	setResizable(true);
	setMaximizable(true);
	this.app = Preconditions.checkNotNull(app, "App can not be null.");

	setToolTipText("Window to add and remove SOCC-Selectors");
	setTitle("Connectors");
	setBounds(100, 100, 253, 150);
	getContentPane().setLayout(new BorderLayout(0, 0));

	listConnectorsModel = new DefaultListModel<String>();
	listConnectors = new JList<String>(listConnectorsModel);

	// add 'backspace' and 'delete' keys to delete a selected connector
	KeyStroke backSpaceKey = KeyStroke.getKeyStroke('\b');
	KeyStroke deleteKey = KeyStroke.getKeyStroke((char) Ascii.DEL);
	listConnectors.getInputMap().put(backSpaceKey,
		REMOVE_CONNECTOR_ACTION_STRING);
	listConnectors.getInputMap().put(deleteKey,
		REMOVE_CONNECTOR_ACTION_STRING);
	listConnectors.getActionMap().put(REMOVE_CONNECTOR_ACTION_STRING,
		new AbstractAction() {
		    private static final long serialVersionUID = 1L;

		    @Override
		    public void actionPerformed(ActionEvent e) {
			removeSelectedConnector();
		    }
		});

	JScrollPane scrollPane = new JScrollPane(listConnectors);
	scrollPane
		.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	getContentPane().add(scrollPane, BorderLayout.CENTER);

	JPanel buttonPanel = new JPanel();
	FlowLayout flowLayout = (FlowLayout) buttonPanel.getLayout();
	flowLayout.setAlignment(FlowLayout.RIGHT);
	getContentPane().add(buttonPanel, BorderLayout.SOUTH);

	JButton btnAdd = new JButton("Add");
	btnAdd.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		addNewConnector();
	    }
	});
	btnAdd.setToolTipText("Add a new Connector");
	btnAdd.setIcon(new ImageIcon(ConnectorsInternalFrame.class
		.getResource("/images/add.png")));
	buttonPanel.add(btnAdd);

	JButton btnRemove = new JButton("Remove");
	btnRemove.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		removeSelectedConnector();
	    }
	});
	btnRemove.setToolTipText("Remove the selected Connector");
	btnRemove.setIcon(new ImageIcon(ConnectorsInternalFrame.class
		.getResource("/images/delete.png")));
	buttonPanel.add(btnRemove);

	initialize();
    }

    private void initialize() {
	// Fill connector-list
	for (IConnector connector : app.getSocc().getConnectors()) {
	    listConnectorsModel.addElement(connector.getId());
	}
    }

    private void removeSelectedConnector() {
	int index = listConnectors.getSelectedIndex();

	if (0 <= index) {
	    String connectorId = listConnectors.getSelectedValue();
	    listConnectorsModel.remove(index);
	    app.getSocc().removeConnector(connectorId);
	    app.save();
	}
    }

    private void addNewConnector() {
	ConnectorDialog connectorDlg = new ConnectorDialog(app);

	int status = connectorDlg.showDialog();

	if (ConnectorDialog.SAVE_OPTION == status) {
	    String factoryId = connectorDlg.getFactoryId();
	    String connectorId = connectorDlg.getConnectorId();
	    Map<String, Object> parameters = connectorDlg.getParameter();

	    try {
		IConnector connector = app.getSocc().createConnector(factoryId,
			connectorId, parameters);
		listConnectorsModel.addElement(connector.getId());
	    } catch (ConnectorException e) {
		JOptionPane.showMessageDialog(ConnectorsInternalFrame.this,
			e.getMessage(), "Failes to create connector",
			JOptionPane.ERROR_MESSAGE);
		return;
	    }
	}
    }
}
