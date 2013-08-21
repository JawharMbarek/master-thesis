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

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.exception.FacebookException;
import com.restfb.types.User;

import de.m0ep.sioc.services.auth.AccessToken;
import de.m0ep.sioc.services.auth.ClientId;
import de.m0ep.sioc.services.auth.ClientSecret;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class FacebookClientWrapper {
    private static final Logger LOG = LoggerFactory
            .getLogger(FacebookClientWrapper.class);

    private User user;
    private FacebookClient client;

    public FacebookClientWrapper(ClientId clientId, ClientSecret clientSecret,
            AccessToken accessToken) throws IOException,
            AuthenticationException {

        try {
            DefaultFacebookClient client = new DefaultFacebookClient();
            com.restfb.FacebookClient.AccessToken token = client
                    .obtainExtendedAccessToken(
                            clientId.getValue(),
                            clientSecret.getValue(),
                            accessToken.getValue());

            accessToken.setValue(token.getAccessToken());
        } catch (Exception e) {
            LOG.warn("Failed to obtain extended accesstoken: {}", e
                    .getMessage());
        }

        this.client = new DefaultFacebookClient(accessToken.getValue());

        try {
            this.user = this.client.fetchObject("me", User.class);
        } catch (FacebookException e) {
            FacebookConnector.handleFacebookException(e);
        }
    }

    public User getUser() {
        return user;
    }

    public FacebookClient getClient() {
        return client;
    }
}
