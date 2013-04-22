package de.m0ep.socc.shop.ui;

import java.awt.BorderLayout;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.IConnectorFactory;
import de.m0ep.socc.config.DataField;
import de.m0ep.socc.config.DataForm;
import de.m0ep.socc.config.DataType;
import de.m0ep.socc.shop.SOCCShopApplication;

public class CreateConnectorDialog extends JDialog {
    private static final long serialVersionUID = -7606005788404878311L;
    private static final Logger LOG = LoggerFactory
	    .getLogger(CreateConnectorDialog.class);

    public static final int OK_OPTION = 0;
    public static final int SAVE_OPTION = 0;
    public static final int CANCEL_OPTION = 1;

    private final JPanel contentPanel = new JPanel();
    private JPanel dynamicPanel = new JPanel();

    private int returnOption;
    private Map<String, Object> returnData;

    private IConnectorFactory factory;
    private IConnector connector;

    private SOCCShopApplication app;
    private JComboBox<FactoryItem> cboxFactory;

    /**
     * Create the dialog.
     */
    public CreateConnectorDialog(final SOCCShopApplication app) {
	this.app = Preconditions.checkNotNull(app, "App can not be null");

	setBounds(100, 100, 501, 332);
	setPreferredSize(new Dimension(500, 250));
	getContentPane().setLayout(new BorderLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	contentPanel
		.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
	{
	    JLabel lblNewLabel = new JLabel("Factory");
	    lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
	    contentPanel.add(lblNewLabel);
	}
	{
	    cboxFactory = new JComboBox<FactoryItem>();
	    cboxFactory.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
		    FactoryItem item = (FactoryItem) e.getItem();

		    if (ItemEvent.SELECTED == e.getStateChange()) {
			contentPanel.remove(dynamicPanel);

			// TODO: create IConnectorFactory#getDataForm()
			DataForm form = new DataForm();
			form.addField(new DataField.Builder().setName("test1")
				.setLabel("Test1 Field")
				.setType(DataType.BOOLEAN)
				.setDefaultValue(Boolean.TRUE).create());
			form.addField(new DataField.Builder().setName("test2")
				.setLabel("Test2 Field")
				.setType(DataType.STRING)
				.setDefaultValue("Hallo, Welt!").create());
			form.addField(new DataField.Builder().setName("test3")
				.setLabel("Test3 Field")
				.setType(DataType.INTEGER)
				.setDefaultValue(new Integer(1337)).create());
			form.addField(new DataField.Builder().setName("test4")
				.setLabel("Test4 Field")
				.setType(DataType.FLOAT)
				.setDefaultValue(new Float(1337)).create());

			dynamicPanel = new DataFormPanel(item.toString()
				+ " Properties", form);
			contentPanel.add(dynamicPanel);
			pack();
		    }
		}
	    });
	    contentPanel.add(cboxFactory);
	}
	{
	    contentPanel.add(dynamicPanel);
	}
	{
	    JPanel buttonPane = new JPanel();
	    buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    getContentPane().add(buttonPane, BorderLayout.SOUTH);
	    {
		JButton okButton = new JButton("Save");
		okButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			returnOption = SAVE_OPTION;

			if (dynamicPanel instanceof DataFormPanel) {
			    DataFormPanel formPanel = (DataFormPanel) dynamicPanel;
			    returnData = formPanel.getData();

			    LOG.debug("Dataproperties:");
			    for (String id : returnData.keySet()) {
				LOG.debug("name={},\tvalue={},\ttype={}",
					new Object[] {
						id,
						returnData.get(id),
						returnData.get(id).getClass()
							.getName() });
			    }

			}

			closeDialog();
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
			returnOption = CANCEL_OPTION;
			closeDialog();
		    }
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	    }
	}

	initialize();
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

	return returnOption;
    }

    private void closeDialog() {
	setVisible(false);
	dispose();
    }

    public SOCCShopApplication getApp() {
	return this.app;
    }

    public IConnectorFactory getFactory() {
	return this.factory;
    }

    public IConnector getConnector() {
	return this.connector;
    }

    public Map<String, Object> getData() {
	return returnData;
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
