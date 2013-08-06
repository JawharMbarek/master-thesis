
package de.m0ep.socc.core.connector.moodle;

import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.rdfs.sioc.UserAccount;
import org.rdfs.sioc.services.Service;

import com.google.common.base.Preconditions;

import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.Credential;
import de.m0ep.sioc.service.auth.Password;
import de.m0ep.sioc.service.auth.ServicesAuthVocabulary;
import de.m0ep.sioc.service.auth.Username;
import de.m0ep.socc.core.connector.AbstractServiceClientManager;
import de.m0ep.socc.core.utils.RdfUtils;

public class Moodle2ClientManager extends
        AbstractServiceClientManager<Moodle2ClientWrapper> {

    public Moodle2ClientManager(Service service, UserAccount defaultUserAccount)
            throws Exception {
        super(service, defaultUserAccount);
    }

    @Override
    protected void init() {
    }

    @Override
    public Moodle2ClientWrapper createClientFromAccount(UserAccount userAccount)
            throws Exception {
        de.m0ep.sioc.service.auth.UserAccount authUserAccount =
                de.m0ep.sioc.service.auth.UserAccount.getInstance(
                        userAccount.getModel(),
                        userAccount.getResource());

        Preconditions.checkArgument(authUserAccount.hasAuthentication(),
                "The defaultUserAccount has no required authentication data.");
        Authentication authentication = authUserAccount.getAuthentication();

        Preconditions
                .checkArgument(authentication.hasCredential(),
                        "The defaultUserAccount authentication has no required credentials");
        ClosableIterator<Credential> credentialIter = authentication
                .getAllCredential();

        Username username = null;
        Password password = null;
        while (credentialIter.hasNext()) {
            Credential credential = (Credential) credentialIter.next();

            if (RdfUtils.isType(
                    credential.getModel(),
                    credential.getResource(),
                    ServicesAuthVocabulary.Username)
                    && credential.hasValue()) {
                username = Username.getInstance(
                        credential.getModel(),
                        credential.asResource());
            } else if (RdfUtils.isType(
                    credential.getModel(),
                    credential.getResource(),
                    ServicesAuthVocabulary.Password)
                    && credential.hasValue()) {
                password = Password.getInstance(
                        credential.getModel(),
                        credential.asResource());
            }
        }

        Preconditions
                .checkArgument(null != username,
                        "The defaultUserAccount authentication contains no required username");
        Preconditions
                .checkArgument(null != password,
                        "The defaultUserAccount authentication contains no required password");

        return new Moodle2ClientWrapper(
                getService().getServiceEndpoint().asURI(),
                username.getValue(),
                password.getValue());
    }
}
