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

import de.m0ep.sioc.service.auth.AccessToken;
import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.ClientId;
import de.m0ep.sioc.service.auth.ClientSecret;
import de.m0ep.sioc.service.auth.Credential;
import de.m0ep.socc.core.connector.AbstractServiceClientManager;
import de.m0ep.socc.core.utils.RdfUtils;

public class FacebookClientManaget extends AbstractServiceClientManager {
    private ClientId clientId;
    private ClientSecret clientSecret;

    public FacebookClientManaget(Service service, UserAccount defaultUserAccount) throws Exception {
        super(service, defaultUserAccount);
    }

    public ClientId getClientId() {
        return clientId;
    }

    public ClientSecret getClientSecret() {
        return clientSecret;
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

        return new FacebookClientWrapper(clientId, clientSecret, accessToken);
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
