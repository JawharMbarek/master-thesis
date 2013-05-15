package de.m0ep.socc.shop.ui.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.shop.SOCCShopApplication;
import de.m0ep.socc.shop.ui.dialogs.ConnectorDialog;

public class ConnectorsInternalFrame extends JInternalFrame {
    private static final long serialVersionUID = -8822621555961769296L;

    private static class ConnectorItem {
	private IConnector connector;

	public ConnectorItem(IConnector connector) {
	    super();
	    this.connector = connector;
	}

	public IConnector getConnector() {
	    return connector;
	}

	@Override
	public String toString() {
	    return getConnector().getId();
	}
    }

    private static class ConnectorCellItemRenderer extends JLabel implements
	    ListCellRenderer<ConnectorItem> {
	private static final long serialVersionUID = 1L;

	private static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	@Override
	public Component getListCellRendererComponent(
		JList<? extends ConnectorItem> list, ConnectorItem value,
		int index, boolean isSelected, boolean cellHasFocus) {

	    setText(value.toString());

	    if (isSelected) {
		setBackground(list.getSelectionBackground());
		setForeground(list.getSelectionForeground());
	    } else {
		setBackground(list.getBackground());
		setForeground(list.getForeground());
	    }

	    if (!value.getConnector().isOnline()) {
		setForeground(Color.red);
		setIcon(new ImageIcon(
			ConnectorCellItemRenderer.class
				.getResource("/images/disconnect.png")));
	    }
	    else {
		setIcon(new ImageIcon(
			ConnectorCellItemRenderer.class
				.getResource("/images/connect.png")));
	    }

	    setEnabled(list.isEnabled());
	    setFont(list.getFont());

	    Border border = null;
	    if (cellHasFocus) {
		if (isSelected) {
		    border = UIManager
			    .getBorder("List.focusSelectedCellHighlightBorder");
		}
		if (border == null) {
		    border = UIManager
			    .getBorder("List.focusCellHighlightBorder");
		}
	    } else {
		border = noFocusBorder;
	    }
	    setBorder(border);

	    return this;
	}
    }

    public static final String REMOVE_CONNECTOR_ACTION_STRING = "RemoveConnector";

    private SOCCShopApplication app;

    private JList<ConnectorItem> listConnectors;
    private DefaultListModel<ConnectorItem> listConnectorsModel;

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
	setClosable(true);
	setResizable(true);
	setMaximizable(true);
	setDefaultCloseOperation(HIDE_ON_CLOSE);
	this.app = Preconditions.checkNotNull(app, "App can not be null.");

	setToolTipText("Window to add and remove SOCC-Selectors");
	setTitle("Connectors");
	setBounds(100, 100, 450, 300);
	getContentPane().setLayout(new BorderLayout(0, 0));

	listConnectorsModel = new DefaultListModel<ConnectorItem>();
	listConnectors = new JList<ConnectorItem>(listConnectorsModel);
	listConnectors.setCellRenderer(new ConnectorCellItemRenderer());

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
	    listConnectorsModel.addElement(new ConnectorItem(connector));
	}
    }

    private void removeSelectedConnector() {
	int index = listConnectors.getSelectedIndex();

	if (0 <= index) {
	    IConnector connector = listConnectors.getSelectedValue()
		    .getConnector();
	    listConnectorsModel.remove(index);
	    app.getSocc().removeConnector(connector.getId());
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
		listConnectorsModel.addElement(new ConnectorItem(connector));
	    } catch (ConnectorException e) {
		JOptionPane.showMessageDialog(ConnectorsInternalFrame.this,
			e.getMessage(), "Failes to create connector",
			JOptionPane.ERROR_MESSAGE);
		return;
	    }
	}
    }
}
