
package de.m0ep.socc.workbench.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class ConnectToDialogView extends JDialog {
    private static final long serialVersionUID = 1L;

    public static final String CMD_OK = "ok";
    public static final String CMD_CANCEL = "cancel";

    private final JPanel contentPanel = new JPanel();

    private JButton okButton;
    private JButton cancelButton;
    private JTextField txtServerUri;
    private JTextField txtRespositoryid;
    private JTextField txtUsername;
    private JTextField txtPassword;
    private JCheckBox chckbxAnonymousLogin;

    /**
     * Create the dialog.
     */
    public ConnectToDialogView() {
        setTitle("Connect to ...");
        setBounds(100, 100, 450, 215);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("default:grow"),
        },
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
                }));

        JLabel lblServerUri = new JLabel("Server Uri:");
        contentPanel.add(lblServerUri, "2, 2, right, default");

        txtServerUri = new JTextField();
        contentPanel.add(txtServerUri, "4, 2, fill, default");
        txtServerUri.setColumns(10);

        JLabel lblRepositoryId = new JLabel("Repository Id:");
        contentPanel.add(lblRepositoryId, "2, 4, right, default");

        txtRespositoryid = new JTextField();
        contentPanel.add(txtRespositoryid, "4, 4, fill, default");
        txtRespositoryid.setColumns(10);

        chckbxAnonymousLogin = new JCheckBox("Is anonymous login");
        contentPanel.add(chckbxAnonymousLogin, "4, 6");

        JLabel lblUsername = new JLabel("Username:");
        contentPanel.add(lblUsername, "2, 8, right, default");

        txtUsername = new JTextField();
        contentPanel.add(txtUsername, "4, 8, fill, default");
        txtUsername.setColumns(10);

        JLabel lblPassword = new JLabel("Password:");
        contentPanel.add(lblPassword, "2, 10, right, default");

        txtPassword = new JTextField();
        contentPanel.add(txtPassword, "4, 10, fill, default");
        txtPassword.setColumns(10);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        okButton = new JButton("OK");
        okButton.setActionCommand(CMD_OK);
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand(CMD_CANCEL);
        buttonPane.add(cancelButton);
    }

    public void showView() {
        setModal(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setVisible(true);
    }

    public void exitView() {
        setVisible(false);
        dispose();
    }

    public JTextField getServerUri() {
        return txtServerUri;
    }

    public JTextField getRespositoryId() {
        return txtRespositoryid;
    }

    public JCheckBox getAnonymousLogin() {
        return chckbxAnonymousLogin;
    }

    public JTextField getUsernameText() {
        return txtUsername;
    }

    public JTextField getPasswordText() {
        return txtPassword;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}
