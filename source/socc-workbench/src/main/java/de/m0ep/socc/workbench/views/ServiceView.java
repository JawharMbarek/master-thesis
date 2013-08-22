package de.m0ep.socc.workbench.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import de.m0ep.socc.workbench.views.ConnectorConfigView.ServiceListEntry;

public class ServiceView extends JDialog {
	public static final String ACTION_CMD_SERVICE_LIST_ADD = "action_cmd_service_list_add";
	public static final String ACTION_CMD_SERVICE_LIST_DELETE = "action_cmd_service_list_delete";
	public static final String ACTION_CMD_VIEW_OK = "action_cmd_view_ok";
	public static final String ACTION_CMD_VIEW_CANCEL = "action_cmd_view_cancel";

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private JTextField txtName;
	private JTextField txtEndpointUri;

	private JList<ServiceListEntry> list;

	private JButton btnAddButton;
	private JButton btnDeleteButton;
	private JButton okButton;
	private JButton cancelButton;

	/**
	 * Launch the application.
	 */
	public static void main( String[] args ) {
		try {
			ServiceView dialog = new ServiceView();
			dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
			dialog.setVisible( true );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ServiceView() {
		initGUI();
	}

	private void initGUI() {
		setBounds( 100, 100, 524, 410 );
		getContentPane().setLayout( new BorderLayout() );
		contentPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		getContentPane().add( contentPanel, BorderLayout.CENTER );
		contentPanel.setLayout( new FormLayout( new ColumnSpec[] {
		        FormFactory.RELATED_GAP_COLSPEC,
		        FormFactory.DEFAULT_COLSPEC,
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
		                RowSpec.decode( "default:grow" ),
		                FormFactory.DEFAULT_ROWSPEC, } ) );

		list = new JList<ServiceListEntry>();
		JScrollPane scroll = new JScrollPane( list );
		scroll.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		contentPanel.add( scroll, "2, 2, 1, 9, fill, fill" );

		JLabel lblUri = new JLabel( "{URI}" );
		lblUri.setHorizontalAlignment( SwingConstants.CENTER );
		contentPanel.add( lblUri, "4, 2, 3, 1" );

		JLabel lblName = new JLabel( "Name:" );
		contentPanel.add( lblName, "4, 4, right, default" );

		txtName = new JTextField();
		contentPanel.add( txtName, "6, 4, fill, fill" );
		txtName.setColumns( 10 );

		JLabel lblEndpointUri = new JLabel( "Endpoint Uri:" );
		contentPanel.add( lblEndpointUri, "4, 6, right, default" );

		txtEndpointUri = new JTextField();
		txtEndpointUri.setText( "EnspointUri" );
		contentPanel.add( txtEndpointUri, "6, 6, fill, fill" );
		txtEndpointUri.setColumns( 10 );

		JLabel lblAuthentications = new JLabel( "Authentications:" );
		contentPanel.add( lblAuthentications, "4, 8, right default" );

		JPanel listButtonsPanel = new JPanel();
		contentPanel.add( listButtonsPanel, "2, 9, fill, fill" );
		listButtonsPanel.setLayout( new GridLayout( 1, 2, 0, 0 ) );

		btnAddButton = new JButton();
		btnAddButton.setActionCommand( ACTION_CMD_SERVICE_LIST_ADD );
		btnAddButton.setIcon(
		        new ImageIcon(
		                ServiceView.class.getResource(
		                        "/assets/icons/add.png" ) ) );
		btnAddButton.setToolTipText( "Add new service" );
		listButtonsPanel.add( btnAddButton );

		btnDeleteButton = new JButton();
		btnDeleteButton.setActionCommand( ACTION_CMD_SERVICE_LIST_DELETE );
		btnDeleteButton.setIcon(
		        new ImageIcon(
		                ServiceView.class.getResource(
		                        "/assets/icons/delete.png" ) ) );
		btnDeleteButton.setToolTipText( "Delete selected service" );
		listButtonsPanel.add( btnDeleteButton );

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
		getContentPane().add( buttonPane, BorderLayout.SOUTH );

		okButton = new JButton( "OK" );
		okButton.setActionCommand( ACTION_CMD_VIEW_OK );
		buttonPane.add( okButton );
		getRootPane().setDefaultButton( okButton );

		cancelButton = new JButton( "Cancel" );
		cancelButton.setActionCommand( ACTION_CMD_VIEW_CANCEL );
		buttonPane.add( cancelButton );
	}

	//    private static class ServiceListEntry {
	//        private Service service;
	//
	//        public ServiceListEntry( final Resource resource ) {
	//            Preconditions.checkNotNull( resource,
	//                    "Required parameter resource must be specified." );
	//
	//            this.service = Service.getInstance( resource.getModel(), resource );
	//        }
	//
	//        @Override
	//        public String toString() {
	//            return ( service.hasName() ) ? ( service.getName() ) : ( service.toString() );
	//        }
	//    }
}
