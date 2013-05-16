package de.m0ep.socc.shop.ui.desktop;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

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

import org.apache.camel.ServiceStatus;
import org.apache.camel.model.RouteDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;

import de.m0ep.socc.shop.SOCCShopApplication;
import de.m0ep.socc.shop.ui.dialogs.RouteDialog;
import de.m0ep.socc.shop.utils.UIUtils;

public class RoutesInternalFrame extends JInternalFrame {

    private class RouteItemCellRenderer implements
	    ListCellRenderer<String> {

	private Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	@Override
	public Component getListCellRendererComponent(
		JList<? extends String> list, String value, int index,
		boolean isSelected, boolean cellHasFocus) {

	    JLabel renderer = new JLabel(value);
	    renderer.setEnabled(list.isEnabled());
	    renderer.setFont(list.getFont());

	    if (isSelected) {
		renderer.setBackground(list.getSelectionBackground());
		renderer.setForeground(list.getSelectionForeground());
	    } else {
		renderer.setBackground(list.getBackground());
		renderer.setForeground(list.getForeground());
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

	    ServiceStatus status = app.getCamelContext().getRouteStatus(value);

	    if (status.isStarted()) {
		renderer.setIcon(new ImageIcon(RoutesInternalFrame.class
			.getResource("/images/accept.png")));
	    } else {
		renderer.setIcon(new ImageIcon(RoutesInternalFrame.class
			.getResource("/images/stop.png")));
	    }

	    return renderer;
	}

    }

    private static final long serialVersionUID = 935536656770656646L;
    private static final Logger LOG = LoggerFactory
	    .getLogger(RoutesInternalFrame.class);
    public static final String REMOVE_ROUTE_ACTION_STRING = "action_remove_route";

    private SOCCShopApplication app;
    JList<String> listRoutes;
    private DefaultListModel<String> listRoutesModel;

    private JPopupMenu popupMenu;
    private JMenuItem mntmStart;
    private JMenuItem mntmStop;

    /**
     * Create the frame.
     */
    public RoutesInternalFrame(final SOCCShopApplication app) {
	setFrameIcon(new ImageIcon(RoutesInternalFrame.class
		.getResource("/images/arrow_switch.png")));
	setTitle("Routes");
	setResizable(true);
	setMaximizable(true);
	setIconifiable(true);
	setClosable(true);
	setBounds(100, 100, 450, 300);
	setDefaultCloseOperation(HIDE_ON_CLOSE);

	this.app = Preconditions.checkNotNull(app, "App can not be null.");

	popupMenu = createItemPopUpMenu();

	JPanel buttonPane = new JPanel();
	getContentPane().add(buttonPane, BorderLayout.SOUTH);
	buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

	JButton btnCreate = new JButton("Create");
	btnCreate.setIcon(new ImageIcon(RoutesInternalFrame.class
		.getResource("/images/add.png")));
	btnCreate.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		RouteDialog dialog = new RouteDialog();
		dialog.showDialog();
	    }
	});
	buttonPane.add(btnCreate);

	listRoutesModel = new DefaultListModel<String>();
	listRoutes = new JList<String>(listRoutesModel);
	listRoutes.setCellRenderer(new RouteItemCellRenderer());
	listRoutes.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mousePressed(MouseEvent e) {
		checkPopup(e);
	    }

	    @Override
	    public void mouseReleased(MouseEvent e) {
		checkPopup(e);
	    }
	});

	// add 'backspace' and 'delete' keys to delete a selected connector
	KeyStroke backSpaceKey = KeyStroke.getKeyStroke((char) Ascii.BS);
	KeyStroke deleteKey = KeyStroke.getKeyStroke((char) Ascii.DEL);
	listRoutes.getInputMap().put(backSpaceKey,
		REMOVE_ROUTE_ACTION_STRING);
	listRoutes.getInputMap().put(deleteKey,
		REMOVE_ROUTE_ACTION_STRING);
	listRoutes.getActionMap().put(REMOVE_ROUTE_ACTION_STRING,
		new AbstractAction() {
		    private static final long serialVersionUID = 1L;

		    @Override
		    public void actionPerformed(ActionEvent e) {
			removeSelectedRoute();
		    }
		});

	getContentPane().add(new JScrollPane(listRoutes), BorderLayout.CENTER);
	initialize();
    }

    private JPopupMenu createItemPopUpMenu() {
	JPopupMenu popupMenu = UIUtils.createTitledPopupMenu("Route Popup");

	JMenuItem mntmDelete = new JMenuItem("Delete");
	mntmDelete.setIcon(new ImageIcon(RoutesInternalFrame.class
		.getResource("/images/cross.png")));
	mntmDelete.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		removeSelectedRoute();
	    }
	});
	popupMenu.add(mntmDelete);

	mntmStart = new JMenuItem("Start");
	mntmStart.setIcon(new ImageIcon(RoutesInternalFrame.class
		.getResource("/images/accept.png")));
	mntmStart.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		startSelectedRoute();
	    }
	});
	popupMenu.add(mntmStart);

	mntmStop = new JMenuItem("Stop");
	mntmStop.setIcon(new ImageIcon(RoutesInternalFrame.class
		.getResource("/images/stop.png")));
	mntmStop.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		stopSelectedRoute();
	    }
	});
	popupMenu.add(mntmStop);

	return popupMenu;
    }

    private void checkPopup(MouseEvent e) {
	if (e.isPopupTrigger()) {
	    int index = listRoutes.locationToIndex(e.getPoint());

	    if (-1 < index) {
		Rectangle cellBounds = listRoutes.getCellBounds(index, index);

		if (null != cellBounds && cellBounds.contains(e.getPoint())) {
		    listRoutes.setSelectedIndex(index);
		    String routeId = listRoutes.getSelectedValue();

		    if (null != routeId) {
			ServiceStatus status = app.getCamelContext()
				.getRouteStatus(
					routeId);

			if (status.isStarted() || status.isStarting()) {
			    mntmStart.setVisible(false);
			} else {
			    mntmStart.setVisible(true);
			}

			if (status.isStopped() || status.isStopping()) {
			    mntmStop.setVisible(false);
			} else {
			    mntmStop.setVisible(true);
			}

			// update title
			Component component = popupMenu.getComponent(0);
			if (component instanceof JLabel) {
			    JLabel title = (JLabel) component;
			    title.setText(routeId);
			}

			popupMenu.show(listRoutes, e.getX(), e.getY());
			return;
		    }
		}
	    }
	}
    }

    protected void initialize() {
	List<RouteDefinition> routes = app.getCamelContext()
		.getRouteDefinitions();

	for (RouteDefinition routeDefinition : routes) {
	    if (null != routeDefinition.getId()) {
		listRoutesModel.addElement(routeDefinition.getId());
	    }
	}
    }

    protected void removeSelectedRoute() {
	int index = listRoutes.getSelectedIndex();
	if (-1 < index) {
	    String routeId = listRoutes.getSelectedValue();

	    int res = JOptionPane
		    .showConfirmDialog(
			    this,
			    "Do you really want to delete the route '"
				    + routeId + "'",
			    "Delete Route",
			    JOptionPane.YES_NO_OPTION);

	    if (JOptionPane.YES_OPTION == res) {
		try {
		    app.getCamelContext().stopRoute(routeId);
		    app.getCamelContext().removeRoute(routeId);
		} catch (Exception e) {
		    LOG.error("Failed to remove route from camel", e);
		    JOptionPane.showMessageDialog(this,
			    "Failed to remove route from camel:\n"
				    + e.getMessage(), "Error",
			    JOptionPane.ERROR_MESSAGE);
		}

		listRoutesModel.remove(index);
	    }
	}
    }

    protected void startSelectedRoute() {
	int index = listRoutes.getSelectedIndex();
	if (-1 < index) {
	    String routeId = listRoutes.getSelectedValue();

	    try {
		app.getCamelContext().startRoute(routeId);
	    } catch (Exception e) {
		LOG.error("Failed to start route " + routeId, e);
		JOptionPane.showMessageDialog(this, "Filed to start route:\n"
			+ e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }

    protected void stopSelectedRoute() {
	int index = listRoutes.getSelectedIndex();
	if (-1 < index) {
	    String routeId = listRoutes.getSelectedValue();

	    try {
		app.getCamelContext().stopRoute(routeId);
	    } catch (Exception e) {
		LOG.error("Failed to stop route " + routeId, e);
		JOptionPane.showMessageDialog(this, "Filed to stop route:\n"
			+ e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }
}
