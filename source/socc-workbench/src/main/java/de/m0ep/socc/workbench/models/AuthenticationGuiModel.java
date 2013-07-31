
package de.m0ep.socc.workbench.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class AuthenticationGuiModel implements Serializable {
    private static final long serialVersionUID = 1L;

    public static enum Type {
        DIRECT("Username/Password"),
        WEBAPI("WebAPI"),
        OAUTH("OAuth");

        private String name;

        private Type(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private List<CredentialGuiModel> credentials;

    public AuthenticationGuiModel() {
        this.credentials = Lists.newArrayList();
    }

    public AuthenticationGuiModel(List<CredentialGuiModel> credentials) {
        this.credentials = Preconditions.checkNotNull(credentials,
                "Required parameter credentials must be specified.");
    }

    public List<CredentialGuiModel> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<CredentialGuiModel> credentials) {
        this.credentials = credentials;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Arrays.hashCode(credentials.toArray()));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }

        AuthenticationGuiModel other = (AuthenticationGuiModel) obj;

        return Arrays.equals(
                this.credentials.toArray(),
                other.credentials.toArray());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("credentials", Arrays.toString(credentials.toArray()))
                .toString();
    }
}
