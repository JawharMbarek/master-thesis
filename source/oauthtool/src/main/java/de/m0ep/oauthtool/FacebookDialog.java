package de.m0ep.oauthtool;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class FacebookDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	private JTextField txtClientId;
	private JTextField txtClientSecret;
	private JTextField txtAccesstoken;
	private JTextField txtExtendedToken;
	private JButton generateButton;
	private JButton closeButton;

	/**
	 * Create the dialog.
	 */
	public FacebookDialog() {
		initView();
	}

	private void initView() {
		setTitle( "Facebook Accesstokens" );
		setResizable( false );
		setBounds( 100, 100, 450, 300 );
		setIconImage( new ImageIcon( OAuthToolApp.class
		        .getResource( "/assets/images/facebook_logo.png" ) ).getImage() );

		getContentPane().setLayout( new BorderLayout() );
		contentPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		getContentPane().add( contentPanel, BorderLayout.CENTER );
		contentPanel.setLayout( new FormLayout( new ColumnSpec[] {
		        FormFactory.RELATED_GAP_COLSPEC,
		        FormFactory.DEFAULT_COLSPEC,
		        FormFactory.RELATED_GAP_COLSPEC,
		        ColumnSpec.decode( "default:grow" ), },
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
		                FormFactory.DEFAULT_ROWSPEC, } ) );

		final JLabel lblClientid = new JLabel( "ClientID:" );
		lblClientid.setHorizontalAlignment( SwingConstants.TRAILING );
		lblClientid.setName( "lblClientid" );
		contentPanel.add( lblClientid, "2, 2, right, default" );

		txtClientId = new JTextField();
		txtClientId.setName( "txtClientId" );
		contentPanel.add( txtClientId, "4, 2, fill, default" );
		txtClientId.setColumns( 30 );

		final JLabel lblClientSecret = new JLabel( "ClientSecret:" );
		lblClientSecret.setHorizontalAlignment( SwingConstants.TRAILING );
		lblClientSecret.setName( "lblClientSecret" );
		contentPanel.add( lblClientSecret, "2, 4, right, default" );

		txtClientSecret = new JTextField();
		txtClientSecret.setName( "txtClientSecret" );
		contentPanel.add( txtClientSecret, "4, 4, fill, default" );
		txtClientSecret.setColumns( 30 );

		final JSeparator separator = new JSeparator();
		separator.setName( "separator" );
		contentPanel.add( separator, "2, 6, 3, 1" );

		final JLabel lblAccesstoken = new JLabel( "Accesstoken:" );
		lblAccesstoken.setHorizontalAlignment( SwingConstants.TRAILING );
		lblAccesstoken.setName( "lblAccesstoken" );
		contentPanel.add( lblAccesstoken, "2, 8, right, default" );

		txtAccesstoken = new JTextField();
		txtAccesstoken.setEditable( false );
		txtAccesstoken.setName( "txtAccesstoken" );
		contentPanel.add( txtAccesstoken, "4, 8, fill, default" );
		txtAccesstoken.setColumns( 30 );

		final JLabel lblExtendedToken = new JLabel( "Extended-Token:" );
		lblExtendedToken.setHorizontalAlignment( SwingConstants.TRAILING );
		lblExtendedToken.setName( "lblExtendedToken" );
		contentPanel.add( lblExtendedToken, "2, 10, right, default" );

		txtExtendedToken = new JTextField();
		txtExtendedToken.setEditable( false );
		txtExtendedToken.setName( "txtExtendedToken" );
		contentPanel.add( txtExtendedToken, "4, 10, fill, default" );
		txtExtendedToken.setColumns( 30 );

		pack();

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
		getContentPane().add( buttonPane, BorderLayout.SOUTH );

		generateButton = new JButton( "Get Token" );
		generateButton.setName( "okButton" );
		generateButton.setActionCommand( "OK" );
		buttonPane.add( generateButton );
		getRootPane().setDefaultButton( generateButton );

		closeButton = new JButton( "Close" );
		closeButton.setName( "cancelButton" );
		closeButton.setActionCommand( "Cancel" );
		buttonPane.add( closeButton );
	}

	public void showDialog() {
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		setModal( true );
		setModalityType( ModalityType.APPLICATION_MODAL );
		setVisible( true );
	}

	public void closeDialog() {
		setVisible( false );
		dispose();
	}

	public JButton getGenerateButton() {
		return generateButton;
	}

	public JButton getCloseButton() {
		return closeButton;
	}

	public JTextField getClientId() {
		return txtClientId;
	}

	public JTextField getClientSecret() {
		return txtClientSecret;
	}

	public JTextField getAccesstoken() {
		return txtAccesstoken;
	}

	public JTextField getExtendedToken() {
		return txtExtendedToken;
	}
}
