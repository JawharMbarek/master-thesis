package de.m0ep.socc.shop.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.google.common.base.Preconditions;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.IConnectorFactory;
import de.m0ep.socc.config.form.DataForm;
import de.m0ep.socc.config.form.FormField;
import de.m0ep.socc.shop.SOCCShopApplication;
import de.m0ep.socc.shop.ui.components.DynamicDataFormPanel;

public class ConnectorDialog extends JDialog {
    private static final long serialVersionUID = -7606005788404878311L;

    public static final int SAVE_OPTION = 0;
    public static final int CANCEL_OPTION = 1;

    private final JPanel contentPanel = new JPanel();
    private DynamicDataFormPanel dataFormPanel = new DynamicDataFormPanel();

    private int resultOption;
    private String resultFactoryId;
    private String resultConnectorId;
    private Map<String, Object> resultParameters;

    private SOCCShopApplication app;
    private IConnector connector;

    private JComboBox<String> cboxFactory;
    private JPanel parametersPanel;
    private JTextField textConnectorId;
    private JPanel factoryPanel;
    private JPanel idPanel;

    /**
     * Create the dialog to create a connector.
     * 
     * 
     * @wbp.parser.constructor
     * 
     * @param app
     */
    public ConnectorDialog(final SOCCShopApplication app) {
	this.app = Preconditions.checkNotNull(app, "App can not be null.");

	buildGUI("Create Connector");
	initialize();
    }

    /**
     * Create the dialog to modify a connector.
     * 
     * @param app
     * 
     * @param connector
     */
    public ConnectorDialog(final SOCCShopApplication app, IConnector connector) {
	this.app = Preconditions.checkNotNull(app, "App can not be null.");
	this.connector = Preconditions.checkNotNull(connector,
		"Connector can not be null.");

	buildGUI("Modify Connector");
	initialize(connector);
    }

    /**
     * @param app
     */
    private void buildGUI(final String title) {
	setBounds(100, 100, 494, 481);
	setPreferredSize(new Dimension(400, 400));
	setTitle(title);
	getContentPane().setLayout(new BorderLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	SpringLayout contentPanelLayout = new SpringLayout();
	contentPanel.setLayout(contentPanelLayout);
	{
	    factoryPanel = new JPanel();
	    factoryPanel.setBorder(new TitledBorder(null, "Connector Factory",
		    TitledBorder.LEADING, TitledBorder.TOP, null, null));
	    factoryPanel
		    .setLayout(new BoxLayout(factoryPanel, BoxLayout.X_AXIS));
	    contentPanelLayout.putConstraint(SpringLayout.WEST, factoryPanel,
		    0, SpringLayout.WEST, contentPanel);
	    contentPanelLayout.putConstraint(SpringLayout.EAST, factoryPanel,
		    0, SpringLayout.EAST, contentPanel);
	    contentPanel.add(factoryPanel);
	    {
		cboxFactory = new JComboBox<String>();
		factoryPanel.add(cboxFactory);
		cboxFactory.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
			String factoryId = (String) e.getItem();

			if (ItemEvent.SELECTED == e.getStateChange()) {
			    IConnectorFactory factory = app.getSocc()
				    .getFactory(factoryId);

			    if (null != factory) {
				dataFormPanel.setDataForm(factory
					.getParameterForm());
			    }
			    pack();
			}
		    }
		});
	    }
	}
	{
	    idPanel = new JPanel();
	    idPanel.setBorder(new TitledBorder(null, "Connector Id",
		    TitledBorder.LEADING, TitledBorder.TOP, null, null));
	    idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.X_AXIS));
	    contentPanelLayout.putConstraint(SpringLayout.NORTH, idPanel, 6,
		    SpringLayout.SOUTH, factoryPanel);
	    contentPanelLayout.putConstraint(SpringLayout.WEST, idPanel, 0,
		    SpringLayout.WEST, factoryPanel);
	    contentPanelLayout.putConstraint(SpringLayout.EAST, idPanel, 0,
		    SpringLayout.EAST, factoryPanel);
	    contentPanel.add(idPanel);
	    {
		textConnectorId = new JTextField();
		textConnectorId.setPreferredSize(new Dimension(4, 24));
		idPanel.add(textConnectorId);
		textConnectorId.setColumns(10);
	    }
	}
	{
	    parametersPanel = new JPanel();
	    contentPanelLayout.putConstraint(SpringLayout.NORTH,
		    parametersPanel, 6, SpringLayout.SOUTH, idPanel);
	    contentPanelLayout.putConstraint(SpringLayout.WEST,
		    parametersPanel, 0, SpringLayout.WEST, idPanel);
	    contentPanelLayout.putConstraint(SpringLayout.SOUTH,
		    parametersPanel, -5, SpringLayout.SOUTH, contentPanel);
	    contentPanelLayout.putConstraint(SpringLayout.EAST,
		    parametersPanel, 0, SpringLayout.EAST, idPanel);
	    parametersPanel.setBorder(new TitledBorder(new LineBorder(
		    new Color(184, 207, 229)), "Connector Parameters",
		    TitledBorder.LEFT, TitledBorder.TOP, null, null));
	    contentPanel.add(parametersPanel);
	    parametersPanel.setLayout(new BoxLayout(parametersPanel,
		    BoxLayout.X_AXIS));
	    parametersPanel.add(dataFormPanel);
	}
	{
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	    {
		JButton okButton = new JButton("Save");
		okButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			String factoryId = (String) cboxFactory
				.getSelectedItem();
			String connectorId = textConnectorId.getText();
			Map<String, Object> parameters = dataFormPanel
				.getData();

			if (validateInput(factoryId, connectorId, parameters)) {
			    resultOption = SAVE_OPTION;
			    resultConnectorId = connectorId;
			    resultFactoryId = factoryId;
			    resultParameters = parameters;

			    closeDialog();
			} else {
			    resultConnectorId = null;
			    resultFactoryId = null;
			    resultParameters = null;
			}
		    }
		});
		okButton.setActionCommand("Save");
		okButton.setToolTipText("Save SOCC-Connector");
		buttonPanel.add(okButton);
		getRootPane().setDefaultButton(okButton);
	    }
	    {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			resultOption = CANCEL_OPTION;
			closeDialog();
		    }
		});
		cancelButton.setActionCommand("Cancel");
		cancelButton.setToolTipText("Discard this SOCC-Connector");
		buttonPanel.add(cancelButton);
	    }
	}

	pack();
    }

    private void initialize() {
	Set<String> factoryIds = app.getSocc().getFactoryIds();

	for (String id : factoryIds) {
	    cboxFactory.addItem(id);
	}
    }

    private void initialize(final IConnector connector) {
	initialize();

	IConnectorFactory factory = app.getSocc().getFactoryOfConnector(
		connector.getId());

	cboxFactory.setSelectedItem(factory.getId());
	cboxFactory.setEnabled(false);

	// TODO:
    }

    private boolean validateInput(final String factoryId, final String id,
	    final Map<String, Object> parameters) {

	if (null == factoryId) {
	    JOptionPane.showMessageDialog(ConnectorDialog.this,
		    "No Factory selected.", "Input error",
		    JOptionPane.ERROR_MESSAGE);
	    return false;
	}

	if (id.isEmpty()) {
	    JOptionPane.showMessageDialog(ConnectorDialog.this,
		    "Give the connector an unique id.", "Input error",
		    JOptionPane.ERROR_MESSAGE);
	    return false;
	}

	// duplicate id check
	try {
	    app.getSocc().getConnector(id);
	    JOptionPane.showMessageDialog(ConnectorDialog.this,
		    "There is already a connector with this id.",
		    "Input error", JOptionPane.ERROR_MESSAGE);
	    return false;
	} catch (Exception e) {
	    // Do nothing, connector with this id doesn't exists.
	}

	// validate parameters
	DataForm dataForm = app.getSocc().getFactory(factoryId)
		.getParameterForm();
	for (FormField field : dataForm.getFields()) {
	    if (parameters.containsKey(field.getVariable())) {
		Object obj = parameters.get(field.getVariable());

		if (FormField.Type.STRING == field.getType()
			|| field.isHidden()) {
		    if (obj.toString().isEmpty()) {
			JOptionPane.showMessageDialog(ConnectorDialog.this,
				"Fill the '" + field.getLabel()
					+ "' Parameter.", "Input error",
				JOptionPane.ERROR_MESSAGE);
			return false;
		    }
		} else if (FormField.Type.INTEGER == field.getType()) {
		    Integer value = null;

		    try {
			value = (Integer) obj;
		    } catch (Throwable t) {
			JOptionPane.showMessageDialog(ConnectorDialog.this,
				"Please fill '" + field.getLabel()
					+ "' with an integer.", "Input error",
				JOptionPane.ERROR_MESSAGE);
			return false;
		    }

		    if (field.isPositive() && 0 > value) {
			JOptionPane.showMessageDialog(ConnectorDialog.this,
				"Please fill '" + field.getLabel()
					+ "' with positive integers.",
				"Input error", JOptionPane.ERROR_MESSAGE);
			return false;
		    }
		} else if (FormField.Type.FLOAT == field.getType()) {
		    Double value = null;

		    try {
			value = (Double) obj;
		    } catch (Throwable t) {
			JOptionPane.showMessageDialog(ConnectorDialog.this,
				"Please fill '" + field.getLabel()
					+ "' with a floatingpoint number.",
				"Input error", JOptionPane.ERROR_MESSAGE);
			return false;
		    }

		    if (field.isPositive() && 0 > value) {
			JOptionPane
				.showMessageDialog(
					ConnectorDialog.this,
					"Please fill '"
						+ field.getLabel()
						+ "' with positive floatingpoint numbers.",
					"Input error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		    }
		}

	    } else if (field.isRequired()) {
		JOptionPane.showMessageDialog(ConnectorDialog.this,
			"Fill the '" + field.getLabel() + "' Parameter.",
			"Input error", JOptionPane.ERROR_MESSAGE);
		return false;
	    }
	}

	return true;
    }

    public int showDialog() {
	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	setModalityType(ModalityType.APPLICATION_MODAL);
	setModal(true);
	setVisible(true);

	return resultOption;
    }

    private void closeDialog() {
	setVisible(false);
	dispose();
    }

    public SOCCShopApplication getApp() {
	return this.app;
    }

    public IConnector getConnector() {
	return this.connector;
    }

    public String getFactoryId() {
	return this.resultFactoryId;
    }

    public String getConnectorId() {
	return this.resultConnectorId;
    }

    public Map<String, Object> getParameter() {
	return resultParameters;
    }
}
