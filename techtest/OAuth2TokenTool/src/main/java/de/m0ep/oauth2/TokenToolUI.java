package de.m0ep.oauth2;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.MemoryCredentialStore;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.PlusScopes;

public class TokenToolUI extends JFrame {
    private static final long serialVersionUID = 8733990698950061930L;

    /** Global instance of the HTTP transport. */
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    private JPanel contentPane;
    private JTextField textClientId;
    private JTextField textClientSecret;
    private JTextField textAccessToken;
    private JTextField textRefreshToken;
    private JTextField textExpires;

    private Credential credential;
    private JButton btnRefreshToken;
    private JButton btnGetAccessToken;
    private JPanel panel;
    private JButton btnLoadJson;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    TokenToolUI frame = new TokenToolUI();
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the frame.
     */
    public TokenToolUI() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(new BorderLayout(0, 0));

	panel = new JPanel();
	contentPane.add(panel);
	panel.setLayout(new GridLayout(0, 2, 0, 0));

	JLabel lblNewLabel = new JLabel("Client ID");
	panel.add(lblNewLabel);

	textClientId = new JTextField();
	panel.add(textClientId);
	textClientId.setColumns(10);

	JLabel lblClientSecret = new JLabel("Client Secret");
	panel.add(lblClientSecret);

	textClientSecret = new JTextField();
	panel.add(textClientSecret);
	textClientSecret.setColumns(10);

	Component horizontalStrut = Box.createHorizontalStrut(20);
	panel.add(horizontalStrut);

	btnGetAccessToken = new JButton("Get AccessToken");
	panel.add(btnGetAccessToken);

	JLabel lblAccessToken = new JLabel("Accesstoken");
	panel.add(lblAccessToken);

	textAccessToken = new JTextField();
	panel.add(textAccessToken);
	textAccessToken.setEditable(false);
	textAccessToken.setColumns(10);

	JLabel lblRefreshtoken = new JLabel("Refreshtoken");
	panel.add(lblRefreshtoken);

	textRefreshToken = new JTextField();
	panel.add(textRefreshToken);
	textRefreshToken.setEditable(false);
	textRefreshToken.setColumns(10);

	JLabel lblExpires = new JLabel("Expires");
	panel.add(lblExpires);

	textExpires = new JTextField();
	panel.add(textExpires);
	textExpires.setEditable(false);
	textExpires.setColumns(10);

	Component horizontalStrut_1 = Box.createHorizontalStrut(20);
	panel.add(horizontalStrut_1);

	btnRefreshToken = new JButton("Refresh Token");
	panel.add(btnRefreshToken);
	btnRefreshToken.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		try {
		    if (credential.refreshToken()) {
			setCredentialData(credential);
		    }
		} catch (IOException e) {
		    JOptionPane.showMessageDialog(null, e.getMessage(),
			    "Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	});
	btnRefreshToken.setEnabled(false);

	btnLoadJson = new JButton("Load Json");
	btnLoadJson.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {

	    }
	});
	contentPane.add(btnLoadJson, BorderLayout.NORTH);
	btnGetAccessToken.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		try {
		    credential = authorize(textClientId.getText(),
			    textClientSecret.getText());
		    setCredentialData(credential);
		    btnRefreshToken.setEnabled(true);

		} catch (IOException e) {
		    JOptionPane.showMessageDialog(null, e.getMessage(),
			    "Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	});
    }

    protected Credential authorize(String id, String secret) throws IOException {
	// load client secrets

	System.out.println("hiho");
	// set up file credential store
	MemoryCredentialStore credentialStore = new MemoryCredentialStore();

	// set up authorization code flow
	GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
		HTTP_TRANSPORT, JSON_FACTORY, id, secret,
		Collections.singleton(PlusScopes.PLUS_ME)).setCredentialStore(
		credentialStore).build();
	// authorize
	return new AuthorizationCodeInstalledApp(flow,
		new LocalServerReceiver()).authorize("user");

    }

    protected void setCredentialData(Credential credential) {
	textAccessToken.setText(credential.getAccessToken());
	textRefreshToken.setText(credential.getRefreshToken());
	textExpires.setText(new Date(credential.getExpiresInSeconds() * 1000L
		+ System.currentTimeMillis()).toString());
    }
}
