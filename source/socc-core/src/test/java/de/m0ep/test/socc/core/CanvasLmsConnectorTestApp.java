
package de.m0ep.test.socc.core;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.util.Builder;
import org.ontoware.rdf2go.util.RDFTool;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Thread;

import com.google.common.collect.Lists;
import com.xmlns.foaf.Person;

import de.m0ep.sioc.service.auth.AccessToken;
import de.m0ep.sioc.service.auth.OAuth;
import de.m0ep.sioc.service.auth.Service;
import de.m0ep.sioc.service.auth.UserAccount;
import de.m0ep.socc.config.ConnectorCfg;
import de.m0ep.socc.core.SoccContext;
import de.m0ep.socc.core.connector.canvaslms.CanvasLmsConnector;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class CanvasLmsConnectorTestApp {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String rootUri = "https://canvas.instructure.com";
        String oAuthToken = "7~LUpV7B3lJYadvZ2sHlpJiTcyJ6HaduVb3Ho8YjBNXSdIE4AEFzLFfORcOHRHh1fU";

        Model model = RDF2Go.getModelFactory().createModel();
        model.open();
        SoccContext context = new SoccContext(model);

        AccessToken accessToken = new AccessToken(model, true);
        accessToken.setValue(oAuthToken);

        OAuth oAuth = new OAuth(model, true);
        oAuth.addCredential(accessToken);

        UserAccount defaultUserAccount = new UserAccount(model, true);
        defaultUserAccount.setAuthentication(oAuth);

        Person defaultPerson = new Person(model, true);
        defaultPerson.setName("dafault");
        defaultPerson.addAccount(defaultUserAccount);
        defaultUserAccount.setAccountOf(defaultPerson);

        Service service = new Service(model, true);
        service.setServiceEndpoint(Builder.createURI(rootUri));
        service.setServiceDefinition(Builder.createPlainliteral("Canvas LMS Service"));

        ConnectorCfg config = new ConnectorCfg(model, true);
        config.setId("canvas-test");
        config.setDefaultUser(defaultUserAccount);
        config.setService(service);

        CanvasLmsConnector connector = new CanvasLmsConnector(context, config);
        try {
            connector.initialize();
        } catch (AuthenticationException | IOException e) {
            e.printStackTrace();

            return;
        }

        List<Forum> forums = null;
        try {
            forums = connector.getServiceStructureReader().listForums();
        } catch (AuthenticationException | IOException e) {
            e.printStackTrace();
        }

        if (null != forums) {
            for (Forum forum : forums) {
                System.err.println(forum.getResource());

                List<Thread> threads = null;
                try {
                    threads = connector.getServiceStructureReader().listThreads(forum);
                } catch (AuthenticationException | IOException e) {
                    e.printStackTrace();
                }

                if (null != threads) {
                    for (Thread thread : threads) {
                        System.err.println(thread.getResource());
                    }
                }
            }
        }

        System.out.println(RDFTool.modelToString(model, Syntax.RdfXml));

        List<Statement> sortedStmts = Lists.newArrayList(model);
        Collections.sort(sortedStmts);

        Model writeModel = RDF2Go.getModelFactory().createModel();
        writeModel.open();
        writeModel.addAll(sortedStmts.iterator());

        System.out.println(RDFTool.modelToString(writeModel, Syntax.RdfXml));

        writeModel.close();
        model.close();
    }
}
