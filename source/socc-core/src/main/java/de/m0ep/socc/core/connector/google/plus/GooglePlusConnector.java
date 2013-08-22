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

import java.io.IOException;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.repackaged.com.google.common.base.Throwables;

import de.m0ep.socc.config.ConnectorConfig;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.connector.DefaultConnector;
import de.m0ep.socc.core.connector.IClientManager;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;

public class GooglePlusConnector extends DefaultConnector {

    private static final URI URI_SERVICE_ENDPOINT = Builder
            .createURI("https://plus.google.com");

    private IClientManager<GooglePlusClientWrapper> clientManager;
    private IStructureReader structureReader;
    private IPostReader postReader;

    public GooglePlusConnector(ISoccContext context, ConnectorConfig config) {
        super(context, config);
    }

    public GooglePlusConnector(String id, ISoccContext context,
            UserAccount defaultUserAccount,
            Service service) {
        super(id, context, defaultUserAccount, service);
    }

    @SuppressWarnings("unchecked")
    @Override
    public IClientManager<GooglePlusClientWrapper> getServiceClientManager() {
        return clientManager;
    }

    @Override
    public IStructureReader getStructureReader() {

        if (null == structureReader) {
            structureReader = new GooglePlusStructureReader(this);
        }

        return structureReader;
    }

    @Override
    public IPostReader getPostReader() {
        if (null == postReader) {
            postReader = new GooglePlusPostReader(this);
        }

        return postReader;
    }

    @Override
    public IPostWriter getPostWriter() {
        throw new UnsupportedOperationException(
                "Google Plus supports currently no writing of posts");
    }

    @Override
    public void initialize() throws AuthenticationException, IOException {
        getService().setServiceEndpoint(URI_SERVICE_ENDPOINT);

        try {
            this.clientManager = new GooglePlusClientManager(
                    getService(),
                    getDefaultUserAccount());
        } catch (Exception e) {
            Throwables.propagateIfInstanceOf(e, IOException.class);
            Throwables.propagateIfInstanceOf(e, AuthenticationException.class);
            throw Throwables.propagate(e);
        }

        setInitialized(true);
    }

    @Override
    public void shutdown() {
        clientManager.clear();
        setInitialized(false);
    }

    public static void handleGoogleException(Exception e)
            throws AuthenticationException, IOException {
        if (e instanceof GoogleJsonResponseException) {
            GoogleJsonResponseException gjre = (GoogleJsonResponseException) e;
            GoogleJsonError error = gjre.getDetails();

            switch (error.getCode()) {
                case 404:
                    throw new NotFoundException(
                            "Requested resource not found.", e);
                case 401:
                    throw new AuthenticationException("Authentication failed.",
                            e);
            }
        }

        Throwables.propagateIfInstanceOf(e, IOException.class);
    }

}
