
package de.m0ep.socc.workbench.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.beans.BeanAdapter;

import de.m0ep.socc.workbench.models.RDFStoreConnection;
import de.m0ep.socc.workbench.views.ConnectToView;

public class ConnectToController implements ActionListener {
    private RDFStoreConnection model;
    private ConnectToView view;
    private BeanAdapter<RDFStoreConnection> beanAdapter;
    private Runnable okListener;
    private Runnable cancelListener;

    public ConnectToController() {
        model = new RDFStoreConnection( "http://localhost:8080/openrdf-workbench", "master-thesis" );
        view = new ConnectToView();

        view.getAnonymousLogin().setSelected( true );
        view.getUsername().setEditable( false );
        view.getPassword().setEditable( false );

        view.getCancelButton().addActionListener( this );
        view.getOkButton().addActionListener( this );

        view.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e ) {
                super.windowClosing( e );
                if (null != cancelListener) {
                    cancelListener.run();
                }
            }
        } );

        view.getAnonymousLogin().addChangeListener( new ChangeListener() {
            @Override
            public void stateChanged( ChangeEvent e ) {
                if (view.getAnonymousLogin().isSelected()) {
                    view.getUsername().setEditable( false );
                    view.getPassword().setEditable( false );
                } else {
                    view.getUsername().setEditable( true );
                    view.getPassword().setEditable( true );
                }
            }
        } );

        beanAdapter = new BeanAdapter<RDFStoreConnection>( model, true );

        Bindings.bind(
                view.getServerUri(),
                beanAdapter.getValueModel( RDFStoreConnection.PROPERTY_SERVER_URI ) );
        Bindings.bind(
                view.getRespositoryId(),
                beanAdapter.getValueModel( RDFStoreConnection.PROPERTY_REPOSITORY_ID ) );
        Bindings.bind(
                view.getAnonymousLogin(),
                beanAdapter.getValueModel( RDFStoreConnection.PROPERTY_ANONYMOUS_LOGIN ) );
        Bindings.bind(
                view.getUsername(),
                beanAdapter.getValueModel( RDFStoreConnection.PROPERTY_USERNAME ) );
        Bindings.bind(
                view.getPassword(),
                beanAdapter.getValueModel( RDFStoreConnection.PROPERTY_PASSWORD ) );
    }

    public RDFStoreConnection getModel() {
        return model;
    }

    public ConnectToView getView() {
        return view;
    }

    public void run() {
        view.showView();
    }

    public void setOnOk( Runnable okListener ) {
        this.okListener = okListener;
    }

    public void setOnCancel( Runnable cancelListener ) {
        this.cancelListener = cancelListener;
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
        String cmd = e.getActionCommand();

        if (ConnectToView.CMD_OK.equals( cmd )) {
            view.exitView();
            if (null != okListener) {
                okListener.run();
            }
        } else if (ConnectToView.CMD_CANCEL.equals( cmd )) {
            view.exitView();
            if (null != cancelListener) {
                cancelListener.run();
            }
        }
    }
}
