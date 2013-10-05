package de.m0ep.oauthtool;

import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import de.m0ep.oauthtool.facebook.FacebookController;
import de.m0ep.oauthtool.facebook.FacebookDialog;
import de.m0ep.oauthtool.google.GooglePlusController;
import de.m0ep.oauthtool.google.GooglePlusDialog;

public class OAuthToolApp {

	private JFrame frmOauthTool;
	private JButton btnFacebook;
	private JButton btnGooglePlus;

	/**
	 * Launch the application.
	 */
	public static void main( String[] args ) {
		EventQueue.invokeLater( new Runnable() {
			@Override
			public void run() {
				try {
					OAuthToolApp window = new OAuthToolApp();
					window.frmOauthTool.setVisible( true );
				} catch ( Exception e ) {
					e.printStackTrace();
				}
			}
		} );
	}

	/**
	 * Create the application.
	 */
	public OAuthToolApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmOauthTool = new JFrame();
		frmOauthTool.setTitle( "OAuth Tool" );
		frmOauthTool.setName( "frmOauthTool" );
		frmOauthTool.setBounds( 100, 100, 450, 300 );
		frmOauthTool.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frmOauthTool.getContentPane().setLayout( new FormLayout( new ColumnSpec[] {
		        FormFactory.RELATED_GAP_COLSPEC,
		        FormFactory.DEFAULT_COLSPEC,
		        FormFactory.RELATED_GAP_COLSPEC,
		        FormFactory.DEFAULT_COLSPEC,
		        FormFactory.RELATED_GAP_COLSPEC, },
		        new RowSpec[] {
		                FormFactory.RELATED_GAP_ROWSPEC,
		                FormFactory.DEFAULT_ROWSPEC,
		                FormFactory.RELATED_GAP_ROWSPEC, } ) );

		btnGooglePlus = new JButton( "" );
		btnGooglePlus.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				GooglePlusController ctrl = new GooglePlusController( new GooglePlusDialog() );
				ctrl.run();
			}
		} );
		btnGooglePlus.setToolTipText( "Create Google+ Accesstoken" );
		btnGooglePlus.setMargin( new Insets( 2, 2, 2, 2 ) );
		btnGooglePlus.setIcon( new ImageIcon( OAuthToolApp.class
		        .getResource( "/assets/images/googleplus_logo.png" ) ) );
		btnGooglePlus.setName( "btnGooglePlus" );
		frmOauthTool.getContentPane().add( btnGooglePlus, "2, 2" );

		btnFacebook = new JButton( "" );
		btnFacebook.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				FacebookController ctrl = new FacebookController( new FacebookDialog() );
				ctrl.run();
			}
		} );
		btnFacebook.setToolTipText( "Create Facebook Accesstoken" );
		btnFacebook.setMargin( new Insets( 2, 2, 2, 2 ) );
		btnFacebook.setIcon( new ImageIcon( OAuthToolApp.class
		        .getResource( "/assets/images/facebook_logo.png" ) ) );
		btnFacebook.setName( "btnFacebook" );
		frmOauthTool.getContentPane().add( btnFacebook, "4, 2" );

		frmOauthTool.pack();
	}

}
