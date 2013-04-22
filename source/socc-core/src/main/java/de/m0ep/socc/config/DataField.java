package de.m0ep.socc.config;

import java.io.Serializable;

public class DataField implements Serializable {
    private static final long serialVersionUID = 1394551120060205230L;

    private String name;
    private String label;
    private Object defaultValue;
    private DataType type;

    private boolean hidden;
    private boolean required;
    private boolean positive;

    public DataField() {
	this.type = DataType.STRING;
	this.defaultValue = null;

	this.hidden = false;
	this.required = false;
	this.positive = false;
    }

    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getLabel() {
	return this.label;
    }

    public void setLabel(String label) {
	this.label = label;
    }

    public Object getDefaultValue() {
	return this.defaultValue;
    }

    public void setDefaultValue(Object value) {
	this.defaultValue = value;
    }

    public DataType getType() {
	return this.type;
    }

    public void setType(DataType type) {
	this.type = type;
    }

    public boolean isHidden() {
	return this.hidden;
    }

    public void setHidden(boolean hidden) {
	this.hidden = hidden;
    }

    public boolean isRequired() {
	return this.required;
    }

    public void setRequired(boolean required) {
	this.required = required;
    }

    public boolean isPositive() {
	return this.positive;
    }

    public void setPositive(boolean positive) {
	this.positive = positive;
    }

    public static class Builder {
	DataField field;

	public Builder() {
	    field = new DataField();
	}

	public Builder setName(String name) {
	    this.field.setName(name);
	    return this;
	}

	public Builder setLabel(String label) {
	    this.field.setLabel(label);
	    return this;
	}

	public Builder setDefaultValue(Object value) {
	    this.field.setDefaultValue(value);
	    return this;
	}

	public Builder setType(DataType type) {
	    this.field.setType(type);
	    return this;
	}

	public Builder isHidden() {
	    this.field.setHidden(true);
	    return this;
	}

	public Builder isRequired() {
	    this.field.setRequired(true);
	    return this;
	}

	public Builder isPositive() {
	    this.field.setPositive(true);
	    return this;
	}

	public DataField create() {
	    return field;
	}
    }
}
