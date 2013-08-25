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

package de.m0ep.socc.core.connector.google.plus;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import com.google.common.base.Preconditions;

import de.m0ep.sioc.services.auth.AccessToken;
import de.m0ep.sioc.services.auth.AuthenticationMechanism;
import de.m0ep.sioc.services.auth.ClientId;
import de.m0ep.sioc.services.auth.ClientSecret;
import de.m0ep.sioc.services.auth.Credentials;
import de.m0ep.sioc.services.auth.RefreshToken;
import de.m0ep.sioc.services.auth.ServicesAuthVocabulary;
import de.m0ep.socc.core.connector.DefauktClientManager;
import de.m0ep.socc.core.utils.RdfUtils;

public class GooglePlusClientManager extends
        DefauktClientManager<GooglePlusClientWrapper> {

    private ClientId clientId;
    private ClientSecret clientSecret;

    public GooglePlusClientManager(Service service,
            UserAccount defaultUserAccount)
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
        de.m0ep.sioc.services.auth.Service authService =
                de.m0ep.sioc.services.auth.Service.getInstance(
                        getService().getModel(),
                        getService().getResource());

        Preconditions.checkArgument(authService.hasServiceAuthentication(),
                "The service has no authentication");

        ClosableIterator<AuthenticationMechanism> authIter =
                authService.getAllServiceAuthentication();

        try {
            while (authIter.hasNext()) {
                AuthenticationMechanism authentication =
                        (AuthenticationMechanism) authIter.next();

                if (authentication.hasCredentials()) {
                    ClosableIterator<Credentials> credIter = authentication
                            .getAllCredentials();
                    try {
                        clientId = null;
                        clientSecret = null;
                        while (credIter.hasNext()) {
                            Credentials credentials = (Credentials) credIter
                                    .next();

                            if (credentials.hasValue()) {
                                if (RdfUtils.isType(
                                        credentials.getModel(),
                                        credentials.getResource(),
                                        ServicesAuthVocabulary.ClientId)) {
                                    clientId = ClientId.getInstance(
                                            credentials.getModel(),
                                            credentials.getResource());
                                } else if (RdfUtils.isType(
                                        credentials.getModel(),
                                        credentials.getResource(),
                                        ServicesAuthVocabulary.ClientSecret)) {
                                    clientSecret = ClientSecret.getInstance(
                                            credentials.getModel(),
                                            credentials.getResource());
                                }
                            }
                        }
                    } finally {
                        credIter.close();
                    }

                    if (null != clientId && null != clientSecret) {
                        break;
                    }
                }
            }
        } finally {
            authIter.close();
        }

        Preconditions.checkArgument(null != clientId,
                "No client id found in the authentications of the service");

        Preconditions.checkArgument(null != clientSecret,
                "No client secret found in the authentications of the service");
    }

    @Override
    public GooglePlusClientWrapper createClientFromAccount(
            UserAccount userAccount)
            throws Exception {

        Preconditions.checkNotNull(userAccount,
                "Required parameter userAccount must be specified.");

        de.m0ep.sioc.services.auth.UserAccount authAccount =
                de.m0ep.sioc.services.auth.UserAccount.getInstance(
                        userAccount.getModel(),
                        userAccount.getResource());

        Preconditions.checkArgument(authAccount.hasAccountAuthentication(),
                "The parameter userAccount has no authentications.");

        ClosableIterator<AuthenticationMechanism> authIter =
                authAccount.getAllAccountAuthentication();

        AccessToken accessToken = null;
        RefreshToken refreshToken = null;

        try {
            while (authIter.hasNext()) {
                AuthenticationMechanism authentication =
                        (AuthenticationMechanism) authIter.next();

                if (authentication.hasCredentials()) {
                    ClosableIterator<Credentials> credIter = authentication
                            .getAllCredentials();
                    try {
                        accessToken = null;
                        refreshToken = null;
                        while (credIter.hasNext()) {
                            Credentials credentials = (Credentials) credIter
                                    .next();

                            if (credentials.hasValue()) {
                                if (RdfUtils.isType(
                                        credentials.getModel(),
                                        credentials.getResource(),
                                        ServicesAuthVocabulary.AccessToken)) {
                                    accessToken = AccessToken.getInstance(
                                            credentials.getModel(),
                                            credentials.getResource());
                                } else if (RdfUtils.isType(
                                        credentials.getModel(),
                                        credentials.getResource(),
                                        ServicesAuthVocabulary.RefreshToken)) {
                                    refreshToken = RefreshToken.getInstance(
                                            credentials.getModel(),
                                            credentials.getResource());
                                }
                            }
                        }
                    } finally {
                        credIter.close();
                    }

                    if (null != accessToken && null != refreshToken) {
                        break;
                    }
                }
            }
        } finally {
            authIter.close();
        }

        Preconditions.checkArgument(null != accessToken,
                "No accesstoken found in the authentications of " +
                        "the userAccount");

        Preconditions.checkArgument(null != refreshToken,
                "No refreshtoken secret found in the authentications of " +
                        "the userAccount");

        return new GooglePlusClientWrapper(
                clientId,
                clientSecret,
                accessToken,
                refreshToken);
    }
}
