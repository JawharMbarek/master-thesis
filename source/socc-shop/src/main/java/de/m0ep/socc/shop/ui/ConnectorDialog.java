package de.m0ep.socc.shop.ui;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.IConnectorFactory;
import de.m0ep.socc.config.DataField;
import de.m0ep.socc.config.DataType;
import de.m0ep.socc.exceptions.ConnectorException;
import de.m0ep.socc.shop.SOCCShopApplication;

public class ConnectorDialog extends JDialog {
    private static final long serialVersionUID = -7606005788404878311L;
    private static final Logger LOG = LoggerFactory
	    .getLogger(ConnectorDialog.class);

    public static final int OK_OPTION = 0;
    public static final int SAVE_OPTION = 0;
    public static final int CANCEL_OPTION = 1;

    private final JPanel contentPanel = new JPanel();
    private DataFormPanel dataFormPanel = new DataFormPanel();

    private int resultOption;
    private Map<String, Object> resultParameters;
    private IConnectorFactory resultFactory;
    private IConnector resultConnector;

    private SOCCShopApplication app;
    private JComboBox<FactoryItem> cboxFactory;
    private JPanel parametersPanel;
    private JTextField textConnectorId;
    private JPanel factoryPanel;
    private JPanel idPanel;

    /**
     * Create the dialog.
     */
    public ConnectorDialog(final SOCCShopApplication app) {
	this.app = Preconditions.checkNotNull(app, "App can not be null");

	setBounds(100, 100, 494, 481);
	setPreferredSize(new Dimension(400, 400));
	getContentPane().setLayout(new BorderLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	SpringLayout sl_contentPanel = new SpringLayout();
	contentPanel.setLayout(sl_contentPanel);
	{
	    factoryPanel = new JPanel();
	    factoryPanel.setBorder(new TitledBorder(null, "Connector Factory",
		    TitledBorder.LEADING, TitledBorder.TOP, null, null));
	    sl_contentPanel.putConstraint(SpringLayout.WEST, factoryPanel, 0,
		    SpringLayout.WEST, contentPanel);
	    sl_contentPanel.putConstraint(SpringLayout.EAST, factoryPanel, 0,
		    SpringLayout.EAST, contentPanel);
	    contentPanel.add(factoryPanel);
	    factoryPanel
		    .setLayout(new BoxLayout(factoryPanel, BoxLayout.X_AXIS));
	    {
		cboxFactory = new JComboBox<FactoryItem>();
		factoryPanel.add(cboxFactory);
		cboxFactory.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
			FactoryItem item = (FactoryItem) e.getItem();

			if (ItemEvent.SELECTED == e.getStateChange()) {
			    dataFormPanel.setDataForm(item.getFactory()
				    .getParameterForm());
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
	    sl_contentPanel.putConstraint(SpringLayout.NORTH, idPanel, 6,
		    SpringLayout.SOUTH, factoryPanel);
	    sl_contentPanel.putConstraint(SpringLayout.WEST, idPanel, 0,
		    SpringLayout.WEST, factoryPanel);
	    sl_contentPanel.putConstraint(SpringLayout.EAST, idPanel, 0,
		    SpringLayout.EAST, factoryPanel);
	    contentPanel.add(idPanel);
	    idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.X_AXIS));
	    {
		textConnectorId = new JTextField();
		textConnectorId.setPreferredSize(new Dimension(4, 24));
		idPanel.add(textConnectorId);
		textConnectorId.setColumns(10);
	    }
	}
	{
	    parametersPanel = new JPanel();
	    sl_contentPanel.putConstraint(SpringLayout.NORTH, parametersPanel,
		    6, SpringLayout.SOUTH, idPanel);
	    sl_contentPanel.putConstraint(SpringLayout.WEST, parametersPanel,
		    0, SpringLayout.WEST, idPanel);
	    sl_contentPanel.putConstraint(SpringLayout.SOUTH, parametersPanel,
		    -5, SpringLayout.SOUTH, contentPanel);
	    sl_contentPanel.putConstraint(SpringLayout.EAST, parametersPanel,
		    0, SpringLayout.EAST, idPanel);
	    parametersPanel.setBorder(new TitledBorder(new LineBorder(
		    new Color(184, 207, 229)), "Connector Parameters",
		    TitledBorder.LEFT, TitledBorder.TOP, null, null));
	    contentPanel.add(parametersPanel);
	    parametersPanel.setLayout(new BoxLayout(parametersPanel,
		    BoxLayout.X_AXIS));
	    parametersPanel.add(dataFormPanel);
	}
	{
	    JPanel buttonPane = new JPanel();
	    buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    getContentPane().add(buttonPane, BorderLayout.SOUTH);
	    {
		JButton okButton = new JButton("Save");
		okButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			FactoryItem item = (FactoryItem) cboxFactory
				.getSelectedItem();
			IConnectorFactory factory = item.getFactory();
			String id = textConnectorId.getText();
			Map<String, Object> parameters = dataFormPanel
				.getData();

			if (validateInput(factory, id, parameters)) {
			    try {
				resultConnector = factory.createConnector(
					textConnectorId.getText(),
					app.getModel(), parameters);
			    } catch (ConnectorException ex) {
				JOptionPane.showMessageDialog(
					ConnectorDialog.this, ex.getMessage(),
					"Failed to create Connector!",
					JOptionPane.ERROR_MESSAGE);
				return;
			    }

			    LOG.debug("Dataproperties:");
			    for (String key : parameters.keySet()) {
				LOG.debug("name={},\tvalue={},\ttype={}",
					new Object[] {
						key,
						parameters.get(key),
						parameters.get(key).getClass()
							.getName() });
			    }

			    resultOption = SAVE_OPTION;
			    closeDialog();
			}
		    }
		});
		okButton.setActionCommand("Save");
		buttonPane.add(okButton);
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
		buttonPane.add(cancelButton);
	    }
	}

	pack();
	initialize();
    }

    protected boolean validateInput(final IConnectorFactory factory,
	    final String id, final Map<String, Object> parameters) {

	if (null == factory) {

	}

	if (id.isEmpty()) {
	    JOptionPane.showMessageDialog(ConnectorDialog.this,
		    "Give the connector an unique id.", "Input error",
		    JOptionPane.ERROR_MESSAGE);
	    return false;
	}

	// TODO: duplicate id check

	for (DataField field : factory.getParameterForm().getFields().values()) {
	    if (parameters.containsKey(field.getName())) {
		Object obj = parameters.get(field.getName());

		if (DataType.STRING == field.getType() || field.isHidden()) {
		    if (obj.toString().isEmpty()) {
			JOptionPane.showMessageDialog(ConnectorDialog.this,
				"Fill the '" + field.getLabel()
					+ "' Parameter.", "Input error",
				JOptionPane.ERROR_MESSAGE);
			return false;
		    }
		} else if (DataType.INTEGER == field.getType()) {
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
		} else if (DataType.FLOAT == field.getType()) {
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

    private void initialize() {
	Set<String> factoryIds = app.getSocc().getFactoryIds();

	for (String id : factoryIds) {
	    cboxFactory.addItem(new FactoryItem(app.getSocc().getFactory(id)));
	}
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

    public IConnectorFactory getFactory() {
	return this.resultFactory;
    }

    public IConnector getConnector() {
	return this.resultConnector;
    }

    public Map<String, Object> getData() {
	return resultParameters;
    }

    private static class FactoryItem {
	private IConnectorFactory factory;

	public FactoryItem(final IConnectorFactory factory) {
	    this.factory = factory;
	}

	public IConnectorFactory getFactory() {
	    return this.factory;
	}

	@Override
	public String toString() {
	    return factory.getConnectorName();
	}
    }
}
