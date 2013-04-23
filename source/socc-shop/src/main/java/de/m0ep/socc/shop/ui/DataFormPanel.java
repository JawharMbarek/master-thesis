package de.m0ep.socc.shop.ui;

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

import de.m0ep.socc.config.DataField;
import de.m0ep.socc.config.DataForm;
import de.m0ep.socc.config.DataType;
import de.m0ep.socc.shop.utils.SpringUtilities;

public class DataFormPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    DataForm dataForm;
    Map<String, JComponent> componentMap;

    /**
     * Create the panel.
     */
    public DataFormPanel() {
	this.componentMap = new HashMap<String, JComponent>();

	setLayout(new SpringLayout());
    }

    private JComponent getFieldComponent(DataField field) {
	JComponent result = null;

	Object defObj = field.getDefaultValue();

	if (field.isHidden()) {
	    String def = (null != defObj) ? ((String) defObj) : ("");
	    result = new JPasswordField(def);
	} else if (DataType.STRING == field.getType()) {
	    String def = (null != defObj) ? ((String) defObj) : ("");
	    result = new JTextField(def);
	} else if (DataType.INTEGER == field.getType()) {
	    Integer def = (null != defObj) ? ((Integer) defObj) : (0);
	    result = new JSpinner(new SpinnerNumberModel(def.intValue(),
		    (field.isPositive()) ? (0) : (Integer.MIN_VALUE),
		    Integer.MAX_VALUE, 0));
	} else if (DataType.FLOAT == field.getType()) {
	    Double def = (null != defObj) ? ((Double) defObj) : (0.0);
	    result = new JSpinner(new SpinnerNumberModel(def.doubleValue(),
		    (field.isPositive()) ? (0.0) : (Double.MIN_VALUE),
		    Double.MAX_VALUE, 1.0));

	} else if (DataType.BOOLEAN == field.getType()) {
	    result = new JComboBox<Boolean>(new Boolean[] { Boolean.TRUE,
		    Boolean.FALSE });

	    Boolean def = (null != defObj) ? ((Boolean) defObj)
		    : (Boolean.FALSE);
	    ((JComboBox<?>) result).setSelectedItem(def);
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

    public void setDataForm(final DataForm dataForm) {
	this.dataForm = dataForm;
	this.removeAll();
	this.componentMap.clear();

	for (DataField field : this.dataForm.getFields().values()) {
	    JComponent component = getFieldComponent(field);
	    componentMap.put(field.getName(), component);

	    add(new JLabel(field.getLabel()
		    + ((field.isRequired()) ? ("*") : (""))));
	    add(component);
	}

	SpringUtilities.makeCompactGrid(this, this.dataForm.getFields().size(),
		2, 6, 6, 6, 6);
    }

    @SuppressWarnings("unchecked")
    private Object getFieldData(DataField field, JComponent component) {
	if (field.isHidden()) {
	    return new String(((JPasswordField) component).getPassword());
	} else if (DataType.STRING == field.getType()) {
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
	    Object data = getFieldData(dataForm.getField(name), component);
	    result.put(name, data);
	}

	return result;
    }
}
