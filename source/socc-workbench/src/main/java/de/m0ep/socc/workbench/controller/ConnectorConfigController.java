
package de.m0ep.socc.workbench.controller;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.Resource;
import org.rdfs.sioc.UserAccount;

import com.google.common.base.Preconditions;

import de.m0ep.socc.config.ConnectorConfig;
import de.m0ep.socc.workbench.views.ConnectorConfigView;
import de.m0ep.socc.workbench.views.ConnectorConfigView.ConnectorConfigListEntry;
import de.m0ep.socc.workbench.views.ConnectorConfigView.UserAccountListEntry;

public class ConnectorConfigController {

    private ConnectorConfigView view;
    private Model model;

    public void init() {
        DefaultListModel<ConnectorConfigListEntry> connectorConfigListModel =
                (DefaultListModel<ConnectorConfigListEntry>) view.getConnectorConfigList()
                        .getModel();

        ClosableIterator<Resource> conConfigIterator = ConnectorConfig.getAllInstances( model );
        try {
            while (conConfigIterator.hasNext()) {
                Resource resource = (Resource) conConfigIterator.next();
                connectorConfigListModel.addElement( new ConnectorConfigListEntry( model, resource ) );
            }
        } finally {
            conConfigIterator.close();
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

    }

    public void setView( ConnectorConfigView view ) {
        this.view = Preconditions.checkNotNull( view,
                "Required parameter view must be specified." );
    }

    public void setModel( Model model ) {
        this.model = Preconditions.checkNotNull( model,
                "Required parameter model must be specified." );
    }

}
