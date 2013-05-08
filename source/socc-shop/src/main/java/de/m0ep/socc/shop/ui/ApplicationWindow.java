package de.m0ep.socc.shop.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileFilter;

import de.m0ep.socc.shop.SOCCShopApplication;
import de.m0ep.socc.shop.utils.ExportUtils;

public class ApplicationWindow {

    private JFrame frmSoccShop;
    private SOCCShopApplication app;

    private JDesktopPane desktopPane;

    private ConnectorsInternalFrame connectorFrame;
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
	menuBar.add(mnSocc);

	JMenuItem mntmClose = new JMenuItem("Close");
	mntmClose.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		closeWindow();
	    }
	});

	JMenuItem mntmImport = new JMenuItem("Import Data");
	mntmImport.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		importData();
	    }
	});
	mnSocc.add(mntmImport);

	JMenuItem mntmExport = new JMenuItem("Export Data");
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
	mntmClose.setIcon(new ImageIcon(ApplicationWindow.class.getResource("/images/door.png")));
	mnSocc.add(mntmClose);

	JMenu mnWindow = new JMenu("Window");
	menuBar.add(mnWindow);

	JMenuItem mntmConnectors = new JMenuItem("Connectors");
	mnWindow.add(mntmConnectors);
	mntmConnectors.setIcon(new ImageIcon(ApplicationWindow.class
		.getResource("/images/connect.png")));

	JMenuItem mntmPipes = new JMenuItem("Pipes");
	mnWindow.add(mntmPipes);
	mntmPipes.setIcon(new ImageIcon(ApplicationWindow.class
		.getResource("/images/arrow_switch.png")));

	JMenuItem mntmSioc = new JMenuItem("SIOC Viewer");
	mntmSioc.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		if (null == siocFrame) {
		    siocFrame = new SIOCInternalFrame(app);
		    siocFrame
			    .addInternalFrameListener(new InternalFrameAdapter() {
				@Override
				public void internalFrameClosed(
					InternalFrameEvent event) {
				    siocFrame = null;
				}
			    });
		    siocFrame.setVisible(true);
		    desktopPane.add(siocFrame);
		}

		desktopPane.moveToFront(siocFrame);
	    }
	});
	mntmSioc.setIcon(new ImageIcon(ApplicationWindow.class
		.getResource("/images/user_comment.png")));
	mnWindow.add(mntmSioc);

	JMenuItem mntmFoafViewer = new JMenuItem("FOAF Viewer");
	mntmFoafViewer.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (null == foafFrame) {
		    foafFrame = new FOAFInternalFrame(app);
		    foafFrame
			    .addInternalFrameListener(new InternalFrameAdapter() {
				@Override
				public void internalFrameClosed(
					InternalFrameEvent event) {
				    foafFrame = null;
				}
			    });
		    foafFrame.setVisible(true);
		    desktopPane.add(foafFrame);
		}

		desktopPane.moveToFront(foafFrame);
	    }
	});
	mntmFoafViewer.setIcon(new ImageIcon(ApplicationWindow.class
		.getResource("/images/group.png")));
	mnWindow.add(mntmFoafViewer);

	JMenu mnHelp = new JMenu("Help");
	menuBar.add(mnHelp);

	JMenuItem mntmAbout = new JMenuItem("About");
	mntmAbout.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		new AboutDialog().showDialog();
	    }
	});
	mntmAbout.setIcon(new ImageIcon(ApplicationWindow.class
		.getResource("/images/help.png")));
	mnHelp.add(mntmAbout);
	mntmConnectors.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		if (null == connectorFrame) {
		    connectorFrame = new ConnectorsInternalFrame(app);
		    connectorFrame
			    .addInternalFrameListener(new InternalFrameAdapter() {
				@Override
				public void internalFrameClosed(
					InternalFrameEvent event) {
				    connectorFrame = null;
				}
			    });
		    connectorFrame.setVisible(true);
		    desktopPane.add(connectorFrame);
		}

		desktopPane.moveToFront(connectorFrame);
	    }
	});

	desktopPane = new JDesktopPane();
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
	    }

	    zos.finish();
	    zos.close();
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
