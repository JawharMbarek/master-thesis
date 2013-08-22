/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.m0ep.socc.core.connector.facebook;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import com.google.common.base.Preconditions;

import de.m0ep.sioc.services.auth.AccessToken;
import de.m0ep.sioc.services.auth.AuthenticationMechanism;
import de.m0ep.sioc.services.auth.ClientId;
import de.m0ep.sioc.services.auth.ClientSecret;
import de.m0ep.sioc.services.auth.Credentials;
import de.m0ep.sioc.services.auth.ServicesAuthVocabulary;
import de.m0ep.socc.core.connector.DefauktClientManager;
import de.m0ep.socc.core.utils.RdfUtils;

public class FacebookClientManager extends
        DefauktClientManager<FacebookClientWrapper> {
    private ClientId clientId;
    private ClientSecret clientSecret;

    public FacebookClientManager(Service service, UserAccount defaultUserAccount)
            throws Exception {
        super(service, defaultUserAccount);
    }

    public ClientId getClientId() {
        return clientId;
    }

    public ClientSecret getClientSecret() {
        return clientSecret;
    }

    @Override
    protected void init() {
        clientId = null;
        clientSecret = null;
        de.m0ep.sioc.services.auth.Service authService =
                de.m0ep.sioc.services.auth.Service.getInstance(
                        getService().getModel(),
                        getService().getResource());

        Preconditions.checkArgument(authService.hasServiceAuthentication(),
                "The parameter service has no authentication.");

        AuthenticationMechanism authentication = authService
                .getServiceAuthentication();
        Preconditions.checkArgument(authentication.hasCredentials(),
                "The service authentication has no credentials.");

        ClosableIterator<Credentials> credIter = authentication
                .getAllCredentials();
        try {
            while (credIter.hasNext()) {
                Credentials credential = (Credentials) credIter.next();

                if (credential.hasValue()) {
                    if (RdfUtils.isType(
                            credential.getModel(),
                            credential.getResource(),
                            ServicesAuthVocabulary.ClientId)) {
                        clientId = ClientId.getInstance(
                                credential.getModel(),
                                credential.getResource());
                    } else if (RdfUtils.isType(
                            credential.getModel(),
                            credential.getResource(),
                            ServicesAuthVocabulary.ClientSecret)) {
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

    @Override
    public FacebookClientWrapper createClientFromAccount(UserAccount userAccount)
            throws Exception {
        Preconditions.checkState(null != clientId,
                "Client id missing.");
        Preconditions.checkState(null != clientSecret,
                "Client secret missing.");

        de.m0ep.sioc.services.auth.UserAccount authAccount =
                de.m0ep.sioc.services.auth.UserAccount.getInstance(
                        userAccount.getModel(),
                        userAccount.getResource());

        Preconditions.checkArgument(authAccount.hasAccountAuthentication(),
                "The paramater userAccount has no authentication");

        AuthenticationMechanism authentication = authAccount
                .getAccountAuthentication();
        Preconditions.checkArgument(authentication.hasCredentials(),
                "The authentication of the parameter userAccount " +
                        "has no credentials");

        AccessToken accessToken = null;
        ClosableIterator<Credentials> credIter = authentication
                .getAllCredentials();
        try {
            while (credIter.hasNext()) {
                Credentials credential = (Credentials) credIter.next();

                if (credential.hasValue()) {
                    if (RdfUtils.isType(
                            credential.getModel(),
                            credential.getResource(),
                            ServicesAuthVocabulary.AccessToken)) {
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
                "The authentication of the parameter userAccount has no " +
                        "accesstoken credential.");

        return new FacebookClientWrapper(clientId, clientSecret, accessToken);
    }
}
