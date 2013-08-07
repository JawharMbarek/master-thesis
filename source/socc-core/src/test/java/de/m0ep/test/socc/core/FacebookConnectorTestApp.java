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

package de.m0ep.test.socc.core;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.ontoware.rdf2go.util.RDFTool;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;

import com.google.common.collect.Lists;
import com.xmlns.foaf.Person;

import de.m0ep.sioc.service.auth.AccessToken;
import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.ClientId;
import de.m0ep.sioc.service.auth.ClientSecret;
import de.m0ep.sioc.service.auth.OAuth;
import de.m0ep.sioc.service.auth.Service;
import de.m0ep.sioc.service.auth.UserAccount;
import de.m0ep.socc.config.ConnectorConfig;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.SoccContext;
import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.facebook.FacebookConnector;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class FacebookConnectorTestApp {

    /**
     * @param args
     * @throws IOException
     * @throws AuthenticationException
     */
    public static void main(String[] args) throws AuthenticationException, IOException {
        URI serviceEndpointUri = Builder.createURI("https://www.facebook.com");

        Model model = RDF2Go.getModelFactory().createModel();
        model.open();

        ISoccContext context = new SoccContext(model);

        Service service = new Service(model, true);
        service.setServiceEndpoint(serviceEndpointUri);

        Authentication serviceAuth = new OAuth(model, true);
        service.setAuthentication(serviceAuth);

        ClientId clientId = new ClientId(model, true);
        clientId.setValue("218182098322396");
        serviceAuth.addCredential(clientId);

        ClientSecret clientSecret = new ClientSecret(model, true);
        clientSecret.setValue("f4ed27b621c0f6476c2741f7cf9c4dc5");
        serviceAuth.addCredential(clientSecret);

        /*********************************/

        UserAccount userAccount = new UserAccount(model, true);
        userAccount.setAccountName("100000490230885");
        userAccount.setAccountServiceHomepage(serviceEndpointUri);

        Person defaultPerson = new Person(model, true);
        defaultPerson.setName("Florian Müller");
        defaultPerson.addAccount(userAccount);
        userAccount.setAccountOf(defaultPerson);

        Authentication userAuth = new OAuth(model, true);
        userAccount.setAuthentication(userAuth);

        AccessToken accessToken = new AccessToken(model, true);
        accessToken
                .setValue("CAADGb3p3g9wBANQpIOfeJjlO6WcgyILAE39y6YMECzuiM6xJB3HG5oAMetr0nQ3FdRfh" +
                        "ysngrNmRysSVAcPQiWPPXd2q9ZBZBNf7kqd4IQnyg1BRZAEvABiUhpkxFlAMhVgn44J2mFN" +
                        "pkgdDgUT");
        userAuth.addCredential(accessToken);

        /*********************************/

        ConnectorConfig config = new ConnectorConfig(model, true);
        config.setId("youtube-test");
        config.setDefaultUser(userAccount);
        config.setService(service);

        Post replyPost = new Post(model, true);
        replyPost.setContent("automated reply at " + new Date());
        replyPost.setCreator(userAccount);

        printModel(model);

        IConnector connector = new FacebookConnector(context, config);
        connector.initialize();

        List<Forum> forums = connector.getStructureReader().listForums();
        for (Forum forum : forums) {
            System.out.println(forum);
        }

        connector.shutdown();

        printModel(model);

        model.close();
    }

    private static void printModel(Model model) {
        List<Statement> sortedStmts = Lists.newArrayList(model);
        Collections.sort(sortedStmts);

        Model writeModel = RDF2Go.getModelFactory().createModel();
        writeModel.open();
        writeModel.addAll(sortedStmts.iterator());

        System.out.println(RDFTool.modelToString(writeModel, Syntax.RdfXml));
        writeModel.close();
    }

}
