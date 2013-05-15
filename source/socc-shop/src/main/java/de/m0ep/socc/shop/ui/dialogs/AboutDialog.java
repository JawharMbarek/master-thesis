package de.m0ep.socc.shop.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AboutDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory
	    .getLogger(AboutDialog.class);

    private final JPanel contentPanel = new JPanel();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	try {
	    AboutDialog dialog = new AboutDialog();
	    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    dialog.setVisible(true);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * Create the dialog.
     */
    public AboutDialog() {
	setBounds(100, 100, 450, 300);
	getContentPane().setLayout(new BorderLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	contentPanel.setLayout(new BorderLayout(0, 0));
	{

	    String aboutText = "";
	    try {
		File aboutTextFile = new File(AboutDialog.class
			.getResource("/strings/about.txt").toURI());
		aboutText = FileUtils.readFileToString(aboutTextFile);
	    } catch (URISyntaxException e) {
		LOG.error("Invalid URI", e);
	    } catch (IOException e) {
		LOG.error("Failed to read 'about.txt'", e);
	    }

	    JEditorPane editorPane = new JEditorPane("text/html", aboutText);
	    editorPane.setBackground(new Color(240, 240, 240));
	    editorPane.setEditable(false);
	    editorPane.addHyperlinkListener(new HyperlinkListener() {
		@Override
		public void hyperlinkUpdate(HyperlinkEvent ev) {
		    URL url = ev.getURL();

		    if (Desktop.isDesktopSupported()) {
			try {
			    if (EventType.ACTIVATED == ev.getEventType()) {
				if ("mailto".equals(url.getProtocol())) {
				    Desktop.getDesktop().mail(url.toURI());
				} else {
				    Desktop.getDesktop().browse(url.toURI());
				}
			    }
			} catch (IOException e) {
			    LOG.error("failed to browse " + url, e);
			} catch (URISyntaxException e) {
			    LOG.error("Invalid URI", e);
			}
		    }
		}
	    });
	    contentPanel.add(editorPane);
	}
	{
	    JPanel buttonPane = new JPanel();
	    buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    getContentPane().add(buttonPane, BorderLayout.SOUTH);
	    {
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			closeDialog();
		    }
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	    }
	}
    }

    public void showDialog() {
	setModal(true);
	setModalityType(ModalityType.APPLICATION_MODAL);
	setVisible(true);
    }

    public void closeDialog() {
	setVisible(false);
	dispose();
    }
}
