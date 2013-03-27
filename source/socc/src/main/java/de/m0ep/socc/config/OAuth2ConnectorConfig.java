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

package de.m0ep.socc.config;

import org.mortbay.jetty.Connector;

/**
 * Basic configuration JavaBean for OAuth2 enabled {@link Connector}s
 * 
 * @author Florian Müller
 * 
 */
public class OAuth2ConnectorConfig extends DefaultConnectorConfig {
    private static final long serialVersionUID = 1613150078617134827L;

    public static final String CLIENT_ID = "clientId";
    public static final String CLIENT_SECRET = "clientSecret";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String EXPIRES_IN_SECONDS = "expiresInSeconds";

    private String clientId;
    private String clientSecret;
    private String accessToken;
    private long expiresInSeconds;

    public String getClientId() {
	return clientId;
    }

    public void setClientId(String clientId) {
	this.clientId = clientId;
    }

    public String getClientSecret() {
	return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
	this.clientSecret = clientSecret;
    }

    public String getAccessToken() {
	return accessToken;
    }

    public void setAccessToken(String accessToken) {
	this.accessToken = accessToken;
    }

    public long getExpiresInSeconds() {
	return expiresInSeconds;
    }

    public void setExpiresInSeconds(long expiresInSeconds) {
	this.expiresInSeconds = expiresInSeconds;
    }
}
