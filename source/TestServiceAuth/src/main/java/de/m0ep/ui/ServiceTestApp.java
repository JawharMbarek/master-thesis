package de.m0ep.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.DiffReader;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.ModelChangedListener;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.impl.NotifyingModelLayer;
import org.ontoware.rdf2go.model.node.Resource;

import de.m0ep.sioc.service.auth.Service;
import de.m0ep.ui.dialog.ServiceDialog;
import de.m0ep.ui.model.ServiceItem;

public class ServiceTestApp implements ModelChangedListener {

    private Model model;

    private JFrame frame;
    private JButton btnAddService;

    private DefaultListModel<ServiceItem> servicesListModel;
    private JList<ServiceItem> servicesList;

    /**
     * Create the application.
     */
    public ServiceTestApp(Model model) {
	initialize();

	this.model = new NotifyingModelLayer(model);
	((NotifyingModelLayer) this.model).addModelChangedListener(this);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
	frame = new JFrame();
	frame.setBounds(100, 100, 450, 300);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().setLayout(new BorderLayout(0, 0));
	frame.addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosed(WindowEvent e) {
		model.close();
	    }
	});

	servicesListModel = new DefaultListModel<ServiceItem>();
	servicesList = new JList<ServiceItem>(servicesListModel);
	frame.getContentPane().add(servicesList, BorderLayout.CENTER);

	JPanel panel = new JPanel();
	FlowLayout flowLayout = (FlowLayout) panel.getLayout();
	flowLayout.setAlignment(FlowLayout.RIGHT);
	frame.getContentPane().add(panel, BorderLayout.SOUTH);

	btnAddService = new JButton("Add Service");
	btnAddService.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		ServiceDialog dlg = new ServiceDialog();
		dlg.showDialog();
	    }
	});
	panel.add(btnAddService);
    }

    /*
     * Getter and Setter
     */

    public JFrame getFrame() {
	return frame;
    }

    /*
     * Runtime
     */

    private void updateServicesList() {
	servicesListModel.clear();

	ClosableIterator<Resource> servicesIter = Service
		.getAllInstances(model);

	while (servicesIter.hasNext()) {
	    Resource resource = (Resource) servicesIter.next();
	    servicesListModel.addElement(
		    new ServiceItem(
			    new Service(
				    model,
				    resource,
				    true)));
	}
	servicesIter.close();
    }

    public void addedStatement(Statement statement) {
	// TODO Auto-generated method stub

    }

    public void addedStatements(Iterator<? extends Statement> statements) {
	// TODO Auto-generated method stub

    }

    public void removedStatement(Statement statement) {
	// TODO Auto-generated method stub

    }

    public void removedStatements(Iterator<? extends Statement> statements) {
	// TODO Auto-generated method stub

    }

    public void performedUpdate(DiffReader diff) {
	// TODO Auto-generated method stub

    }
}
