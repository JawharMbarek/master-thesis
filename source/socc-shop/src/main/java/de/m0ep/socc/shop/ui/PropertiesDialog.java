package de.m0ep.socc.shop.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.common.base.Preconditions;

import de.m0ep.socc.shop.SOCCShopApplication;

public class PropertiesDialog extends JDialog {
    private static final long serialVersionUID = -7285505723062512596L;
    private final JPanel contentPanel = new JPanel();

    SOCCShopApplication app;

    /**
     * Create the dialog.
     */
    public PropertiesDialog(final SOCCShopApplication app) {
	this.app = Preconditions.checkNotNull(app, "App can not be null");

	setBounds(100, 100, 450, 300);
	getContentPane().setLayout(new BorderLayout());
	contentPanel.setLayout(new FlowLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	{
	    JButton btnCreateConnector = new JButton("Create Connector");
	    btnCreateConnector.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    CreateConnectorDialog ccd = new CreateConnectorDialog(app);
		    int res = ccd.showDialog();
		    JOptionPane.showMessageDialog(null, "retured " + res);
		}
	    });
	    contentPanel.add(btnCreateConnector);
	}
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
    }

}
