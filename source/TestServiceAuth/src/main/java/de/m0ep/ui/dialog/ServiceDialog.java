package de.m0ep.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import de.m0ep.sioc.service.auth.Direct;
import de.m0ep.sioc.service.auth.OAuth;
import de.m0ep.sioc.service.auth.WebAPI;

public class ServiceDialog extends JDialog {
    private static final long serialVersionUID = -505997071729841214L;

    private final JPanel contentPanel = new JPanel();
    private JTextField txtName;
    private JTextField txtDescription;
    private JComboBox<String> cbxAuthentication;
    private JComboBox<String> cbxOauthVersion;
    private JTextField txtEndpointURL;
    private JList credentialsList;
    private JButton btnAddCredential;

    /**
     * Create the dialog.
     */
    public ServiceDialog() {
	setBounds(100, 100, 450, 338);
	getContentPane().setLayout(new BorderLayout());
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
		FormFactory.RELATED_GAP_COLSPEC,
		FormFactory.DEFAULT_COLSPEC,
		FormFactory.RELATED_GAP_COLSPEC,
		ColumnSpec.decode("default:grow"), },
		new RowSpec[] {
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			RowSpec.decode("default:grow"),
			FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC, }));
	JLabel lblName = new JLabel("Name:");
	contentPanel.add(lblName, "2, 2, right, default");

	txtName = new JTextField();
	contentPanel.add(txtName, "4, 2, fill, default");
	txtName.setColumns(10);

	JLabel lblDescription = new JLabel("Description:");
	contentPanel.add(lblDescription, "2, 4, right, default");

	txtDescription = new JTextField();
	contentPanel.add(txtDescription, "4, 4, fill, default");
	txtDescription.setColumns(10);

	JLabel lblEndpointUrl = new JLabel("Endpoint URL:");
	contentPanel.add(lblEndpointUrl, "2, 6, right, default");

	txtEndpointURL = new JTextField();
	contentPanel.add(txtEndpointURL, "4, 6, fill, default");
	txtEndpointURL.setColumns(10);

	JLabel lblAuthentication = new JLabel("Authentication:");
	contentPanel.add(lblAuthentication, "2, 8, right, default");

	cbxAuthentication = new JComboBox<String>();
	contentPanel.add(cbxAuthentication, "4, 8, fill, default");

	final JLabel lblOauthVersion = new JLabel("OAuth Version:");
	lblOauthVersion.setEnabled(false);
	contentPanel.add(lblOauthVersion, "2, 10, right, default");

	cbxOauthVersion = new JComboBox<String>();
	cbxOauthVersion.setEnabled(false);
	cbxOauthVersion.setModel(
		new DefaultComboBoxModel<String>(
			new String[] {
				"1.0",
				"2.0" }));
	contentPanel.add(cbxOauthVersion, "4, 10, fill, default");

	cbxAuthentication.addItem("-None-");
	cbxAuthentication.addItem(Direct.class.getSimpleName());
	cbxAuthentication.addItem(OAuth.class.getSimpleName());
	cbxAuthentication.addItem(WebAPI.class.getSimpleName());
	cbxAuthentication.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		if (ItemEvent.SELECTED == e.getStateChange()) {
		    boolean isOAuth = e.getItem().equals(
			    OAuth.class.getSimpleName());

		    cbxOauthVersion.setEnabled(isOAuth);
		    lblOauthVersion.setEnabled(isOAuth);
		}
	    }
	});
	cbxOauthVersion.setSelectedIndex(0);

	JLabel lblCredentials = new JLabel("Credentials:");
	contentPanel.add(lblCredentials, "2, 12, right, top");

	credentialsList = new JList();
	contentPanel.add(credentialsList, "4, 12, fill, fill");

	btnAddCredential = new JButton("Add Credential");
	contentPanel.add(btnAddCredential, "4, 14");

	JPanel buttonPane = new JPanel();
	buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
	getContentPane().add(buttonPane, BorderLayout.SOUTH);

	JButton okButton = new JButton("OK");
	okButton.setActionCommand("OK");
	buttonPane.add(okButton);
	getRootPane().setDefaultButton(okButton);

	JButton cancelButton = new JButton("Cancel");
	cancelButton.setActionCommand("Cancel");
	buttonPane.add(cancelButton);
    }

    public void showDialog() {
	setModalityType(ModalityType.APPLICATION_MODAL);
	setModal(true);
	setVisible(true);
    }

}
