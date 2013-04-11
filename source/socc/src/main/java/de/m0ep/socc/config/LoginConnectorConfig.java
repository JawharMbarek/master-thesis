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

import com.google.common.base.Objects;

/**
 * Basic configuraion JavaBean for {@link Connector}s with a username/password
 * login scheme.
 * 
 * @author Florian Müller
 * 
 */
public class LoginConnectorConfig extends DefaultConnectorConfig {
    private static final long serialVersionUID = 7330276183009828815L;

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private String username;
    private String password;

    /**
     * Returns the username.
     * 
     * @return The username.
     */
    public String getUsername() {
	return username;
    }

    /**
     * Set the username.
     * 
     * @param username
     *            Username to set.
     */
    public void setUsername(String username) {
	this.username = username;
    }

    /**
     * Returns the password.
     * 
     * @return The password.
     */
    public String getPassword() {
	return password;
    }

    /**
     * Sets the password.
     * 
     * @param password
     *            The password to set.
     */
    public void setPassword(String password) {
	this.password = password;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result
		+ Objects.hashCode(this.username, this.password);
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (!super.equals(obj)) {
	    return false;
	}
	if (!(obj instanceof LoginConnectorConfig)) {
	    return false;
	}
	LoginConnectorConfig other = (LoginConnectorConfig) obj;

	if (!Objects.equal(this.username, other.username)) {
	    return false;
	}

	if (!Objects.equal(this.password, other.password)) {
	    return false;
	}

	return super.equals(obj);
    }
}
