
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

import de.m0ep.sioc.services.auth.APIKey;
import de.m0ep.sioc.services.auth.Password;
import de.m0ep.sioc.services.auth.Service;
import de.m0ep.sioc.services.auth.UserAccount;
import de.m0ep.sioc.services.auth.Username;
import de.m0ep.sioc.services.auth.WebAPI;
import de.m0ep.socc.config.ConnectorConfig;
import de.m0ep.socc.core.ISoccContext;
import de.m0ep.socc.core.SoccContext;
import de.m0ep.socc.core.connector.IConnector;
import de.m0ep.socc.core.connector.google.youtube.v2.YoutubeConnector;
import de.m0ep.socc.core.connector.google.youtube.v2.YoutubeSiocConverter;
import de.m0ep.socc.core.exceptions.AuthenticationException;
import de.m0ep.socc.core.exceptions.NotFoundException;

public class YoutubeV2ConnectorTestApp {

    /**
     * @param args
     * @throws IOException
     * @throws AuthenticationException
     * @throws NotFoundException
     */
    public static void main(String[] args) throws NotFoundException,
            AuthenticationException,
            IOException {
        URI serviceEndpointUri = Builder.createURI("http://www.youtube.com");

        Model model = RDF2Go.getModelFactory().createModel();
        model.open();

        ISoccContext context = new SoccContext(model);

        Service service = new Service(model, true);
        service.setServiceEndpoint(serviceEndpointUri);

        WebAPI serviceAuth = new WebAPI(model, true);
        service.setServiceAuthentication(serviceAuth);

        APIKey apiKey = new APIKey(model, true);
        apiKey.setValue("AI39si48dEjhAE9RrY6w1HnlmyrUUTDt-xssOKkEEcpOIMD1gFcQ-0Xv40YNl-H1MxFzGzbHih4ootWo1cRrPH9gV-5UdazEbQ");
        serviceAuth.addCredentials(apiKey);

        /*********************************/

        UserAccount userAccount = new UserAccount(model, true);
        userAccount.setAccountName("qX2si9NbFXXGS14BWC1juw");
        userAccount.setAccountServiceHomepage(serviceEndpointUri);

        Person defaultPerson = new Person(model, true);
        defaultPerson.setName("Max Hiwi");
        defaultPerson.addAccount(userAccount);
        userAccount.setAccountOf(defaultPerson);

        WebAPI userAuth = new WebAPI(model, true);
        userAccount.setAccountAuthentication(userAuth);

        Username username = new Username(model, true);
        username.setValue("tkhiwis@gmail.com");
        userAuth.addCredentials(username);

        Password password = new Password(model, true);
        password.setValue("turing123");
        userAuth.addCredentials(password);

        /*********************************/

        UserAccount m0eperAccount = new UserAccount(model, true);
        m0eperAccount.setAccountName("m0eper");
        m0eperAccount.setAccountServiceHomepage(serviceEndpointUri);

        Person m0eperPerson = new Person(model, true);
        m0eperPerson.setName("Florian Müller");
        m0eperPerson.addAccount(m0eperAccount);
        m0eperAccount.setAccountOf(m0eperPerson);

        WebAPI m0eperAuth = new WebAPI(model, true);
        m0eperAccount.setAccountAuthentication(m0eperAuth);

        Username m0eperUsername = new Username(model, true);
        m0eperUsername.setValue("email.mufl@gmail.com");
        m0eperAuth.addCredentials(m0eperUsername);

        Password m0eperPassword = new Password(model, true);
        m0eperPassword.setValue("email.mufl.net");
        m0eperAuth.addCredentials(m0eperPassword);

        /*********************************/

        ConnectorConfig config = new ConnectorConfig(model, true);
        config.setId("youtube-test");
        config.setDefaultUserAccount(userAccount);
        config.setService(service);

        Post replyPost = new Post(model, true);
        replyPost.setContent("automated reply at " + new Date());
        replyPost.setCreator(userAccount);

        IConnector connector = new YoutubeConnector(context, config);
        connector.initialize();

        List<Forum> forums = connector.getStructureReader().listForums();
        for (Forum forum : forums) {
            System.err.println(forum + " " + forum.getId());
            if (forum.getId().startsWith(
                    YoutubeSiocConverter.PLAYLISTS_ID_PREFIX)) {
                List<Post> posts = connector.getPostReader().readNewPosts(null,
                        -1, forum);
                for (Post post : posts) {
                    List<Post> replies = connector.getPostReader()
                            .readNewReplies(null, -1, post);
                    for (Post reply : replies) {
                        connector.getPostWriter().writeReply(replyPost, reply);
                        System.err.println(reply.getContent() + " " + reply);
                    }
                }
            }
        }

        // Forum playlists = connector.serviceStructureReader()
        // .getForum(YoutubeConnector.PLAYLISTS_ID);
        //
        // List<Thread> threads =
        // connector.serviceStructureReader().listThreads(playlists);
        // for (Thread thread : threads) {
        // System.out.println(thread.getName() + " " + thread.getId() + " " +
        // thread);
        //
        // posts = connector.postReader().readNewPosts(null, -1, thread);
        // for (Post post : posts) {
        // System.out.println(post.getContent() + " " + post);
        //
        // List<Post> replies = connector.postReader().readNewReplies(null, -1,
        // post);
        // for (Post reply : replies) {
        // System.out.println(reply);
        // }
        // }
        // }

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
