
package de.m0ep.socc.workbench.models;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.jgoodies.binding.beans.Model;

public class ServiceGuiModel extends Model {
    public static final String PROPERTY_ENDPOINT_URI = "endpointUri";
    public static final String PROPERTY_DESCRIPTION = "description";
    public static final String PROPERTY_AUTHENTICATION_LIST = "authenticationList";

    private static final long serialVersionUID = 1L;

    private String endpointUri;
    private String description;
    private List<AuthenticationGuiModel> authenticationList;

    public ServiceGuiModel() {
        this("", "", new ArrayList<AuthenticationGuiModel>());
    }

    private ServiceGuiModel(String endpointUri, String description,
            List<AuthenticationGuiModel> authenticationList) {
        this.endpointUri = Preconditions.checkNotNull(endpointUri,
                "Required parameter endpointUri must be specified.");
        this.description = Preconditions.checkNotNull(description,
                "Required parameter description must be specified.");
        this.authenticationList = Preconditions.checkNotNull(authenticationList,
                "Required parameter authenticationList must be specified.");
    }

    public String getEndpointUri() {
        return endpointUri;
    }

    public void setEndpointUri(String endpointUri) {
        firePropertyChange(PROPERTY_ENDPOINT_URI, this.endpointUri, endpointUri);
        this.endpointUri = endpointUri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        firePropertyChange(PROPERTY_DESCRIPTION, this.description, description);
        this.description = description;
    }

    public void setAuthenticationList(List<AuthenticationGuiModel> authenticationList) {
        firePropertyChange(
                PROPERTY_AUTHENTICATION_LIST,
                this.authenticationList,
                authenticationList);
        this.authenticationList = authenticationList;
    }

    public List<AuthenticationGuiModel> getAuthenticationList() {
        return authenticationList;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                endpointUri,
                description);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }

        ServiceGuiModel other = (ServiceGuiModel) obj;

        return Objects.equal(this.endpointUri, other.endpointUri) &&
                Objects.equal(this.description, other.description);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("endpointUri", endpointUri)
                .add("description", description)
                .toString();
    }

}
