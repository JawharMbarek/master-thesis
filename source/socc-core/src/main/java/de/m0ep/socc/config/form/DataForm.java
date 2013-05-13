package de.m0ep.socc.config.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;

public class DataForm implements Serializable {
    private static final long serialVersionUID = -7304618354516093426L;
    Map<String, FormField> fieldMap = new HashMap<String, FormField>();;

    public List<FormField> getFields() {
	return new ArrayList<FormField>(this.fieldMap.values());
    }

    public void addField(final FormField field) {
	Preconditions.checkNotNull(field, "Field can not be null.");
	Preconditions.checkNotNull(field.getVariable(),
		"Field.name can not be null.");
	Preconditions.checkArgument(!field.getVariable().isEmpty(),
		"Field.name can not be empty.");

	fieldMap.put(field.getVariable(), field);
    }

    public FormField getField(final String name) {
	Preconditions.checkNotNull(name, "Name can not be null.");
	Preconditions.checkArgument(!name.isEmpty(), "Name can not be empty.");

	return fieldMap.get(name);
    }
}
