
package de.m0ep.socc.core.connector.canvaslms;

import java.io.IOException;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.UserAccount;

import com.google.common.base.Preconditions;

import de.m0ep.canvas.CanvasClient;
import de.m0ep.sioc.service.auth.AccessToken;
import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.Credential;
import de.m0ep.socc.core.connector.IConnectorClient;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.utils.RDF2GoUtils;

public class CanvasLmsConnectorClient implements IConnectorClient<CanvasClient> {
    private AccessToken accessToken;
    private CanvasClient client;
    private UserAccount userAccount;

    public CanvasLmsConnectorClient(final URI serviceEndpointUri, final UserAccount userAccount) {
        de.m0ep.sioc.service.auth.UserAccount authUserAccount =
                de.m0ep.sioc.service.auth.UserAccount.getInstance(
                        userAccount.getModel(),
                        userAccount.getResource());

        Preconditions.checkArgument(
                authUserAccount.hasAuthentication(),
                "The userAccount has no authentication data.");
        Authentication authentication = authUserAccount.getAuthentication();

        Preconditions.checkArgument(
                authentication.hasCredential(),
                "The authentication has no credentials");
        ClosableIterator<Credential> credentialIter = authentication.getAllCredential();

        while (credentialIter.hasNext()) {
            Credential credential = (Credential) credentialIter.next();
            URI type = RDF2GoUtils.getType(credential.getModel(), credential.getResource());

            if (AccessToken.RDFS_CLASS.equals(type) && credential.hasValue()) {
                accessToken = AccessToken.getInstance(
                        credential.getModel(),
                        credential.getResource());

                client = new CanvasClient(
                        serviceEndpointUri.toString(),
                        accessToken.getValue());
            }
        }

        if (null == accessToken) {
            throw new IllegalArgumentException("No access token found in userAccount");
        }

        this.userAccount = userAccount;
    }

    public CanvasClient getClient() {
        return client;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void recoverClient() throws AuthenticationException, IOException {
        throw new UnsupportedOperationException("recoverClient is not supported.");
    }
}
