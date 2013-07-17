
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
import de.m0ep.socc.workbench.views.ConnectToDialogView;

public class ConnectToDialogController implements ActionListener {
    private RDFStoreConnection model;
    private ConnectToDialogView view;
    private BeanAdapter<RDFStoreConnection> beanAdapter;
    private Runnable okListener;
    private Runnable cancelListener;

    public ConnectToDialogController() {
        model = new RDFStoreConnection("http://localhost:8080/openrdf-workbench", "master-thesis");
        view = new ConnectToDialogView();

        view.getCancelButton().addActionListener(this);
        view.getOkButton().addActionListener(this);

        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (null != cancelListener) {
                    cancelListener.run();
                }
            }
        });

        view.getAnonymousLogin().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (view.getAnonymousLogin().isSelected()) {
                    view.getUsernameText().setEditable(false);
                    view.getPasswordText().setEditable(false);
                } else {
                    view.getUsernameText().setEditable(true);
                    view.getPasswordText().setEditable(true);
                }
            }
        });

        beanAdapter = new BeanAdapter<RDFStoreConnection>(model, true);

        Bindings.bind(
                view.getServerUri(),
                beanAdapter.getValueModel("serverUri"));
        Bindings.bind(
                view.getRespositoryId(),
                beanAdapter.getValueModel("repositoryId"));
        Bindings.bind(
                view.getAnonymousLogin(),
                beanAdapter.getValueModel("anonymousLogin"));
        Bindings.bind(
                view.getUsernameText(),
                beanAdapter.getValueModel("username"));
        Bindings.bind(
                view.getPasswordText(),
                beanAdapter.getValueModel("password"));
    }

    public RDFStoreConnection getModel() {
        return model;
    }

    public ConnectToDialogView getView() {
        return view;
    }

    public void run() {
        view.showView();
    }

    public void setOkListener(Runnable okListener) {
        this.okListener = okListener;
    }

    public void setCancelListener(Runnable cancelListener) {
        this.cancelListener = cancelListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (ConnectToDialogView.CMD_OK.equals(cmd)) {
            view.exitView();
            if (null != okListener) {
                okListener.run();
            }
        } else if (ConnectToDialogView.CMD_CANCEL.equals(cmd)) {
            view.exitView();
            if (null != cancelListener) {
                cancelListener.run();
            }
        }
    }
}
