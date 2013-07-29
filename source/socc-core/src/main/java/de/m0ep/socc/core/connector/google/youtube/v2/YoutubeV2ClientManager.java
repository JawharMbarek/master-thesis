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

package de.m0ep.socc.core.connector.google.youtube.v2;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import com.google.common.base.Preconditions;

import de.m0ep.sioc.service.auth.APIKey;
import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.Credential;
import de.m0ep.sioc.service.auth.Password;
import de.m0ep.sioc.service.auth.Username;
import de.m0ep.socc.core.connector.AbstractServiceClientManager;
import de.m0ep.socc.core.utils.RdfUtils;

public class YoutubeV2ClientManager extends AbstractServiceClientManager {

    private APIKey apiKey;

    public YoutubeV2ClientManager(Service service) {
        super(service);
        checkService(service);
    }

    public YoutubeV2ClientManager(Service service, UserAccount defaultUserAccount) throws Exception {
        super(service, defaultUserAccount);
    }

    @Override
    public Object createClientFromAccount(UserAccount userAccount) throws Exception {
        if (null == apiKey) {
            checkService(getService());
        }

        de.m0ep.sioc.service.auth.UserAccount authUserAccount =
                de.m0ep.sioc.service.auth.UserAccount.getInstance(
                        userAccount.getModel(),
                        userAccount.getResource());

        Preconditions.checkArgument(
                authUserAccount.hasAuthentication(),
                "The defaultUserAccount has no required authentication data.");
        Authentication authentication = authUserAccount.getAuthentication();

        Preconditions.checkArgument(
                authentication.hasCredential(),
                "The defaultUserAccount authentication has no required credentials");
        ClosableIterator<Credential> credentialIter = authentication.getAllCredential();

        Username username = null;
        Password password = null;
        while (credentialIter.hasNext()) {
            Credential credential = (Credential) credentialIter.next();

            if (RdfUtils.isType(credential.getModel(), credential.getResource(),
                    Username.RDFS_CLASS) && credential.hasValue()) {
                username = Username.getInstance(credential.getModel(), credential.asResource());
            } else if (RdfUtils.isType(credential.getModel(), credential.getResource(),
                    Password.RDFS_CLASS) && credential.hasValue()) {
                password = Password.getInstance(credential.getModel(), credential.asResource());
            }
        }

        Preconditions.checkArgument(null != username,
                "The defaultUserAccount authentication contains no required username");
        Preconditions.checkArgument(null != password,
                "The defaultUserAccount authentication contains no required password");

        return new YoutubeV2ClientWrapper(apiKey, username, password);
    }

    private void checkService(Service service) {
        Preconditions.checkNotNull(service,
                "Required parameter service must be specified.");
        Preconditions.checkArgument(service.hasServiceEndpoint(),
                "The parameter service has no serviceEndpoint.");

        de.m0ep.sioc.service.auth.Service authService = de.m0ep.sioc.service.auth.Service
                .getInstance(
                        service.getModel(),
                        service.getResource());

        Preconditions.checkArgument(authService.hasAuthentication(),
                "The parameter service has no authentication.");

        Authentication authentication = authService.getAuthentication();
        Preconditions.checkArgument(authentication.hasCredential(),
                "The service authentication has no credentials.");

        ClosableIterator<Credential> credIter = authentication.getAllCredential();
        try {
            while (credIter.hasNext()) {
                Credential credential = (Credential) credIter.next();

                if (RdfUtils.isType(
                        credential.getModel(),
                        credential.getResource(),
                        APIKey.RDFS_CLASS) && credential.hasValue()) {
                    apiKey = APIKey.getInstance(credential.getModel(), credential.getResource());
                    return;
                }
            }
        } finally {
            credIter.close();
        }

        throw new IllegalArgumentException("The service authentication has no apikey credential.");
    }
}
