
package de.m0ep.foaftool;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class ConnectToDialog extends JDialog implements ActionListener {
    public static final int OK_OPTION = 0;
    public static final int CANCEL_OPTION = 1;
    public static final int CLOSED_OPTION = 1;

    private static final long serialVersionUID = 1992787432449334678L;
    private static final String ACTION_OK = "OK";
    private static final String ACTION_CANCEL = "Cancel";
    private static final String ACTION_CHANGE_LOGIN_TYPE = "change_login_type";

    private final JPanel contentPanel = new JPanel();
    private JTextField txtServerUrl;
    private JTextField txtRespositoryId;
    private JTextField txtUsername;
    private JTextField txtPassword;
    private JLabel lblPassword;
    private JLabel lblUsername;
    private JCheckBox chckbxAnonymousLogin;

    private int option = CANCEL_OPTION;
    private String serverUrl;
    private String repositoryId;
    private boolean isAnonymousLogin;
    private String username;
    private String password;

    /**
     * Create the dialog.
     */
    public ConnectToDialog(Component parent) {
        setTitle("Connect to...");
        setBounds(100, 100, 450, 278);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));

        JPanel connection = new JPanel();
        connection.setBorder(new TitledBorder(null, "Connection", TitledBorder.LEADING,
                TitledBorder.TOP, null, null));
        contentPanel.add(connection);
        connection.setLayout(new FormLayout(new ColumnSpec[] {
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
                }));

        JLabel lblServerUrl = new JLabel("Server URL:");
        connection.add(lblServerUrl, "2, 2, right, default");

        txtServerUrl = new JTextField();
        txtServerUrl.setColumns(10);
        connection.add(txtServerUrl, "4, 2, fill, default");

        JLabel lblRepositoryId = new JLabel("Repository ID:");
        connection.add(lblRepositoryId, "2, 4, right, default");

        txtRespositoryId = new JTextField();
        txtRespositoryId.setColumns(10);
        connection.add(txtRespositoryId, "4, 4, fill, default");

        JPanel spacer = new JPanel();
        contentPanel.add(spacer);

        JPanel credentials = new JPanel();
        credentials.setBorder(new TitledBorder(null, "Credentials", TitledBorder.LEADING,
                TitledBorder.TOP, null, null));
        contentPanel.add(credentials);

        credentials.setLayout(new FormLayout(new ColumnSpec[] {
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
                }));

        chckbxAnonymousLogin = new JCheckBox("anonymous login");
        chckbxAnonymousLogin.setActionCommand(ACTION_CHANGE_LOGIN_TYPE);
        chckbxAnonymousLogin.addActionListener(this);
        chckbxAnonymousLogin.setSelected(true);
        credentials.add(chckbxAnonymousLogin, "2, 2, 3, 1");

        lblUsername = new JLabel("Username:");
        txtUsername = new JTextField();
        txtUsername.setColumns(10);
        credentials.add(txtUsername, "4, 4, fill, default");
        credentials.add(lblUsername, "2, 4, right, default");

        lblPassword = new JLabel("Password:");
        txtPassword = new JTextField();
        txtPassword.setColumns(10);
        credentials.add(txtPassword, "4, 6, fill, default");
        credentials.add(lblPassword, "2, 6, right, default");

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton(ACTION_OK);
        okButton.setActionCommand(ACTION_OK);
        okButton.addActionListener(this);
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton(ACTION_CANCEL);
        cancelButton.setActionCommand(ACTION_CANCEL);
        cancelButton.addActionListener(this);
        buttonPane.add(cancelButton);

        updateCrendentialPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (ACTION_CHANGE_LOGIN_TYPE.equals(cmd)) {
            updateCrendentialPanel();
        }
        else if (ACTION_CANCEL.equals(cmd)) {
            onCancel();
        }
        else if (ACTION_OK.equals(cmd)) {
            onOk();
        }
    }

    public int openDialog() {
        setModal(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setVisible(true);
        return option;
    }

    private void onOk() {
        option = OK_OPTION;
        serverUrl = txtServerUrl.getText();
        repositoryId = txtRespositoryId.getText();
        isAnonymousLogin = chckbxAnonymousLogin.isSelected();
        username = txtUsername.getText();
        password = txtPassword.getText();

        setVisible(false);
        dispose();
    }

    private void onCancel() {
        option = CANCEL_OPTION;
        serverUrl = null;
        repositoryId = null;
        isAnonymousLogin = false;
        username = null;
        password = null;

        setVisible(false);
        dispose();
    }

    private void updateCrendentialPanel() {
        if (chckbxAnonymousLogin.isSelected()) {
            lblUsername.setEnabled(false);
            txtUsername.setEnabled(false);
            lblPassword.setEnabled(false);
            txtPassword.setEnabled(false);
        } else {
            lblUsername.setEnabled(true);
            txtUsername.setEnabled(true);
            lblPassword.setEnabled(true);
            txtPassword.setEnabled(true);
        }
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
        txtServerUrl.setText(serverUrl);
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
        txtRespositoryId.setText(repositoryId);
    }

    public boolean isAnonymousLogin() {
        return isAnonymousLogin;
    }

    public void setAnonymousLogin(boolean isAnonymousLogin) {
        this.isAnonymousLogin = isAnonymousLogin;
        chckbxAnonymousLogin.setSelected(isAnonymousLogin);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        txtUsername.setText(username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        txtPassword.setText(password);
    }
}
