
package de.m0ep.socc.workbench.models;

import com.google.common.base.Objects;
import com.jgoodies.binding.beans.Model;

public class RDFStoreConnection extends Model {
    public static final String PROPERTY_SERVER_URI = "serverUri";
    public static final String PROPERTY_REPOSITORY_ID = "repositoryId";
    public static final String PROPERTY_ANONYMOUS_LOGIN = "anonymousLogin";
    public static final String PROPERTY_USERNAME = "username";
    public static final String PROPERTY_PASSWORD = "password";

    private static final long serialVersionUID = 1L;

    private String serverUri;
    private String repositoryId;
    private boolean anonymousLogin;
    private String username;
    private String password;

    public RDFStoreConnection() {
        this.serverUri = null;
        this.repositoryId = null;
        this.anonymousLogin = false;
        this.username = null;
        this.password = null;
    }

    public RDFStoreConnection(String serverUrl, String repositoryId) {
        this.serverUri = serverUrl;
        this.repositoryId = repositoryId;
        this.anonymousLogin = true;
        this.username = null;
        this.password = null;
    }

    public String getServerUri() {
        return serverUri;
    }

    public void setServerUri(String serverUri) {
        firePropertyChange("serverUri", this.serverUri, serverUri);
        this.serverUri = serverUri;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        firePropertyChange("repositoryId", this.repositoryId, repositoryId);
        this.repositoryId = repositoryId;
    }

    public boolean isAnonymousLogin() {
        return anonymousLogin;
    }

    public void setAnonymousLogin(boolean isAnonymousLogin) {
        firePropertyChange("anonymousLogin", this.anonymousLogin, isAnonymousLogin);
        this.anonymousLogin = isAnonymousLogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        firePropertyChange("username", this.username, username);
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        firePropertyChange("password", this.password, password);
        this.password = password;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(serverUri, repositoryId, anonymousLogin, username, password);
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        RDFStoreConnection other = (RDFStoreConnection) obj;

        return Objects.equal(this.serverUri, other.serverUri) &&
                Objects.equal(this.repositoryId, other.repositoryId) &&
                Objects.equal(this.anonymousLogin, other.anonymousLogin) &&
                Objects.equal(this.username, other.username) &&
                Objects.equal(this.password, other.password);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("serverUri", serverUri)
                .add("repositoryId", repositoryId)
                .add("isAnonymousLogin", anonymousLogin)
                .add("username", username)
                .toString();
    }

}
