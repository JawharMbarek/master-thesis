package de.m0ep.socc.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;

public class DataForm implements Serializable {
    private static final long serialVersionUID = -7304618354516093426L;
    Map<String, DataField> fieldMap = new HashMap<String, DataField>();;

    public List<DataField> getFields() {
	return new ArrayList<DataField>(this.fieldMap.values());
    }

    public void addField(final DataField field) {
	Preconditions.checkNotNull(field, "Field can not be null.");
	Preconditions.checkNotNull(field.getName(),
		"Field.name can not be null.");
	Preconditions.checkArgument(!field.getName().isEmpty(),
		"Field.name can not be empty.");

	fieldMap.put(field.getName(), field);
    }

    public DataField getField(final String name) {
	Preconditions.checkNotNull(name, "Name can not be null.");
	Preconditions.checkArgument(!name.isEmpty(), "Name can not be empty.");

	return fieldMap.get(name);
    }
}
