package de.m0ep.socc.shop.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import org.ontoware.rdf2go.model.node.Resource;
import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOC;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.Thread;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.shop.SOCCShopApplication;

public class SIOCInternalFrame extends JInternalFrame {
    private static final long serialVersionUID = 1750490537282051026L;
    private static final Logger LOG = LoggerFactory
	    .getLogger(SIOCInternalFrame.class);

    private SOCCShopApplication app;
    private JList<String> listPosts;
    private DefaultListModel<String> listPostsModel;

    private JTree treeContainers;
    private DefaultMutableTreeNode treeRoot;
    private DefaultTreeModel treeContainersModel;

    ImageIcon loadingIcon = new ImageIcon(
	    SIOCInternalFrame.class.getResource("/images/imagesLoadingOLD.gif"));
    ImageIcon refreshIcon = new ImageIcon(
	    SIOCInternalFrame.class.getResource("/images/arrow_refresh.png"));
    private JButton btnUpdate;

    /**
     * Create the frame.
     */
    public SIOCInternalFrame(final SOCCShopApplication app) {
	setTitle("SIOC Viewer");
	setMinimumSize(new Dimension(250, 150));
	setFrameIcon(new ImageIcon(
		SIOCInternalFrame.class.getResource("/images/user_comment.png")));
	setResizable(true);
	setMaximizable(true);
	setIconifiable(true);
	setClosable(true);
	this.app = Preconditions.checkNotNull(app, "App can not be null.");

	setBounds(100, 100, 450, 300);

	JSplitPane splitPane = new JSplitPane();
	getContentPane().add(splitPane, BorderLayout.CENTER);

	treeRoot = new DefaultMutableTreeNode(new SIOCTreeItem(
		SIOCTreeItem.ROOT, "SIOC", null), true);
	treeContainersModel = new DefaultTreeModel(treeRoot);

	treeContainers = new JTree(treeContainersModel);
	treeContainers.addTreeSelectionListener(new TreeSelectionListener() {
	    public void valueChanged(TreeSelectionEvent e) {
		onContainerSelection(e);
	    }
	});
	treeContainers.setCellRenderer(new MyCellRenderer());
	JScrollPane scrollPane = new JScrollPane(treeContainers);
	splitPane.setLeftComponent(scrollPane);

	listPostsModel = new DefaultListModel<String>();
	listPosts = new JList<String>(listPostsModel);
	scrollPane = new JScrollPane(listPosts);
	splitPane.setRightComponent(scrollPane);

	JPanel buttonPanel = new JPanel();
	getContentPane().add(buttonPanel, BorderLayout.SOUTH);

	btnUpdate = new JButton("Update");
	btnUpdate.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		onUpdateContainers();
	    }
	});

	buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
	btnUpdate.setIcon(refreshIcon);
	buttonPanel.add(btnUpdate);

	onUpdateContainers();
    }

    protected void onUpdateContainers() {
	btnUpdate.setIcon(loadingIcon);

	java.lang.Thread t = new java.lang.Thread(new Runnable() {
	    @Override
	    public void run() {
		updateContainer();
	    }
	});

	t.start();
    }

    protected void updateContainer() {
	List<IConnector> connectors = app.getSocc().getConnectors();
	treeRoot = new DefaultMutableTreeNode(new SIOCTreeItem(
		SIOCTreeItem.ROOT, "SIOC", null), true);
	for (IConnector connector : connectors) {
	    if (!connector.isOnline()) {
		LOG.error(connector.getId() + " is not online");
		continue;
	    }

	    try {
		Site site = connector.getSite();
		LOG.debug("load site {}", site.getName());

		MutableTreeNode siteNode = new DefaultMutableTreeNode(
			new SIOCTreeItem(SIOCTreeItem.SITE, site.getName(),
				site.asResource()), true);

		List<Forum> forums = connector.getForums();
		LOG.debug("{} forums in {}", forums.size(), site.getName());

		for (Forum forum : forums) {
		    LOG.debug("load forum {}", forum.getName());
		    MutableTreeNode forumNode = new DefaultMutableTreeNode(
			    new SIOCTreeItem(SIOCTreeItem.FORUM,
				    forum.getName(), forum.asResource()), true);
		    siteNode.insert(forumNode, siteNode.getChildCount());

		    List<Thread> threads = connector.getThreads(forum);
		    LOG.debug("{} threads in {}", threads.size(), forum
			    .getName());

		    for (Thread thread : threads) {
			LOG.debug("load thread {}", thread.getName());
			MutableTreeNode threadNode = new DefaultMutableTreeNode(
				new SIOCTreeItem(SIOCTreeItem.THREAD,
					thread.getName(), thread.asResource()),
				true);
			forumNode.insert(threadNode, forumNode.getChildCount());
		    }
		}

		treeRoot.insert(siteNode, treeRoot.getChildCount());
	    } catch (ConnectorException e) {
		e.printStackTrace();
	    }
	}

	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		treeContainersModel.setRoot(treeRoot);
		treeContainers.revalidate();
		btnUpdate.setIcon(refreshIcon);
	    }
	});
    }

    protected void onContainerSelection(TreeSelectionEvent e) {
	TreePath path = e.getPath();

	if (null == path) {
	    return;
	}

	DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
		.getLastPathComponent();

	if (null != node) {
	    SIOCTreeItem item = (SIOCTreeItem) node.getUserObject();

	    if (null != item) {
		Container container = null;
		int type = item.getType();

		if (SIOCTreeItem.FORUM == type) {
		    container = Forum.getInstance(app.getSiocModel(), item
			    .getResource());
		}
		else if (SIOCTreeItem.THREAD == type) {
		    container = Thread.getInstance(app.getSiocModel(), item
			    .getResource());
		}

		if (null != container) {
		    onUpdatePost(container);
		}
	    }
	}
    }

    protected void onUpdatePost(final Container container) {
	btnUpdate.setIcon(loadingIcon);
	java.lang.Thread t = new java.lang.Thread(new Runnable() {
	    @Override
	    public void run() {
		updatePosts(container);
	    }
	});

	t.start();
    }

    protected void updatePosts(final Container container) {
	for (IConnector connector : app.getSocc().getConnectors()) {
	    if (connector.hasPosts(container)) {
		try {
		    connector.pollPosts(container);
		} catch (ConnectorException e) {
		    LOG.error("Failed to pull posts at " + connector.getId(), e);
		}
	    }
	}

	final List<Post> posts = SIOC.listAllPosts(app.getSiocModel());
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		listPostsModel.clear();
		for (Post post : posts) {
		    if (post.hasContainer(container)) {
			UserAccount user = post.getCreator();
			listPostsModel.addElement(
				user.getName() + " " +
					post.getCreated() + " " +
					post.getContent());

			LOG.debug("add post to list");
		    }
		}

		btnUpdate.setIcon(refreshIcon);
	    }
	});

    }

    public SOCCShopApplication getApp() {
	return this.app;
    }

    private static class SIOCTreeItem {
	public static final int ROOT = 0;
	public static final int SITE = 1;
	public static final int FORUM = 2;
	public static final int THREAD = 3;

	private int type;
	private String label;

	private Resource resource;

	public SIOCTreeItem(int type, String label, Resource resource) {
	    super();
	    this.type = type;
	    this.label = label;
	    this.resource = resource;
	}

	public int getType() {
	    return this.type;
	}

	public String getLabel() {
	    return this.label;
	}

	public Resource getResource() {
	    return this.resource;
	}

	@Override
	public String toString() {
	    return getLabel();
	}
    }

    private static class MyCellRenderer extends DefaultTreeCellRenderer {
	private static final long serialVersionUID = 5549244961720243028L;

	private ImageIcon siteIcon;
	private ImageIcon forumIcon;
	private ImageIcon threadIcon;

	public MyCellRenderer() {
	    super();

	    siteIcon = new ImageIcon(
		    MyCellRenderer.class.getResource("/images/site.png"));
	    forumIcon = new ImageIcon(
		    MyCellRenderer.class.getResource("/images/forum.png"));
	    threadIcon = new ImageIcon(
		    MyCellRenderer.class.getResource("/images/thread.png"));
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
		boolean selected, boolean expanded, boolean leaf, int row,
		boolean hasFocus) {

	    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
	    SIOCTreeItem item = (SIOCTreeItem) node.getUserObject();
	    int type = item.getType();

	    if (SIOCTreeItem.SITE == type) {
		setIcon(siteIcon);
	    } else if (SIOCTreeItem.FORUM == type) {
		setIcon(forumIcon);
	    } else if (SIOCTreeItem.THREAD == type) {
		setIcon(threadIcon);
	    } else if (leaf)
		setIcon(getLeafIcon());
	    else if (expanded)
		setIcon(getOpenIcon());
	    else
		setIcon(getClosedIcon());

	    setText(value.toString());
	    this.selected = selected;
	    this.hasFocus = hasFocus;
	    setHorizontalAlignment(LEFT);
	    setOpaque(false);
	    setVerticalAlignment(CENTER);
	    setEnabled(true);
	    super.setFont(UIManager.getFont("Tree.font"));

	    if (selected) {
		super.setBackground(getBackgroundSelectionColor());
		setForeground(getTextSelectionColor());

		if (hasFocus)
		    setBorderSelectionColor(UIManager.getLookAndFeelDefaults()
			    .getColor("Tree.selectionBorderColor"));
		else
		    setBorderSelectionColor(null);
	    } else {
		super.setBackground(getBackgroundNonSelectionColor());
		setForeground(getTextNonSelectionColor());
		setBorderSelectionColor(null);
	    }

	    return this;
	}
    }
}
