package de.m0ep.socc.shop.ui.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.shop.SOCCShopApplication;
import de.m0ep.socc.shop.ui.dialogs.ConnectorDialog;
import de.m0ep.socc.shop.utils.Icons;
import de.m0ep.socc.shop.utils.UIUtils;

public class ConnectorsInternalFrame extends JInternalFrame {
    private static final long serialVersionUID = -8822621555961769296L;
    private static final Logger LOG = LoggerFactory
	    .getLogger(ConnectorsInternalFrame.class);

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

    private static class ConnectorCellItemRenderer implements
	    ListCellRenderer<ConnectorItem> {

	private static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	@Override
	public Component getListCellRendererComponent(
		JList<? extends ConnectorItem> list, ConnectorItem value,
		int index, boolean isSelected, boolean cellHasFocus) {

	    JLabel renderer = new JLabel(value.getConnector().getId());
	    renderer.setEnabled(list.isEnabled());
	    renderer.setFont(list.getFont());

	    if (isSelected) {
		renderer.setBackground(list.getSelectionBackground());
		renderer.setForeground(list.getSelectionForeground());
	    } else {
		renderer.setBackground(list.getBackground());
		renderer.setForeground(list.getForeground());
	    }

	    if (!value.getConnector().isOnline()) {
		renderer.setForeground(Color.red);
		renderer.setIcon(new ImageIcon(
			ConnectorCellItemRenderer.class
				.getResource("/images/disconnect.png")));
	    }
	    else {
		renderer.setIcon(new ImageIcon(
			ConnectorCellItemRenderer.class
				.getResource("/images/connect.png")));
	    }

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
	    renderer.setBorder(border);

	    return renderer;
	}
    }

    public static final String REMOVE_CONNECTOR_ACTION_STRING = "action_remove_connector";

    private SOCCShopApplication app;

    private JList<ConnectorItem> listConnectors;
    private DefaultListModel<ConnectorItem> listConnectorsModel;

    private JPopupMenu popupMenu;
    private JMenuItem mntmConnect;
    private JMenuItem mntmDisconnect;

    /**
     * Create the frame.
     * 
     * @param app
     */
    public ConnectorsInternalFrame(final SOCCShopApplication app) {
	setFrameIcon(Icons.CONNECT);
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

	popupMenu = createPopupMenu();

	listConnectorsModel = new DefaultListModel<ConnectorItem>();
	listConnectors = new JList<ConnectorItem>(listConnectorsModel);
	listConnectors.setCellRenderer(new ConnectorCellItemRenderer());

	// add 'backspace' and 'delete' keys to delete a selected connector
	KeyStroke backSpaceKey = KeyStroke.getKeyStroke((char) Ascii.BS);
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

	listConnectors.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mousePressed(MouseEvent e) {
		checkPopup(e);
	    }

	    @Override
	    public void mouseReleased(MouseEvent e) {
		checkPopup(e);
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

	JButton btnCreate = new JButton("Create");
	btnCreate.setToolTipText("Create a new Connector");
	btnCreate.setIcon(Icons.ADD);
	btnCreate.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		createNewConnector();
	    }
	});
	buttonPanel.add(btnCreate);

	initialize();
    }

    private JPopupMenu createPopupMenu() {
	JPopupMenu result = UIUtils.createTitledPopupMenu("Connector Popup");

	JMenuItem mntmDelete = new JMenuItem("Delete");
	mntmDelete.setIcon(Icons.CROSS);
	mntmDelete.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		removeSelectedConnector();
	    }
	});
	result.add(mntmDelete);

	mntmConnect = new JMenuItem("Connect");
	mntmConnect.setIcon(Icons.CONNECT);
	mntmConnect.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		connectSelectedConnector();
	    }
	});
	result.add(mntmConnect);

	mntmDisconnect = new JMenuItem("Disconnect");
	mntmDisconnect.setIcon(Icons.DISCONNECT);
	mntmDisconnect.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		disconnectSelectedConnector();
	    }
	});
	result.add(mntmDisconnect);

	return result;
    }

    private void initialize() {
	// Fill connector-list
	for (IConnector connector : app.getSocc().getConnectors()) {
	    listConnectorsModel.addElement(new ConnectorItem(connector));
	}
    }

    private void checkPopup(MouseEvent e) {
	if (e.isPopupTrigger()) {
	    int index = listConnectors.locationToIndex(e.getPoint());

	    if (-1 < index) {
		Rectangle cellBounds = listConnectors.getCellBounds(index,
			index);

		if (null != cellBounds && cellBounds.contains(e.getPoint())) {
		    listConnectors.setSelectedIndex(index);
		    ConnectorItem item = listConnectors.getSelectedValue();

		    if (null != item) {
			IConnector connector = item.getConnector();

			if (connector.isOnline()) {
			    mntmConnect.setVisible(false);
			    mntmConnect.setEnabled(false);
			    mntmDisconnect.setVisible(true);
			    mntmDisconnect.setEnabled(true);
			} else {
			    mntmConnect.setVisible(true);
			    mntmConnect.setEnabled(true);
			    mntmDisconnect.setVisible(false);
			    mntmDisconnect.setEnabled(false);
			}

			// update title
			Component component = popupMenu.getComponent(0);
			if (component instanceof JLabel) {
			    JLabel title = (JLabel) component;
			    title.setText(item.getConnector().getId());
			}

			popupMenu.show(listConnectors, e.getX(), e.getY());
			return;
		    }
		}
	    }
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

    protected void connectSelectedConnector() {
	int index = listConnectors.getSelectedIndex();

	if (-1 < index) {
	    ConnectorItem item = listConnectors.getSelectedValue();

	    if (null != item) {
		IConnector connector = item.getConnector();

		try {
		    connector.connect();
		} catch (ConnectorException e) {
		    LOG.error("Failed to connect " + connector.getId(), e);
		    JOptionPane.showMessageDialog(
			    this,
			    "Failed to connect " + connector.getId() + ":\n '"
				    + e.getLocalizedMessage() + "'", "Error",
			    JOptionPane.ERROR_MESSAGE);
		}
	    }
	}
    }

    protected void disconnectSelectedConnector() {
	int index = listConnectors.getSelectedIndex();

	if (-1 < index) {
	    ConnectorItem item = listConnectors.getSelectedValue();

	    if (null != item) {
		IConnector connector = item.getConnector();

		try {
		    // TODO: connector.disconnect();
		    throw new ConnectorException("nothing implemented here!");
		} catch (ConnectorException e) {
		    LOG.error("Failed to connect " + connector.getId(), e);
		    JOptionPane.showMessageDialog(
			    this,
			    "Failed to connect " + connector.getId() + ":\n '"
				    + e.getLocalizedMessage() + "'", "Error",
			    JOptionPane.ERROR_MESSAGE);
		}
	    }
	}
    }

    private void createNewConnector() {
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
