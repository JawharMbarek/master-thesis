package de.m0ep.socc.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;

public class DataForm implements Serializable {
    private static final long serialVersionUID = -7304618354516093426L;
    Map<String, DataField> fields;

    public Map<String, DataField> getFields() {
	if (null == fields) {
	    fields = new HashMap<String, DataField>();
	}

	return this.fields;
    }

    public void addField(final DataField field) {
	Preconditions.checkNotNull(field, "Field can not be null.");
	Preconditions.checkNotNull(field.getName(),
		"Field.name can not be null.");
	Preconditions.checkArgument(!field.getName().isEmpty(),
		"Field.name can not be empty.");

	getFields().put(field.getName(), field);
    }

    public DataField getField(final String name) {
	Preconditions.checkNotNull(name, "Name can not be null.");
	Preconditions.checkArgument(!name.isEmpty(), "Name can not be empty.");

	return getFields().get(name);
    }
}
