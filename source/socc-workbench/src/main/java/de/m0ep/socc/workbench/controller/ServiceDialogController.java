
package de.m0ep.socc.workbench.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.beans.BeanAdapter;

import de.m0ep.socc.workbench.models.ServiceGuiModel;
import de.m0ep.socc.workbench.views.ServiceDialogView;

public class ServiceDialogController implements ActionListener {

    private ServiceGuiModel model;
    private ServiceDialogView view;

    private BeanAdapter<ServiceGuiModel> beanAdapter;

    private Runnable okListener;
    private Runnable cancelListener;

    public ServiceDialogController() {
        this.model = new ServiceGuiModel();
        this.view = new ServiceDialogView();

        initView();
    }

    public ServiceDialogView getView() {
        return view;
    }

    public ServiceGuiModel getModel() {
        return model;
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

    public void initView() {
        view.getAddAuthenticationButton().addActionListener(this);
        view.getOkButton().addActionListener(this);
        view.getCancelButton().addActionListener(this);

        this.beanAdapter = new BeanAdapter<ServiceGuiModel>(model, true);
        Bindings.bind(
                view.getServiceEndpointUri(),
                beanAdapter.getValueModel(ServiceGuiModel.PROPERTY_ENDPOINT_URI));
        Bindings.bind(
                view.getDescription(),
                beanAdapter.getValueModel(ServiceGuiModel.PROPERTY_DESCRIPTION));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (ServiceDialogView.ACTION_ADD_AUTHENTICATION.equals(cmd)) {

        } else if (ServiceDialogView.ACTION_OK.equals(cmd)) {
            view.exitView();
            if (null != okListener) {
                okListener.run();
            }
        } else if (ServiceDialogView.ACTION_CANCEL.equals(cmd)) {
            view.exitView();
            if (null != cancelListener) {
                cancelListener.run();
            }
        }
    }
}
