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

package de.m0ep.socc.core.connector;

import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import com.google.common.base.Preconditions;

import de.m0ep.socc.config.ConnectorConfig;
import de.m0ep.socc.core.ISoccContext;

/**
 * Abstract connector that implements some basic methods which may be used in
 * all subclasses.
 * 
 * @author Florian Müller
 */
public abstract class DefaultConnector implements IConnector {
    protected String id;
    protected ISoccContext context;
    protected UserAccount defaultUserAccount;
    protected Service service;
    protected boolean isInitialized;
    protected String messageTemplateString;

    /**
     * Default constructor
     */
    private DefaultConnector() {
        this.id = null;
        this.context = null;
        this.defaultUserAccount = null;
        this.service = null;
        this.isInitialized = false;
        this.messageTemplateString = IPostWriter.DEFAULT_MESSAGE_TEMPLATE;
    }

    /**
     * Constructs a new instance from a {@link ConnectorConfig}.
     * 
     * @param context
     * @param config
     * @throws NullPointerException
     *             Thrown if one or more parameter are <code>null</code>.
     * @throws IllegalArgumentException
     *             Thrown if <code>config</code> contains no <code>id</code>,
     *             <code>defaultUser</code> or <code>service</code>.
     */
    public DefaultConnector(
            final ISoccContext context,
            final ConnectorConfig config) {
        this();
        this.context = Preconditions.checkNotNull(
                context,
                "Required parameter context must be specified.");

        Preconditions.checkNotNull(config,
                "Required parameter config must be specified.");

        Preconditions.checkArgument(config.hasId(),
                "Provides parameter config contains no id.");
        this.id = config.getId();

        Preconditions.checkArgument(
                config.hasDefaultUserAccount(),
                "Provided parameter config contains no default UserAccount.");
        this.defaultUserAccount = config.getDefaultUserAccount();

        Preconditions.checkArgument(config.hasService(),
                "Provided parameter config contains no service");
        this.service = config.getService();

        if (config.hasUnknownMessageTemplate()) {
            this.messageTemplateString = config.getUnknownMessageTemplate();
        }
    }

    /**
     * Constructs a new instance with an <code>id</code>, <code>context</code>,
     * <code>defaultUserAccount</code> and a <code>service</code>.
     * 
     * @param id
     * @param context
     * @param defaultUserAccount
     * @param service
     * @throws NullPointerException
     *             Thrown if one or more parameter are <code>null</code>.
     * @throws IllegalArgumentException
     *             Thrown if <code>id</code> is empty.
     */
    public DefaultConnector(
            final String id,
            final ISoccContext context,
            final UserAccount defaultUserAccount,
            final Service service) {
        this();
        this.id = Preconditions.checkNotNull(
                id,
                "Required parameter id must be specified.");
        Preconditions.checkArgument(
                !id.isEmpty(),
                "Required parameter id may not be empty.");

        this.context = Preconditions.checkNotNull(
                context,
                "Required parameter context must be specified.");

        this.defaultUserAccount = Preconditions.checkNotNull(
                defaultUserAccount,
                "Required parameter defaultUserAccount must be specified.");

        this.service = Preconditions.checkNotNull(
                service,
                "Required parameter service must be specified.");
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ISoccContext getContext() {
        return context;
    }

    @Override
    public UserAccount getDefaultUserAccount() {
        return defaultUserAccount;
    }

    @Override
    public Service getService() {
        return service;
    }

    @Override
    public String getUnknownMessageTemplate() {
        return messageTemplateString;
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * Sets initialized to the provided value.
     * 
     * @param isInitialized
     */
    protected void setInitialized(final boolean isInitialized) {
        this.isInitialized = isInitialized;
    }
}
