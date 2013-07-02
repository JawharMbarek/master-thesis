package de.m0ep.socc.shop.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.google.common.base.Preconditions;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.IConnectorFactory;
import de.m0ep.socc.config.form.DataForm;
import de.m0ep.socc.config.form.FormField;
import de.m0ep.socc.shop.SOCCShopApplication;
import de.m0ep.socc.shop.utils.DataFormUtils;

public class ConnectorDialog extends JDialog {
    private static final long serialVersionUID = -7606005788404878311L;

    public static final int SAVE_OPTION = 0;
    public static final int CANCEL_OPTION = 1;

    private final JPanel contentPanel = new JPanel();

    private int resultOption;
    private String resultFactoryId;
    private String resultConnectorId;
    private Map<String, Object> resultParameters;

    private SOCCShopApplication app;
    private IConnector connector;

    private JTextField txtConnectorId;
    private JComboBox<String> cbxFactory;
    private JPanel parameterPanel;

    private Map<FormField, Component> fieldComponentMap = new HashMap<FormField, Component>();

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
	setBounds(100, 100, 394, 204);
	setTitle(title);
	getContentPane().setLayout(new BorderLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
		FormFactory.RELATED_GAP_COLSPEC,
		FormFactory.DEFAULT_COLSPEC,
		FormFactory.RELATED_GAP_COLSPEC,
		ColumnSpec.decode("default:grow"),
		FormFactory.RELATED_GAP_COLSPEC, },
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
			FormFactory.DEFAULT_ROWSPEC,
			FormFactory.RELATED_GAP_ROWSPEC,
			RowSpec.decode("default:grow"), }));
	{
	    JLabel lblFactory = new JLabel("Factory:");
	    contentPanel.add(lblFactory, "2, 2, right, default");
	}
	{
	    cbxFactory = new JComboBox<String>();
	    contentPanel.add(cbxFactory, "4, 2, fill, default");

	    cbxFactory.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
		    String factoryId = (String) e.getItem();

		    if (ItemEvent.SELECTED == e.getStateChange()) {
			IConnectorFactory factory = app.getSocc()
				.getFactory(factoryId);

			if (null != factory) {
			    updateParameterPanel(factory.getParameterForm());
			}
		    }
		}
	    });
	}
	{
	    JLabel lblId = new JLabel("Id:");
	    contentPanel.add(lblId, "2, 4, right, default");
	}
	{
	    txtConnectorId = new JTextField();
	    txtConnectorId.setPreferredSize(new Dimension(4, 25));
	    txtConnectorId.setMinimumSize(new Dimension(4, 25));
	    contentPanel.add(txtConnectorId, "4, 4, fill, default");
	    txtConnectorId.setColumns(10);
	}
	{
	    JSeparator separator = new JSeparator();
	    contentPanel.add(separator, "2, 6, 3, 1");
	}
	{
	    JLabel lblParameters = new JLabel("Parameters");
	    lblParameters.setHorizontalAlignment(SwingConstants.CENTER);
	    contentPanel.add(lblParameters, "2, 8, 3, 1");
	}
	{
	    parameterPanel = new JPanel();
	    contentPanel.add(parameterPanel, "2, 10, 3, 1, fill, fill");
	}
	{
	    JLabel lblParametersMarkedWith = new JLabel(
		    "Parameters marked by a '*' are requires");
	    lblParametersMarkedWith
		    .setHorizontalAlignment(SwingConstants.RIGHT);
	    lblParametersMarkedWith.setFont(new Font("Dialog", Font.ITALIC, 9));
	    lblParametersMarkedWith.setVerticalAlignment(SwingConstants.TOP);
	    contentPanel.add(lblParametersMarkedWith, "2, 12, 3, 1");
	}
	{
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	    {
		JButton okButton = new JButton("Save");
		okButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			String factoryId = (String) cbxFactory
				.getSelectedItem();
			String connectorId = txtConnectorId.getText();
			Map<String, Object> parameters = getParameterFromPanel();

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

    protected void updateParameterPanel(DataForm form) {
	updateParameterPanel(form, null);
    }

    protected void updateParameterPanel(DataForm form,
	    Map<String, Object> parameters) {

	FormLayout layout = new FormLayout(
		new ColumnSpec[] {
			FormFactory.DEFAULT_COLSPEC,
			FormFactory.RELATED_GAP_COLSPEC,
			ColumnSpec.decode("default:grow")
		});

	parameterPanel.removeAll();
	parameterPanel.setLayout(layout);
	fieldComponentMap.clear();

	DefaultFormBuilder builder = new DefaultFormBuilder(
		layout,
		parameterPanel);

	for (FormField field : form.getFields()) {
	    Object value = null;

	    if (null != parameters) {
		value = parameters.get(field.getVariable());
	    }

	    JLabel label = new JLabel(
		    field.getLabel() + ((field.isRequired()) ? ("*") : (""))
			    + ":");
	    label.setHorizontalAlignment(JLabel.RIGHT);
	    if (null != field.getDescription()) {
		label.setToolTipText(field.getDescription());
	    }

	    Component component = DataFormUtils.createFieldComponent(
		    field,
		    value);

	    fieldComponentMap.put(field, component);
	    builder.append(label, component);
	}

	parameterPanel.validate();
	parameterPanel.repaint();
	contentPanel.validate();
	contentPanel.repaint();

	pack();
    }

    private Map<String, Object> getParameterFromPanel() {
	Map<String, Object> result = new HashMap<String, Object>();

	for (Entry<FormField, Component> entry : fieldComponentMap.entrySet()) {
	    FormField field = entry.getKey();
	    Component component = entry.getValue();

	    result.put(
		    field.getVariable(),
		    DataFormUtils.getFieldComponentData(
			    field,
			    component));
	}

	return result;
    }

    private void initialize() {
	Set<String> factoryIds = app.getSocc().getFactoryIds();

	for (String id : factoryIds) {
	    cbxFactory.addItem(id);
	}
    }

    private void initialize(final IConnector connector) {
	initialize();

	IConnectorFactory factory = app.getSocc().getFactoryOfConnector(
		connector.getId());

	cbxFactory.setSelectedItem(factory.getId());
	cbxFactory.setEnabled(false);

	// TODO: fill form to edit stuff
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
