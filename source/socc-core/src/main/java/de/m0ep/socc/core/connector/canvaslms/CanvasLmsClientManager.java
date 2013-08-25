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

package de.m0ep.socc.core.connector.canvaslms;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import com.google.common.base.Preconditions;

import de.m0ep.canvas.CanvasLmsClient;
import de.m0ep.sioc.services.auth.AuthenticationMechanism;
import de.m0ep.sioc.services.auth.Credentials;
import de.m0ep.sioc.services.auth.ServicesAuthVocabulary;
import de.m0ep.socc.core.connector.DefauktClientManager;
import de.m0ep.socc.core.utils.RdfUtils;

/**
 * Manager that handle all clients for a {@link CanvasLmsConnector}.
 * 
 * @author Florian Müller
 */
public class CanvasLmsClientManager extends
        DefauktClientManager<CanvasLmsClient> {

    /**
     * Constructs a new {@link CanvasLmsClientManager} for a
     * <code>service</code> with a <code>defaultUserAccount</code>.
     * 
     * @param service
     * @param defaultUserAccount
     * @throws Exception
     *             Thrown if an error occurred creating a default client from
     *             the <code>defaultUserAccount</code>.
     */
    public CanvasLmsClientManager(final Service service,
            final UserAccount defaultUserAccount) throws Exception {
        super(service, defaultUserAccount);
    }

    @Override
    protected void init() {
    }

    /**
     * Creates a new {@link CanvasLmsClient} for the given
     * <code>userAccount</code>.
     * 
     * @param userAccount
     * @return
     * @throws NullPointerException
     *             Thrown if <code>userAccount</code> is <code>null</code>.
     * @throws IllegalArgumentException
     *             Thrown if has missing parameters to create a client.
     */
    @Override
    public CanvasLmsClient createClientFromAccount(final UserAccount userAccount)
            throws Exception {
        Preconditions.checkNotNull(userAccount,
                "Required parameter userAccount must be specified.");

        de.m0ep.sioc.services.auth.UserAccount authUserAccount =
                de.m0ep.sioc.services.auth.UserAccount.getInstance(
                        userAccount.getModel(),
                        userAccount.getResource());

        Preconditions.checkArgument(
                authUserAccount.hasAccountAuthentication(),
                "The userAccount has no authentication data.");
        AuthenticationMechanism authentication = authUserAccount
                .getAccountAuthentication();

        Preconditions.checkArgument(
                authentication.hasCredentials(),
                "The authentication has no credentials");
        ClosableIterator<Credentials> credentialIter = authentication
                .getAllCredentials();

        while (credentialIter.hasNext()) {
            Credentials credential = (Credentials) credentialIter.next();

            if (RdfUtils.isType(
                    credential.getModel(),
                    credential.getResource(),
                    ServicesAuthVocabulary.AccessToken)
                    && credential.hasValue()) {
                return new CanvasLmsClient(
                        getService().getServiceEndpoint().toString(),
                        credential.getValue());
            }
        }

        throw new IllegalArgumentException(
                "Provided userAccount has no accessToken.");
    }
}
