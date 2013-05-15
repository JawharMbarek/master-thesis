package de.m0ep.socc.shop.ui.components;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

import de.m0ep.socc.config.form.DataForm;
import de.m0ep.socc.config.form.FormField;
import de.m0ep.socc.shop.utils.SpringUtilities;

public class DynamicDataFormPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private DataForm dataForm;
    private Map<String, JComponent> componentMap;

    /**
     * Create the panel.
     */
    public DynamicDataFormPanel() {
	this.componentMap = new HashMap<String, JComponent>();
	setLayout(new SpringLayout());
    }

    public void setDataForm(final DataForm dataForm) {
	this.dataForm = dataForm;
	this.removeAll();
	this.componentMap.clear();

	for (FormField field : this.dataForm.getFields()) {
	    JComponent component = createFieldComponent(field, null);
	    componentMap.put(field.getVariable(), component);

	    add(new JLabel(field.getLabel()
		    + ((field.isRequired()) ? ("*") : (""))));
	    add(component);
	}

	SpringUtilities.makeCompactGrid(this, this.dataForm.getFields().size(),
		2, 6, 6, 6, 6);
    }

    public void setDataForm(final DataForm dataForm,
	    Map<String, Object> paramaters) {
	this.dataForm = dataForm;
	this.removeAll();
	this.componentMap.clear();

	for (FormField field : this.dataForm.getFields()) {
	    Object value = paramaters.get(field.getVariable());

	    JComponent component = createFieldComponent(field, value);
	    componentMap.put(field.getVariable(), component);

	    add(new JLabel(field.getLabel()
		    + ((field.isRequired()) ? ("*") : (""))));
	    add(component);
	}

	SpringUtilities.makeCompactGrid(this, this.dataForm.getFields().size(),
		2, 6, 6, 6, 6);
    }

    private JComponent createFieldComponent(final FormField field,
	    final Object value) {
	JComponent result = null;

	Object defaultFieldValue = field.getDefaultValue();

	if (field.isHidden()) {
	    String defaultValue = "";

	    if (null != value && value instanceof String) {
		defaultValue = (String) value;
	    } else if (null != defaultFieldValue
		    && defaultFieldValue instanceof String) {
		defaultValue = (String) defaultFieldValue;
	    }

	    result = new JPasswordField(defaultValue);
	} else if (FormField.Type.STRING == field.getType()) {
	    String defaultValue = "";

	    if (null != value && value instanceof String) {
		defaultValue = (String) value;
	    } else if (null != defaultFieldValue
		    && defaultFieldValue instanceof String) {
		defaultValue = (String) defaultFieldValue;
	    }

	    result = new JTextField(defaultValue);
	} else if (FormField.Type.INTEGER == field.getType()) {
	    Integer defaultValue = (null != defaultFieldValue) ? ((Integer) defaultFieldValue)
		    : (0);

	    if (null != value && value instanceof Integer) {
		defaultValue = (Integer) value;
	    } else if (null != defaultFieldValue
		    && defaultFieldValue instanceof Integer) {
		defaultValue = (Integer) defaultFieldValue;
	    }

	    result = new JSpinner(new SpinnerNumberModel(defaultValue
		    .intValue(),
		    (field.isPositive()) ? (0) : (Integer.MIN_VALUE),
		    Integer.MAX_VALUE, 0));
	} else if (FormField.Type.FLOAT == field.getType()) {
	    Double defaultValue = 0.0;

	    if (null != value && value instanceof Number) {
		defaultValue = ((Number) value).doubleValue();
	    } else if (null != defaultFieldValue
		    && defaultFieldValue instanceof Number) {
		defaultValue = ((Number) defaultFieldValue).doubleValue();
	    }

	    result = new JSpinner(new SpinnerNumberModel(defaultValue
		    .doubleValue(),
		    (field.isPositive()) ? (0.0) : (Double.MIN_VALUE),
		    Double.MAX_VALUE, 1.0));

	} else if (FormField.Type.BOOLEAN == field.getType()) {
	    result = new JComboBox<Boolean>(new Boolean[] { Boolean.TRUE,
		    Boolean.FALSE });

	    Boolean defaultValue = Boolean.FALSE;

	    if (null != value && value instanceof Boolean) {
		defaultValue = (Boolean) value;
	    } else if (null != defaultFieldValue
		    && defaultFieldValue instanceof Boolean) {
		defaultValue = (Boolean) defaultFieldValue;
	    }

	    ((JComboBox<?>) result).setSelectedItem(defaultValue);

	}

	if (null != result) {
	    result.setPreferredSize(new Dimension(4, 24));
	    result.setLocale(Locale.GERMANY);
	    return result;
	} else {
	    throw new IllegalArgumentException("Unknown field type: "
		    + field.getType());
	}
    }

    @SuppressWarnings("unchecked")
    private Object getFieldData(FormField field, JComponent component) {
	if (field.isHidden()) {
	    return new String(((JPasswordField) component).getPassword());
	} else if (FormField.Type.STRING == field.getType()) {
	    return ((JTextField) component).getText();
	} else if (FormField.Type.INTEGER == field.getType()) {
	    return (Integer) ((JSpinner) component).getValue();
	} else if (FormField.Type.FLOAT == field.getType()) {
	    return (Double) ((JSpinner) component).getValue();
	} else if (FormField.Type.BOOLEAN == field.getType()) {
	    return (Boolean) ((JComboBox<Boolean>) component).getSelectedItem();
	}

	throw new IllegalArgumentException("Unknown field type: "
		+ field.getType());
    }

    public Map<String, Object> getData() {

	Map<String, Object> result = new HashMap<String, Object>();

	for (String name : componentMap.keySet()) {
	    JComponent component = componentMap.get(name);
	    Object data = getFieldData(dataForm.getField(name), component);
	    result.put(name, data);
	}

	return result;
    }
}
