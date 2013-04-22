package de.m0ep.socc.shop.ui;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.google.common.base.Preconditions;

import de.m0ep.socc.config.DataField;
import de.m0ep.socc.config.DataForm;
import de.m0ep.socc.config.DataType;

public class DataFormPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    DataForm form;
    String title;

    Map<String, JComponent> componentMap;

    /**
     * Create the panel.
     */
    public DataFormPanel(final String title, final DataForm form) {
	this.title = Preconditions.checkNotNull(title, "Title can not be null");
	this.form = Preconditions.checkNotNull(form, "Form can not be null");

	this.componentMap = new HashMap<String, JComponent>();

	initialize();
    }

    private void initialize() {
	setBorder(BorderFactory.createTitledBorder(title));
	setLayout(new GridLayout(form.getFields().size(), 2, 0, 5));

	for (DataField field : form.getFields().values()) {
	    JComponent component = getFieldComponent(field);
	    componentMap.put(field.getName(), component);

	    add(new JLabel(field.getLabel()));
	    add(component);
	}

    }

    private JComponent getFieldComponent(DataField field) {

	if (DataType.STRING == field.getType()) {
	    return new JTextField(field.getDefaultValue().toString());
	} else if (DataType.INTEGER == field.getType()) {
	    return new JSpinner(new SpinnerNumberModel(
		    ((Number) field.getDefaultValue()).intValue(),
		    (field.isPositive()) ? (0) : (Integer.MIN_VALUE),
		    Integer.MAX_VALUE, 1));
	} else if (DataType.FLOAT == field.getType()) {
	    return new JSpinner(new SpinnerNumberModel(
		    ((Number) field.getDefaultValue()).doubleValue(),
		    (field.isPositive()) ? (0.0) : (Double.MIN_VALUE),
		    Double.MAX_VALUE, 1.0));

	} else if (DataType.BOOLEAN == field.getType()) {
	    return new JComboBox<Boolean>(new Boolean[] { Boolean.TRUE,
		    Boolean.FALSE });
	}

	throw new IllegalArgumentException("Unknown field type: "
		+ field.getType());
    }

    @SuppressWarnings("unchecked")
    private Object getFieldData(DataField field, JComponent component) {
	if (DataType.STRING == field.getType()) {
	    return ((JTextField) component).getText();
	} else if (DataType.INTEGER == field.getType()) {
	    return (Integer) ((JSpinner) component).getValue();
	} else if (DataType.FLOAT == field.getType()) {
	    return (Double) ((JSpinner) component).getValue();
	} else if (DataType.BOOLEAN == field.getType()) {
	    return (Boolean) ((JComboBox<Boolean>) component).getSelectedItem();
	}

	throw new IllegalArgumentException("Unknown field type: "
		+ field.getType());
    }

    Map<String, Object> getData() {

	Map<String, Object> result = new HashMap<String, Object>();

	for (String name : componentMap.keySet()) {
	    JComponent component = componentMap.get(name);
	    Object data = getFieldData(form.getField(name), component);
	    result.put(name, data);
	}

	return result;
    }
}
