package de.m0ep.socc.config;

import java.io.Serializable;
import java.util.Map;

import com.google.common.base.Objects;

public class SOCCConfigConnectorEntry implements Serializable {
    private static final long serialVersionUID = -1422106705758952749L;

    private String id;
    private String factoryId;
    private Map<String, Object> parameters;

    /**
     * @return the id
     */
    public String getId() {
	return this.id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
	this.id = id;
    }

    /**
     * @return the factoryId
     */
    public String getFactoryId() {
	return this.factoryId;
    }

    /**
     * @param factoryId
     *            the factoryId to set
     */
    public void setFactoryId(String factoryId) {
	this.factoryId = factoryId;
    }

    /**
     * @return the parameters
     */
    public Map<String, Object> getParameters() {
	return this.parameters;
    }

    /**
     * @param parameters
     *            the parameters to set
     */
    public void setParameters(Map<String, Object> parameters) {
	this.parameters = parameters;
    }

    @Override
    public int hashCode() {
	return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (!(obj instanceof SOCCConfigConnectorEntry)) {
	    return false;
	}

	SOCCConfigConnectorEntry other = (SOCCConfigConnectorEntry) obj;

	if (!Objects.equal(this, other)) {
	    return false;
	}

	return super.equals(obj);
    }

    @Override
    public String toString() {
	return Objects.toStringHelper(this)
		.add("id", id)
		.add("factoryId", factoryId)
		.toString();
    }
}
