package de.m0ep.socc.shop.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m0ep.socc.shop.SOCCShopApplication;
import de.m0ep.socc.shop.ui.desktop.ConnectorsInternalFrame;
import de.m0ep.socc.shop.ui.desktop.FOAFInternalFrame;
import de.m0ep.socc.shop.ui.desktop.RoutesInternalFrame;
import de.m0ep.socc.shop.ui.desktop.SIOCInternalFrame;
import de.m0ep.socc.shop.ui.dialogs.AboutDialog;
import de.m0ep.socc.shop.utils.ExportUtils;
import de.m0ep.socc.shop.utils.Icons;

public class ApplicationWindow {
    private static final Logger LOG = LoggerFactory
	    .getLogger(ApplicationWindow.class);

    private JFrame frmSoccShop;
    private SOCCShopApplication app;

    private JDesktopPane desktopPane;

    private ConnectorsInternalFrame connectorFrame;
    private RoutesInternalFrame pipesFrame;
    private SIOCInternalFrame siocFrame;
    private FOAFInternalFrame foafFrame;

    private JFileChooser fileChooser;

    /**
     * Create the application.
     * 
     * @param soccShopApplication
     */
    public ApplicationWindow(SOCCShopApplication soccShopApplication) {
	initialize();
	this.app = soccShopApplication;
    }

    public void showWindow() {
	frmSoccShop.setVisible(true);
    }

    public void closeWindow() {
	frmSoccShop.setVisible(false);
	frmSoccShop.dispose();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
	frmSoccShop = new JFrame();
	frmSoccShop.setTitle("SOCC Shop");
	frmSoccShop.setBounds(200, 100, 707, 549);
	frmSoccShop.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frmSoccShop.addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosed(WindowEvent e) {
		app.shutdown();
	    }
	});

	fileChooser = new JFileChooser();
	fileChooser.setFileFilter(new ZipFileFilter());

	JMenuBar menuBar = new JMenuBar();
	frmSoccShop.setJMenuBar(menuBar);

	JMenu mnSocc = new JMenu("SOCC");
	mnSocc.setMnemonic('s');
	menuBar.add(mnSocc);

	JMenuItem mntmConnectors = new JMenuItem("Connectors");
	mntmConnectors.setIcon(Icons.CONNECT);
	mntmConnectors.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		if (null == connectorFrame) {
		    connectorFrame = new ConnectorsInternalFrame(app);
		    desktopPane.add(connectorFrame);
		}

		connectorFrame.setVisible(true);
		desktopPane.moveToFront(connectorFrame);
	    }
	});
	mnSocc.add(mntmConnectors);

	JMenuItem mntmPipes = new JMenuItem("Pipes");
	mntmPipes.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (null == pipesFrame) {
		    pipesFrame = new RoutesInternalFrame(app);
		    desktopPane.add(pipesFrame);
		}

		pipesFrame.setVisible(true);
		desktopPane.moveToFront(pipesFrame);
	    }
	});

	mnSocc.add(mntmPipes);
	mntmPipes.setIcon(Icons.ARROW_SWITCH);

	mnSocc.add(new JSeparator());

	JMenuItem mntmImport = new JMenuItem("Import...");
	mntmImport.setEnabled(false);
	mntmImport.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		importData();
	    }
	});
	mnSocc.add(mntmImport);

	JMenuItem mntmExport = new JMenuItem("Export...");
	mntmExport.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		try {
		    exportData();
		} catch (Exception e1) {
		    JOptionPane
			    .showMessageDialog(
				    frmSoccShop,
				    "Failed to export date.\n" +
					    "View Log for more information.",
				    "Export failed!", JOptionPane.ERROR_MESSAGE);
		}
	    }
	});
	mnSocc.add(mntmExport);

	mnSocc.add(new JSeparator());

	JMenuItem mntmClose = new JMenuItem("Quit");
	mntmClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
		InputEvent.CTRL_MASK));
	mntmClose.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		closeWindow();
		app.shutdown();
	    }
	});
	mnSocc.add(mntmClose);

	JMenu mnViewer = new JMenu("Viewer");
	mnViewer.setMnemonic('v');
	menuBar.add(mnViewer);

	JMenuItem mntmSioc = new JMenuItem("SIOC Viewer");
	mntmSioc.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		if (null == siocFrame) {
		    siocFrame = new SIOCInternalFrame(app);
		    desktopPane.add(siocFrame);
		}

		siocFrame.setVisible(true);
		desktopPane.moveToFront(siocFrame);
	    }
	});
	mntmSioc.setIcon(Icons.USER_COMMENT);
	mnViewer.add(mntmSioc);

	JMenuItem mntmFoafViewer = new JMenuItem("FOAF Viewer");
	mntmFoafViewer.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (null == foafFrame) {
		    foafFrame = new FOAFInternalFrame(app);
		    desktopPane.add(foafFrame);
		}

		foafFrame.setVisible(true);
		desktopPane.moveToFront(foafFrame);
	    }
	});
	mntmFoafViewer.setIcon(Icons.GROUP);
	mnViewer.add(mntmFoafViewer);

	JMenu mnHelp = new JMenu("Help");
	mnHelp.setMnemonic('h');
	menuBar.add(mnHelp);

	JMenuItem mntmAbout = new JMenuItem("About");
	mntmAbout.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		new AboutDialog().showDialog();
	    }
	});
	mntmAbout.setIcon(Icons.HELP);
	mnHelp.add(mntmAbout);

	desktopPane = new JDesktopPane();
	desktopPane.setBackground(Color.LIGHT_GRAY);
	JScrollPane scrollPane = new JScrollPane(desktopPane);
	frmSoccShop.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    protected void exportData() throws Exception {
	int res = fileChooser.showSaveDialog(frmSoccShop);

	if (JFileChooser.APPROVE_OPTION == res) {
	    File file = fileChooser.getSelectedFile();

	    if (!file.getName().endsWith(".zip")) {
		file = new File(file.getAbsolutePath() + ".zip");
	    }

	    if (file.exists()) {
		res = JOptionPane.showConfirmDialog(
			frmSoccShop,
			"Override it?",
			file.getName() + " already exists",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE);

		if (JOptionPane.NO_OPTION == res) {
		    return;
		}

		LOG.debug("Exporting RDF data to {}", file.getAbsoluteFile());
	    }

	    FileOutputStream fos = new FileOutputStream(file);
	    ZipOutputStream zos = new ZipOutputStream(fos);
	    CRC32 crc32 = new CRC32();

	    if (null != app.getSiocModel()) {
		byte[] siocBytes = ExportUtils.getModelXML(app.getSiocModel())
			.getBytes();
		crc32.update(siocBytes);

		ZipEntry siocZip = new ZipEntry("sioc.xml");
		siocZip.setTime(new Date().getTime());
		siocZip.setSize(siocBytes.length);
		siocZip.setCrc(crc32.getValue());
		zos.putNextEntry(siocZip);
		zos.write(siocBytes);
		zos.closeEntry();

		LOG.debug("SIOC data written to zip");
	    }

	    if (null != app.getFoafModel()) {
		byte[] foafBytes = ExportUtils.getModelXML(app.getFoafModel())
			.getBytes();
		crc32.update(foafBytes);

		ZipEntry foafZip = new ZipEntry("foaf.xml");
		foafZip.setTime(new Date().getTime());
		foafZip.setSize(foafBytes.length);
		foafZip.setCrc(crc32.getValue());
		zos.putNextEntry(foafZip);
		zos.write(foafBytes);
		zos.closeEntry();

		LOG.debug("FOAF data written to zip");
	    }

	    zos.finish();
	    zos.close();

	    LOG.debug("Finished exporting rdf data");
	}
    }

    protected void importData() {
	// TODO Auto-generated method stub

    }

    private static final class ZipFileFilter extends FileFilter {
	@Override
	public String getDescription() {
	    // TODO Auto-generated method stub
	    return "*.zip";
	}

	@Override
	public boolean accept(File f) {
	    // TODO Auto-generated method stub
	    return (f.isFile() && f.getName().endsWith(".zip"))
		    || f.isDirectory();
	}
    }
}
