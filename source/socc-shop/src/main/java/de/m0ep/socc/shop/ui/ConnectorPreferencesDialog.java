package de.m0ep.socc.shop.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import org.mortbay.log.Log;

import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.IConnectorFactory;
import de.m0ep.socc.shop.SOCCShopApplication;

public class ConnectorPreferencesDialog extends JDialog {
    private static final long serialVersionUID = -7285505723062512596L;
    private final JPanel contentPanel = new JPanel();

    private SOCCShopApplication app;

    private JList<ConnectorItem> listConnectorList;
    private DefaultListModel<ConnectorItem> connectorListModel;
    private JScrollPane scrollPane;

    /**
     * Create the dialog.
     */
    public ConnectorPreferencesDialog(final SOCCShopApplication app) {
	setTitle("Connector Preferences");
	this.app = Preconditions.checkNotNull(app, "App can not be null");

	setBounds(100, 100, 450, 300);
	getContentPane().setLayout(new BorderLayout());
	contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	SpringLayout sl_contentPanel = new SpringLayout();
	contentPanel.setLayout(sl_contentPanel);

	{
	    listConnectorList = new JList<ConnectorItem>();
	    connectorListModel = new DefaultListModel<ConnectorItem>();
	    listConnectorList.setModel(connectorListModel);
	    listConnectorList.setVisibleRowCount(10);
	    listConnectorList
		    .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    String delAction = "deleteItems";
	    KeyStroke backSpaceKey = KeyStroke.getKeyStroke('\b');
	    KeyStroke deleteKey = KeyStroke.getKeyStroke((char) Ascii.DEL);
	    listConnectorList.getInputMap().put(backSpaceKey, delAction);
	    listConnectorList.getInputMap().put(deleteKey, delAction);
	    listConnectorList.getActionMap().put(delAction,
		    new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
			    deleteSelectedListItem();
			}
		    });

	    scrollPane = new JScrollPane(listConnectorList);
	    scrollPane
		    .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    sl_contentPanel.putConstraint(SpringLayout.NORTH, scrollPane, 5,
		    SpringLayout.NORTH, contentPanel);
	    sl_contentPanel.putConstraint(SpringLayout.WEST, scrollPane, 5,
		    SpringLayout.WEST, contentPanel);
	    sl_contentPanel.putConstraint(SpringLayout.EAST, scrollPane, -5,
		    SpringLayout.EAST, contentPanel);

	    contentPanel.add(scrollPane);
	}

	JPanel panel = new JPanel();
	sl_contentPanel.putConstraint(SpringLayout.SOUTH, scrollPane, -5,
		SpringLayout.NORTH, panel);
	sl_contentPanel.putConstraint(SpringLayout.WEST, panel, 5,
		SpringLayout.WEST, contentPanel);
	sl_contentPanel.putConstraint(SpringLayout.SOUTH, panel, -10,
		SpringLayout.SOUTH, contentPanel);
	contentPanel.add(panel);

	JButton btnAdd = new JButton("Add");
	btnAdd.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		ConnectorDialog dlg = new ConnectorDialog(app);

		if (ConnectorDialog.SAVE_OPTION == dlg.showDialog()) {
		    IConnectorFactory factory = dlg.getFactory();
		    IConnector connector = dlg.getConnector();

		    connectorListModel.addElement(new ConnectorItem(factory,
			    connector));
		}
	    }
	});
	btnAdd.setSize(new Dimension(16, 16));
	btnAdd.setIcon(new ImageIcon(ConnectorPreferencesDialog.class
		.getResource("/images/add.png")));
	btnAdd.setBorder(BorderFactory.createEmptyBorder());
	panel.add(btnAdd);

	JButton btnRemove = new JButton("Remove");
	btnRemove.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		deleteSelectedListItem();
	    }
	});
	btnRemove.setBorder(new EmptyBorder(0, 0, 0, 0));
	btnRemove.setIcon(new ImageIcon(ConnectorPreferencesDialog.class
		.getResource("/images/delete.png")));
	panel.add(btnRemove);
	{
	    JPanel buttonPane = new JPanel();
	    buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    getContentPane().add(buttonPane, BorderLayout.SOUTH);
	    {
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	    }
	    {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			setVisible(false);
			dispose();
		    }
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	    }
	}

	initalize();
    }

    private void initalize() {
	// Fill connector-list
	for (IConnector connector : app.getSocc().getConnectors()) {
	    IConnectorFactory factory = app.getSocc().getFactoryOfConnector(
		    connector.getId());
	    connectorListModel
		    .addElement(new ConnectorItem(factory, connector));
	}
    }

    public void showDialog() {
	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	setVisible(true);
    }

    /**
     * 
     */
    private void deleteSelectedListItem() {
	int index = listConnectorList.getSelectedIndex();

	if (0 <= index) {
	    Log.debug("delete " + connectorListModel.get(index));
	    connectorListModel.remove(index);
	}
    }

    private static class ConnectorItem {
	private IConnectorFactory factory;
	private IConnector connector;

	public ConnectorItem(final IConnectorFactory factory,
		final IConnector connector) {
	    this.factory = Preconditions.checkNotNull(factory,
		    "Factory can not be null");
	    this.connector = Preconditions.checkNotNull(connector,
		    "Connector can not be null.");
	}

	public IConnectorFactory getFactory() {
	    return this.factory;
	}

	public IConnector getConnector() {
	    return this.connector;
	}

	@Override
	public String toString() {
	    return connector.getId();
	}
    }
}
