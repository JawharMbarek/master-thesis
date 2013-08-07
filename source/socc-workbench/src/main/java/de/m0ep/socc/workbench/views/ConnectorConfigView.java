
package de.m0ep.socc.workbench.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.Resource;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.xmlns.foaf.Person;

import de.m0ep.sioc.service.auth.UserAccount;
import de.m0ep.socc.config.ConnectorConfig;
import de.m0ep.socc.core.connector.canvaslms.CanvasLmsConnector;
import de.m0ep.socc.core.connector.facebook.FacebookConnector;
import de.m0ep.socc.core.connector.google.youtube.v2.YoutubeConnector;
import de.m0ep.socc.core.connector.moodle.Moodle2Connector;
import de.m0ep.socc.core.exceptions.NotFoundException;
import de.m0ep.socc.core.utils.UserAccountUtils;
import de.m0ep.socc.workbench.controller.ConnectorConfigController;

public class ConnectorConfigView extends JDialog {
    public static final String ACTION_CMD_VIEW_SAVE = "action_cmd_view_save";
    public static final String ACTION_CMD_VIEW_CLOSE = "action_cmd_view_close";

    private static final long serialVersionUID = 1L;

    private final JPanel contentPanel = new JPanel();
    private JButton saveButton;
    private JButton closeButton;
    private JButton btnGenerateUri;
    private JButton btnAddConnectorConfig;
    private JButton btnDeleteConnectorConfig;

    private JTextField txtRdfUri;
    private JTextField txtId;

    private JComboBox<ConnectorClassListEntry<?>> cboxConnectorClass;
    private JComboBox<UserAccountListEntry> cboxDefaultUserAccount;

    private JList<ConnectorConfigListEntry> connectorConfigList;

    private JPanel panel;
    private JScrollPane connectorConfigListScrollPane;

    /**
     * Launch the application.
     */
    public static void main( String[] args ) {
        try {
            ConnectorConfigView dialog = new ConnectorConfigView();
            dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
            dialog.setVisible( true );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public ConnectorConfigView() {
        initView();
    }

    private void initView() {
        setBounds( 100, 100, 450, 300 );
        getContentPane().setLayout( new BorderLayout() );
        contentPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        getContentPane().add( contentPanel, BorderLayout.CENTER );
        contentPanel.setLayout( new FormLayout( new ColumnSpec[] {
                FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode( "3cm:grow" ),
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode( "default:grow" ),
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC, },
                new RowSpec[] {
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        RowSpec.decode( "default:grow" ),
                        FormFactory.DEFAULT_ROWSPEC, } ) );

        connectorConfigList = new JList<ConnectorConfigListEntry>(
                new DefaultListModel<ConnectorConfigListEntry>() );
        connectorConfigListScrollPane = new JScrollPane( connectorConfigList );
        contentPanel.add( connectorConfigListScrollPane, "2, 2, 1, 8, fill, fill" );

        JLabel lblRdfUri = new JLabel( "RDF Uri:" );
        contentPanel.add( lblRdfUri, "4, 2, right, default" );

        txtRdfUri = new JTextField();
        contentPanel.add( txtRdfUri, "6, 2, fill, default" );
        txtRdfUri.setColumns( 10 );

        btnGenerateUri = new JButton( "Random" );
        contentPanel.add( btnGenerateUri, "8, 2" );

        JLabel lblId = new JLabel( "Id:" );
        contentPanel.add( lblId, "4, 4, right, default" );

        txtId = new JTextField();
        contentPanel.add( txtId, "6, 4, 3, 1, fill, default" );
        txtId.setColumns( 10 );

        JLabel lblConnectorClass = new JLabel( "Connector:" );
        contentPanel.add( lblConnectorClass, "4, 6, right, default" );

        cboxConnectorClass = new JComboBox<ConnectorClassListEntry<?>>(
                new DefaultComboBoxModel<>(
                        new ConnectorClassListEntry<?>[] {
                                new ConnectorClassListEntry<>( CanvasLmsConnector.class ),
                                new ConnectorClassListEntry<>( FacebookConnector.class ),
                                new ConnectorClassListEntry<>( YoutubeConnector.class ),
                                new ConnectorClassListEntry<>( Moodle2Connector.class )
                        } ) );
        contentPanel.add( cboxConnectorClass, "6, 6, 3, 1, fill, default" );

        JLabel lblDefaultUserAccount = new JLabel( "Default Account:" );
        contentPanel.add( lblDefaultUserAccount, "4, 8, right, default" );

        cboxDefaultUserAccount = new JComboBox<UserAccountListEntry>(
                new DefaultComboBoxModel<UserAccountListEntry>() );
        contentPanel.add( cboxDefaultUserAccount, "6, 8, 3, 1, fill, default" );

        panel = new JPanel();
        contentPanel.add( panel, "2, 10, fill, fill" );
        panel.setLayout( new GridLayout( 1, 0, 0, 0 ) );

        btnAddConnectorConfig = new JButton();
        btnAddConnectorConfig.setIcon(
                new ImageIcon(
                        ConnectorConfigView.class.getResource(
                                "/assets/icons/add.png" ) ) );
        panel.add( btnAddConnectorConfig );

        btnDeleteConnectorConfig = new JButton();
        btnDeleteConnectorConfig.setIcon(
                new ImageIcon(
                        ConnectorConfigView.class.getResource(

                                "/assets/icons/delete.png" ) ) );
        panel.add( btnDeleteConnectorConfig );

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
        getContentPane().add( buttonPane, BorderLayout.SOUTH );

        saveButton = new JButton( "Save" );
        saveButton.setActionCommand( ACTION_CMD_VIEW_SAVE );
        buttonPane.add( saveButton );
        getRootPane().setDefaultButton( saveButton );

        closeButton = new JButton( "Close" );
        closeButton.setActionCommand( ACTION_CMD_VIEW_CLOSE );
        buttonPane.add( closeButton );
    }

    public static ConnectorConfigView create( ConnectorConfigController controller, Model model ) {
        ConnectorConfigView view = new ConnectorConfigView();
        controller.setView( view );
        controller.setModel( model );
        controller.init();
        return view;
    }

    public void showView() {
        setModal( true );
        setModalityType( ModalityType.APPLICATION_MODAL );
        setVisible( true );
    }

    public void exitView() {
        setVisible( false );
        dispose();
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JButton getGenerateUriButton() {
        return btnGenerateUri;
    }

    public JButton getAddConnectorConfigButton() {
        return btnAddConnectorConfig;
    }

    public JButton getDeleteConnectorConfigButton() {
        return btnDeleteConnectorConfig;
    }

    public JTextField getRdfUriTextfield() {
        return txtRdfUri;
    }

    public JTextField getIdTextfield() {
        return txtId;
    }

    public JComboBox<ConnectorClassListEntry<?>> getConnectorClassCombobox() {
        return cboxConnectorClass;
    }

    public JComboBox<UserAccountListEntry> getDefaultUserAccountCombobox() {
        return cboxDefaultUserAccount;
    }

    public JList<ConnectorConfigListEntry> getConnectorConfigList() {
        return connectorConfigList;
    }

    public JScrollPane getConnectorConfigListScrollPane() {
        return connectorConfigListScrollPane;
    }

    public static class ConnectorClassListEntry<T> {
        private Class<T> clazz;

        public ConnectorClassListEntry( Class<T> clazz ) {
            this.clazz = Preconditions.checkNotNull( clazz,
                    "Required parameter clazz must be specified." );
        }

        public Class<T> getClazz() {
            return clazz;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode( clazz );
        }

        @Override
        public boolean equals( Object obj ) {
            if (this == obj) {
                return true;
            }

            if (null == obj || this.getClass() != obj.getClass()) {
                return false;
            }

            ConnectorClassListEntry<?> other = (ConnectorClassListEntry<?>) obj;

            return Objects.equal( this.clazz, other.clazz );
        }

        @Override
        public String toString() {
            return clazz.getSimpleName();
        }
    }

    public static class ConnectorConfigListEntry {
        private ConnectorConfig connectorConfig;

        public ConnectorConfigListEntry( final Model model, final Resource resource ) {
            Preconditions.checkNotNull( resource,
                    "Required parameter resource must be specified." );
            this.connectorConfig = ConnectorConfig.getInstance(
                    model,
                    resource );

        }

        public ConnectorConfig getConnectorConfig() {
            return connectorConfig;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode( connectorConfig,
                    connectorConfig.getId(),
                    connectorConfig.getConnectorClass(),
                    connectorConfig.getDefaultUser() );
        }

        @Override
        public boolean equals( Object obj ) {
            if (this == obj) {
                return true;
            }

            if (null == obj || this.getClass() != obj.getClass()) {
                return false;
            }

            ConnectorConfigView.ConnectorConfigListEntry other = (ConnectorConfigView.ConnectorConfigListEntry) obj;

            return Objects.equal( this.connectorConfig, other.connectorConfig )
                    && Objects.equal(
                            this.connectorConfig.getId(),
                            other.connectorConfig.getId() )
                    && Objects.equal(
                            this.connectorConfig.getConnectorClass(),
                            other.connectorConfig.getConnectorClass() )
                    && Objects.equal(
                            this.connectorConfig.getDefaultUser(),
                            other.connectorConfig.getDefaultUser() );
        }

        @Override
        public String toString() {
            return connectorConfig.getId();
        }
    }

    public static class UserAccountListEntry {
        private UserAccount userAccount;

        private String personName;
        private String accountName;
        private String accountServiceHomepage;

        public UserAccountListEntry( final Model model, final Resource resource ) {
            Preconditions.checkNotNull( resource,
                    "Required parameter resource must be specified." );

            this.userAccount = UserAccount.getInstance(
                    model,
                    resource );
        }

        public UserAccount getUserAccount() {
            return userAccount;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode( userAccount,
                    userAccount.getAccountName(),
                    userAccount.getAccountServiceHomepage() );
        }

        @Override
        public boolean equals( Object obj ) {
            if (this == obj) {
                return true;
            }

            if (null == obj || this.getClass() != obj.getClass()) {
                return false;
            }

            UserAccountListEntry other = (UserAccountListEntry) obj;

            return Objects.equal( this.userAccount, other.userAccount )
                    && Objects.equal(
                            this.userAccount.getAccountName(),
                            other.getUserAccount().getAccountName() )
                    && Objects.equal(
                            this.userAccount.getAccountServiceHomepage(),
                            other.userAccount.getAccountServiceHomepage() );
        }

        @Override
        public String toString() {
            try {
                Person person = UserAccountUtils.findPerson( userAccount.getModel(), userAccount );
                return person.getName()
                        + " ( " + userAccount.getAccountName()
                        + "@" + userAccount.getAccountServiceHomepage()
                        + " )";
            } catch (NotFoundException e) {
                return userAccount.getName() + "@" + userAccount.getAccountServiceHomepage();
            }
        }
    }
}
