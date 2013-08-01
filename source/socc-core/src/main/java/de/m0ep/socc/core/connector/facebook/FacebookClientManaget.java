
package de.m0ep.socc.core.connector.facebook;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.restfb.DefaultFacebookClient;

import de.m0ep.sioc.service.auth.AccessToken;
import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.ClientId;
import de.m0ep.sioc.service.auth.ClientSecret;
import de.m0ep.sioc.service.auth.Credential;
import de.m0ep.socc.core.connector.AbstractServiceClientManager;
import de.m0ep.socc.core.utils.RdfUtils;

public class FacebookClientManaget extends AbstractServiceClientManager {
    private static final Logger LOG = LoggerFactory.getLogger(FacebookClientManaget.class);

    private ClientId clientId;
    private ClientSecret clientSecret;

    public FacebookClientManaget(Service service, UserAccount defaultUserAccount) throws Exception {
        super(service, defaultUserAccount);
        extractServiceAuthentication();
    }

    @Override
    public Object createClientFromAccount(UserAccount userAccount) throws Exception {
        if (null == clientId || null == clientSecret) {
            extractServiceAuthentication();
        }

        de.m0ep.sioc.service.auth.UserAccount authAccount =
                de.m0ep.sioc.service.auth.UserAccount.getInstance(
                        userAccount.getModel(),
                        userAccount.getResource());

        Preconditions.checkArgument(authAccount.hasAuthentication(),
                "The paramater userAccount has no authentication");

        Authentication authentication = authAccount.getAuthentication();
        Preconditions.checkArgument(authentication.hasCredential(),
                "The authentication of the parameter userAccount has no credentials");

        AccessToken accessToken = null;
        ClosableIterator<Credential> credIter = authentication.getAllCredential();
        try {
            while (credIter.hasNext()) {
                Credential credential = (Credential) credIter.next();

                if (credential.hasValue()) {
                    if (RdfUtils.isType(
                            credential.getModel(),
                            credential.getResource(),
                            AccessToken.RDFS_CLASS)) {
                        accessToken = AccessToken.getInstance(
                                credential.getModel(),
                                credential.getResource());
                    }
                }

            }
        } finally {
            credIter.close();
        }

        Preconditions.checkArgument(null != accessToken,
                "The authentication of the parameter userAccount has no accesstoken credential.");

        try {
            DefaultFacebookClient client = new DefaultFacebookClient();
            com.restfb.FacebookClient.AccessToken extendedToken = client.obtainExtendedAccessToken(
                    clientId.getValue(),
                    clientSecret.getValue(),
                    accessToken.getValue());

            // update accessToken
            accessToken.setValue(extendedToken.getAccessToken());
        } catch (Exception e) {
            LOG.warn("Failed to obtain an extended accesstoken: {}", e.getMessage());
        }

        return new DefaultFacebookClient(accessToken.getValue());
    }

    private void extractServiceAuthentication() {
        clientId = null;
        clientSecret = null;
        de.m0ep.sioc.service.auth.Service authService =
                de.m0ep.sioc.service.auth.Service.getInstance(
                        getService().getModel(),
                        getService().getResource());

        Preconditions.checkArgument(authService.hasAuthentication(),
                "The parameter service has no authentication.");

        Authentication authentication = authService.getAuthentication();
        Preconditions.checkArgument(authentication.hasCredential(),
                "The service authentication has no credentials.");

        ClosableIterator<Credential> credIter = authentication.getAllCredential();
        try {
            while (credIter.hasNext()) {
                Credential credential = (Credential) credIter.next();

                if (credential.hasValue()) {
                    if (RdfUtils.isType(
                            credential.getModel(),
                            credential.getResource(),
                            ClientId.RDFS_CLASS)) {
                        clientId = ClientId.getInstance(
                                credential.getModel(),
                                credential.getResource());
                    } else if (RdfUtils.isType(
                            credential.getModel(),
                            credential.getResource(),
                            ClientSecret.RDFS_CLASS)) {
                        clientSecret = ClientSecret.getInstance(
                                credential.getModel(),
                                credential.getResource());
                    }
                }
            }
        } finally {
            credIter.close();
        }

        Preconditions.checkArgument(null != clientId,
                "The service contains no authentication with a clientid.");

        Preconditions.checkArgument(null != clientSecret,
                "The service contains no authentication with a clientsecret.");
    }
}
