/*
 * The MIT License (MIT) Copyright © 2013 florian Permission is hereby granted,
 * free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the “Software”), to deal in the Software
 * without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions: The above copyright notice and this
 * permission notice shall be included in all copies or substantial portions of
 * the Software. THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package de.m0ep.socc.core.connector.canvaslms;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import com.google.common.base.Preconditions;

import de.m0ep.canvas.CanvasLmsClient;
import de.m0ep.sioc.service.auth.AccessToken;
import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.Credential;
import de.m0ep.socc.core.connector.AbstractServiceClientManager;
import de.m0ep.socc.core.utils.RdfUtils;

public class CanvasLmsClientManager extends AbstractServiceClientManager {
    public CanvasLmsClientManager(Service service) {
        super(service);
    }

    public CanvasLmsClientManager(Service service, UserAccount defaultUserAccount) throws Exception {
        super(service, defaultUserAccount);
    }

    @Override
    public Object createClientFromAccount(UserAccount userAccount) throws Exception {
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

            if (RdfUtils.isType(
                    credential.getModel(),
                    credential.getResource(),
                    AccessToken.RDFS_CLASS)
                    && credential.hasValue()) {
                return new CanvasLmsClient(
                        getService().getServiceEndpoint().toString(),
                        credential.getValue());
            }
        }

        throw new IllegalArgumentException("Provided userAccount has no accessToken.");
    }

}
