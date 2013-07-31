
package de.m0ep.socc.workbench.models;

import java.io.Serializable;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class CredentialGuiModel implements Serializable {
    private static final long serialVersionUID = 1L;

    public static enum Type {
        UNKNOWN("Unknown"),
        API_KEY("Api Key"),
        USERNAME("Username"),
        PASSWORD("Password"),
        CLIENT_ID("Client ID"),
        CLIENT_SECRET("Client Secret"),
        ACCESS_TOKEN("Access Token"),
        REFRESH_TOKEN("Refresh Token");

        private String name;

        private Type(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private Type type;
    private String value;

    public CredentialGuiModel() {
        this(Type.UNKNOWN, "");
    }

    public CredentialGuiModel(Type type, String value) {
        this.type = Preconditions.checkNotNull(type,
                "Required parameter type must be specified.");
        this.value = Preconditions.checkNotNull(value,
                "Required parameter value must be specified.");
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = Preconditions.checkNotNull(type,
                "Required parameter type must be specified.");
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = Preconditions.checkNotNull(value,
                "Required parameter value must be specified.");
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }

        CredentialGuiModel other = (CredentialGuiModel) obj;

        return Objects.equal(this.type, other.type) &&
                Objects.equal(this.value, other.value);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("value", value)
                .toString();
    }
}
