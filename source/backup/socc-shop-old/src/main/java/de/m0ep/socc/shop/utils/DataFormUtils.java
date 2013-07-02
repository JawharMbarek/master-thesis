package de.m0ep.socc.shop.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import de.m0ep.socc.config.form.FormField;

public class DataFormUtils {
    public static Component createFieldComponent(final FormField field,
	    final Object presetValue) {
	Component result = null;
	Object defaultFieldValue = field.getDefaultValue();

	if (field.isHidden()) {
	    String value = "";

	    if (null != presetValue && presetValue instanceof String) {
		value = (String) presetValue;
	    } else if (null != defaultFieldValue
		    && defaultFieldValue instanceof String) {
		value = (String) defaultFieldValue;
	    }

	    result = new JPasswordField(value);
	} else if (FormField.Type.STRING == field.getType()) {
	    String value = "";

	    if (null != presetValue && presetValue instanceof String) {
		value = (String) presetValue;
	    } else if (null != defaultFieldValue
		    && defaultFieldValue instanceof String) {
		value = (String) defaultFieldValue;
	    }

	    result = new JTextField(value);
	} else if (FormField.Type.INTEGER == field.getType()) {
	    Integer value = (null != defaultFieldValue) ? ((Integer) defaultFieldValue)
		    : (0);

	    if (null != presetValue && presetValue instanceof Integer) {
		value = (Integer) presetValue;
	    } else if (null != defaultFieldValue
		    && defaultFieldValue instanceof Integer) {
		value = (Integer) defaultFieldValue;
	    }

	    result = new JSpinner(new SpinnerNumberModel(value
		    .intValue(),
		    (field.isPositive()) ? (0) : (Integer.MIN_VALUE),
		    Integer.MAX_VALUE, 0));
	} else if (FormField.Type.FLOAT == field.getType()) {
	    Double value = 0.0;

	    if (null != presetValue && presetValue instanceof Number) {
		value = ((Number) presetValue).doubleValue();
	    } else if (null != defaultFieldValue
		    && defaultFieldValue instanceof Number) {
		value = ((Number) defaultFieldValue).doubleValue();
	    }

	    result = new JSpinner(new SpinnerNumberModel(value
		    .doubleValue(),
		    (field.isPositive()) ? (0.0) : (Double.MIN_VALUE),
		    Double.MAX_VALUE, 1.0));

	} else if (FormField.Type.BOOLEAN == field.getType()) {
	    result = new JComboBox<Boolean>(new Boolean[] { Boolean.TRUE,
		    Boolean.FALSE });

	    Boolean value = Boolean.FALSE;

	    if (null != presetValue && presetValue instanceof Boolean) {
		value = (Boolean) presetValue;
	    } else if (null != defaultFieldValue
		    && defaultFieldValue instanceof Boolean) {
		value = (Boolean) defaultFieldValue;
	    }

	    ((JComboBox<?>) result).setSelectedItem(value);

	}

	if (null != result) {
	    result.setPreferredSize(new Dimension(4, 25));
	    result.setLocale(Locale.GERMANY);
	    return result;
	} else {
	    throw new IllegalArgumentException("Unknown field type: "
		    + field.getType());
	}
    }

    @SuppressWarnings("unchecked")
    public static Object getFieldComponentData(FormField field,
	    Component component) {
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
}
