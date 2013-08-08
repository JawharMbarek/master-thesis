
package de.m0ep.socc.workbench.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.sioc.services.auth.Service;
import de.m0ep.socc.config.ConnectorConfig;
import de.m0ep.socc.core.connector.canvaslms.CanvasLmsConnector;
import de.m0ep.socc.core.connector.facebook.FacebookConnector;
import de.m0ep.socc.core.connector.google.youtube.v2.YoutubeConnector;
import de.m0ep.socc.core.connector.moodle.Moodle2Connector;
import de.m0ep.socc.workbench.views.ConnectorConfigView;
import de.m0ep.socc.workbench.views.ConnectorConfigView.ConnectorClassListEntry;
import de.m0ep.socc.workbench.views.ConnectorConfigView.ConnectorConfigListEntry;
import de.m0ep.socc.workbench.views.ConnectorConfigView.ServiceListEntry;
import de.m0ep.socc.workbench.views.ConnectorConfigView.UserAccountListEntry;

public class ConnectorConfigController implements ActionListener {
    private static final Logger LOG = LoggerFactory.getLogger( ConnectorConfigController.class );

    private ConnectorConfigView view;
    private Model model;

    private final ConnectorClassListEntry[] connectorClassArray = {
            new ConnectorClassListEntry( CanvasLmsConnector.class ),
            new ConnectorClassListEntry( FacebookConnector.class ),
            new ConnectorClassListEntry( YoutubeConnector.class ),
            new ConnectorClassListEntry( Moodle2Connector.class )
    };

    private boolean isAdding;

    public void init() {
        disable();
        updateConnectorConfigList();

        DefaultComboBoxModel<ConnectorClassListEntry> classCboxModel =
                (DefaultComboBoxModel<ConnectorClassListEntry>) view.getConnectorClassCombobox()
                        .getModel();

        for (ConnectorClassListEntry entry : connectorClassArray) {
            classCboxModel.addElement( entry );
        }

        DefaultComboBoxModel<UserAccountListEntry> accountCboxModel =
                (DefaultComboBoxModel<UserAccountListEntry>) view.getDefaultUserAccountCombobox()
                        .getModel();
        ClosableIterator<Resource> accountIterator = UserAccount.getAllInstances( model );
        try {
            while (accountIterator.hasNext()) {
                Resource resource = (Resource) accountIterator.next();
                accountCboxModel.addElement( new UserAccountListEntry( model, resource ) );
            }
        } finally {
            accountIterator.close();
        }

        DefaultComboBoxModel<ServiceListEntry> serviceCboxModel =
                (DefaultComboBoxModel<ServiceListEntry>) view.getServicesCombobox().getModel();
        ClosableIterator<Resource> serviceIter = Service.getAllInstances( model );
        try {
            while (serviceIter.hasNext()) {
                Resource resource = (Resource) serviceIter.next();
                serviceCboxModel.addElement( new ServiceListEntry( model, resource ) );
            }
        } finally {
            accountIterator.close();
        }

        view.getGenerateUriButton().addActionListener( this );
        view.getAddConnectorConfigButton().addActionListener( this );
        view.getDeleteConnectorConfigButton().addActionListener( this );
        view.getSaveButton().addActionListener( this );
        view.getCloseButton().addActionListener( this );

        view.getRdfUriTextfield().getDocument().addDocumentListener( new DocumentListener() {
            @Override
            public void removeUpdate( DocumentEvent e ) {
                checkRdfUri( view.getRdfUriTextfield() );
            }

            @Override
            public void insertUpdate( DocumentEvent e ) {
                checkRdfUri( view.getRdfUriTextfield() );
            }

            @Override
            public void changedUpdate( DocumentEvent e ) {
                checkRdfUri( view.getRdfUriTextfield() );
            }
        } );

        view.getConnectorConfigList().addListSelectionListener( new ListSelectionListener() {
            @Override
            public void valueChanged( ListSelectionEvent e ) {
                ConnectorConfigListEntry entry = view.getConnectorConfigList().getSelectedValue();

                if (null != entry) {
                    enable();
                    ConnectorConfig connectorConfig = entry.getConnectorConfig();
                    isAdding = false;
                    view.getSaveButton().setText( "Save" );
                    view.getRdfUriTextfield().setEditable( false );
                    view.getRdfUriTextfield().setText( connectorConfig.toString() );
                    view.getIdTextfield().setEditable( false );
                    view.getIdTextfield().setText( connectorConfig.getId() );

                    for (int i = 0; i < view.getConnectorClassCombobox().getItemCount(); i++) {
                        ConnectorClassListEntry classEntry = view.getConnectorClassCombobox()
                                .getItemAt( i );

                        if (classEntry.getClazz().getName()
                                .equals( connectorConfig.getConnectorClass() )) {
                            view.getConnectorClassCombobox().setSelectedIndex( i );
                            break;
                        }
                    }

                    for (int i = 0; i < view.getDefaultUserAccountCombobox().getItemCount(); i++) {
                        UserAccountListEntry accountEntry = view.getDefaultUserAccountCombobox()
                                .getItemAt( i );

                        if (accountEntry.getUserAccount().equals(
                                connectorConfig.getDefaultUserAccount() )) {
                            view.getDefaultUserAccountCombobox().setSelectedIndex( i );
                            break;
                        }
                    }

                    for (int i = 0; i < view.getServicesCombobox().getItemCount(); i++) {
                        ServiceListEntry serviceEntry = view.getServicesCombobox()
                                .getItemAt( i );

                        if (serviceEntry.getService().equals( connectorConfig.getService() )) {
                            view.getServicesCombobox().setSelectedIndex( i );
                            break;
                        }
                    }
                } else {
                    disable();
                }
            }
        } );
    }

    private void updateConnectorConfigList() {
        DefaultListModel<ConnectorConfigListEntry> connectorConfigListModel =
                (DefaultListModel<ConnectorConfigListEntry>) view.getConnectorConfigList()
                        .getModel();
        connectorConfigListModel.clear();

        ClosableIterator<Resource> conConfigIterator = ConnectorConfig.getAllInstances( model );
        try {
            while (conConfigIterator.hasNext()) {
                Resource resource = (Resource) conConfigIterator.next();
                connectorConfigListModel
                        .addElement( new ConnectorConfigListEntry( model, resource ) );
            }
        } finally {
            conConfigIterator.close();
        }
    }

    public void disable() {
        view.getRdfUriTextfield().setEditable( false );
        view.getIdTextfield().setEditable( false );
        view.getDefaultUserAccountCombobox().setEnabled( false );
        view.getConnectorClassCombobox().setEnabled( false );
        view.getServicesCombobox().setEnabled( false );
    }

    public void enable() {
        view.getRdfUriTextfield().setEditable( true );
        view.getIdTextfield().setEditable( true );
        view.getDefaultUserAccountCombobox().setEnabled( true );
        view.getConnectorClassCombobox().setEnabled( true );
        view.getServicesCombobox().setEnabled( true );
    }

    private void checkRdfUri( JTextField textField ) {
        String text = textField.getText();

        try {
            if (!text.startsWith( "urn:rnd:" )) {
                new URL( text );
            }
            textField.setForeground( UIManager.getColor( "TextField.foreground" ) );
        } catch (Exception e) {
            textField.setForeground( Color.RED );
        }
    }

    public void setView( ConnectorConfigView view ) {
        this.view = Preconditions.checkNotNull( view,
                "Required parameter view must be specified." );
    }

    public void setModel( Model model ) {
        this.model = Preconditions.checkNotNull( model,
                "Required parameter model must be specified." );
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
        String cmd = e.getActionCommand();
        LOG.debug( cmd );

        if (ConnectorConfigView.ACTION_CMD_URI_GENERATE.equals( cmd )) {
            view.getRdfUriTextfield().setText( model.newRandomUniqueURI().toString() );
        } else if (ConnectorConfigView.ACTION_CMD_ADD_CONNECTOR_CONFIG.equals( cmd )) {
            enable();
            view.getSaveButton().setText( "Add" );
            view.getConnectorConfigList().setSelectedIndex( -1 );
            view.getRdfUriTextfield().setText( "" );
            view.getIdTextfield().setText( "" );

            isAdding = true;
        } else if (ConnectorConfigView.ACTION_CMD_VIEW_SAVE.equals( cmd )) {
            ConnectorConfig cfg = null;
            if (isAdding) {
                cfg = new ConnectorConfig(
                        model,
                        view.getRdfUriTextfield().getText(),
                        true );
            } else {
                cfg = ConnectorConfig.getInstance(
                        model,
                        Builder.createURI( view.getRdfUriTextfield().getText() ) );
            }

            if (null != cfg) {
                UserAccountListEntry account = (UserAccountListEntry) view
                        .getDefaultUserAccountCombobox().getSelectedItem();
                ConnectorClassListEntry clazz = (ConnectorClassListEntry) view
                        .getConnectorClassCombobox().getSelectedItem();
                ServiceListEntry service = (ServiceListEntry) view.getServicesCombobox()
                        .getSelectedItem();

                cfg.setId( view.getIdTextfield().getText() );
                cfg.setDefaultUserAccount( account.getUserAccount() );
                cfg.setConnectorClass( clazz.getClazz().getName() );
                cfg.setService( service.getService() );
            }

            updateConnectorConfigList();
        } else if (ConnectorConfigView.ACTION_CMD_DELETE_CONNECTOR_CONFIG.equals( cmd )) {
            int index = view.getConnectorConfigList().getSelectedIndex();

            if (-1 != index) {
                ConnectorConfigListEntry configEntry = view.getConnectorConfigList()
                        .getSelectedValue();
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
                        view,
                        "Do you realy wan to delete '"
                                + configEntry.getConnectorConfig().getId()
                                + "'?", "Please confirm...", JOptionPane.YES_NO_OPTION )) {
                    ConnectorConfig.deleteAllProperties(
                            configEntry.getConnectorConfig().getModel(),
                            configEntry.getConnectorConfig() );

                    ( (DefaultListModel<ConnectorConfigListEntry>) view.getConnectorConfigList()
                            .getModel() ).remove( index );

                    updateConnectorConfigList();
                }
            }
        } else if (ConnectorConfigView.ACTION_CMD_VIEW_CLOSE.equals( cmd )) {
            view.dispose();
        }
    }
}
