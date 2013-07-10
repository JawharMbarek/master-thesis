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

import de.m0ep.socc.config.ConnectorCfg;
import de.m0ep.socc.core.ISoccContext;

/**
 * Abstract connector that implements some basic methods which may be used in
 * all subclasses.
 * 
 * @author Florian Müller
 */
public abstract class AbstractConnector implements IConnector {
    protected String id;
    protected ISoccContext context;
    protected UserAccount defaultUserAccount;
    protected Service service;

    public AbstractConnector(final ISoccContext context, final ConnectorCfg config) {
        this.context = Preconditions.checkNotNull(
                context,
                "Required parameter context must be specified.");

        Preconditions.checkNotNull(config, "Required parameter config must be specified.");

        Preconditions.checkArgument(config.hasId(), "Provides parameter config contains no id.");
        this.id = config.getId();

        Preconditions.checkArgument(
                config.hasDefaultUser(),
                "Provided parameter config contains no default UserAccount.");
        this.defaultUserAccount = config.getDefaultUser();

        Preconditions.checkArgument(config.hasService(),
                "Provided parameter config contains no service");
        this.service = config.getService();
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
    public AbstractConnector(
            String id,
            ISoccContext context,
            UserAccount defaultUserAccount,
            Service service) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISoccContext getContext() {
        return context;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAccount getDefaultUserAccount() {
        return defaultUserAccount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Service getService() {
        return service;
    }
}
