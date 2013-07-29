
package de.m0ep.test.socc.core;

import java.io.IOException;
import java.util.Collections;
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
import org.rdfs.sioc.Thread;

import com.google.common.collect.Lists;
import com.xmlns.foaf.Person;

import de.m0ep.sioc.service.auth.APIKey;
import de.m0ep.sioc.service.auth.Authentication;
import de.m0ep.sioc.service.auth.Password;
import de.m0ep.sioc.service.auth.Service;
import de.m0ep.sioc.service.auth.UserAccount;
import de.m0ep.sioc.service.auth.Username;
import de.m0ep.sioc.service.auth.WebAPI;
import de.m0ep.socc.config.ConnectorCfg;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.SoccContext;
import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.google.youtube.v2.YoutubeV2Connector;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;

public class YoutubeV2ConnectorTestApp {

    /**
     * @param args
     * @throws IOException
     * @throws AuthenticationException
     * @throws NotFoundException
     */
    public static void main(String[] args) throws NotFoundException, AuthenticationException,
            IOException {
        URI serviceEndpointUri = Builder.createURI("http://www.youtube.com");

        Model model = RDF2Go.getModelFactory().createModel();
        model.open();

        ISoccContext context = new SoccContext(model);

        Service service = new Service(model, true);
        service.setServiceEndpoint(serviceEndpointUri);

        UserAccount userAccount = new UserAccount(model, true);
        userAccount.setAccountName("qX2si9NbFXXGS14BWC1juw");
        userAccount.setAccountServiceHomepage(serviceEndpointUri);

        Person defaultPerson = new Person(model, true);
        defaultPerson.setName("Max Hiwi");
        defaultPerson.addAccount(userAccount);
        userAccount.setAccountOf(defaultPerson);

        Authentication serviceAuth = new WebAPI(model, true);
        service.setAuthentication(serviceAuth);

        Authentication userAuth = new WebAPI(model, true);
        userAccount.setAuthentication(userAuth);

        APIKey apiKey = new APIKey(model, true);
        apiKey.setValue("AI39si48dEjhAE9RrY6w1HnlmyrUUTDt-xssOKkEEcpOIMD1gFcQ-0Xv40YNl-H1MxFzGzbHih4ootWo1cRrPH9gV-5UdazEbQ");
        serviceAuth.addCredential(apiKey);

        Username username = new Username(model, true);
        username.setValue("tkhiwis@gmail.com");
        userAuth.addCredential(username);

        Password password = new Password(model, true);
        password.setValue("turing123");
        userAuth.addCredential(password);

        ConnectorCfg config = new ConnectorCfg(model, true);
        config.setId("youtube-test");
        config.setDefaultUser(userAccount);
        config.setService(service);

        IConnector connector = new YoutubeV2Connector(context, config);
        connector.initialize();

        Forum uploads = connector.serviceStructureReader()
                .getForum(YoutubeV2Connector.UPLOADS_ID);

        System.err.println(uploads + " " + uploads.getId());

        List<Post> posts = connector.postReader().readNewPosts(null, -1, uploads);
        for (Post post : posts) {
            System.err.println(post.getContent() + " " + post);
        }

        Forum playlists = connector.serviceStructureReader()
                .getForum(YoutubeV2Connector.PLAYLISTS_ID);

        List<Thread> threads = connector.serviceStructureReader().listThreads(playlists);
        for (Thread thread : threads) {
            System.out.println(thread.getName() + " " + thread.getId() + " " + thread);

            posts = connector.postReader().readNewPosts(null, -1, thread);
            for (Post post : posts) {
                System.out.println(post.getContent() + " " + post);

                List<Post> replies = connector.postReader().readNewReplies(null, -1, post);
                for (Post reply : replies) {
                    System.out.println(reply);
                }
            }
        }

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
