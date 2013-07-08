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

import com.google.common.base.Preconditions;

import de.m0ep.sioc.service.auth.Service;
import de.m0ep.socc.core.ISoccContext;

public abstract class AbstractConnector implements IConnector {
    private String id;
    private ISoccContext context;
    private UserAccount defaultUserAccount;
    private Service service;

    public AbstractConnector(
            String id,
            ISoccContext context,
            UserAccount defaultUserAccount,
            Service service) {
        this.id = Preconditions.checkNotNull(id, "Required parameter id must be specified.");
        setContext(context);
        setDefaultUserAccount(defaultUserAccount);
        setService(service);
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
    public void setContext(ISoccContext context) {
        this.context = Preconditions.checkNotNull(
                context,
                "Required parameter context must be specified.");
    }

    @Override
    public UserAccount getDefaultUserAccount() {
        return defaultUserAccount;
    }

    @Override
    public void setDefaultUserAccount(UserAccount defaultUserAccount) {
        this.defaultUserAccount = Preconditions.checkNotNull(
                defaultUserAccount,
                "Required parameter defaultUserAccount must be specified.");
    }

    @Override
    public Service getService() {
        return service;
    }

    @Override
    public void setService(Service service) {
        this.service = Preconditions.checkNotNull(
                service,
                "Required parameter service must be specified.");
    }
}
