
package de.m0ep.test.socc.core;

import java.io.IOException;
import java.util.List;

import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.Thread;

import com.xmlns.foaf.Person;

import de.m0ep.sioc.service.auth.Direct;
import de.m0ep.sioc.service.auth.Password;
import de.m0ep.sioc.service.auth.Service;
import de.m0ep.sioc.service.auth.UserAccount;
import de.m0ep.sioc.service.auth.Username;
import de.m0ep.socc.config.ConnectorCfg;
import de.m0ep.socc.core.SoccContext;
import de.m0ep.socc.core.connector.moodle.Moodle2Connector;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class Moodle2ConnectorTestApp {

    public static void main(String[] args) throws AuthenticationException, IOException {
        String rootUri = "http://localhost/moodle";

        Model model = RDF2Go.getModelFactory().createModel();
        model.open();
        SoccContext context = new SoccContext(model);

        Username username = new Username(model, true);
        username.setValue("admin");

        Password password = new Password(model, true);
        password.setValue("admin");

        Direct direct = new Direct(model, true);
        direct.addCredential(username);
        direct.addCredential(password);

        UserAccount defaultUserAccount = new UserAccount(model, true);
        defaultUserAccount.setAccountName("2");
        defaultUserAccount.setAccountServiceHomepage(Builder.createURI(rootUri));
        defaultUserAccount.setAuthentication(direct);

        Person defaultPerson = new Person(model, true);
        defaultPerson.setName("Admin User");
        defaultPerson.addAccount(defaultUserAccount);
        defaultUserAccount.setAccountOf(defaultPerson);

        Service service = new Service(model, true);
        service.setServiceEndpoint(Builder.createURI(rootUri));
        service.setServiceDefinition(Builder.createPlainliteral("Moodle LMS Service"));

        ConnectorCfg config = new ConnectorCfg(model, true);
        config.setId("moodle-test");
        config.setDefaultUser(defaultUserAccount);
        config.setService(service);

        Post replyPost = new Post(model, true);
        replyPost.setContent("i'm a post!");
        replyPost.setCreator(defaultUserAccount);

        Moodle2Connector connector = new Moodle2Connector(context, config);

        try {
            connector.initialize();
        } catch (AuthenticationException | IOException e) {
            e.printStackTrace();
            return;
        }

        List<Forum> forums = connector.getStructureReader().listForums();
        for (Forum forum : forums) {
            System.out.println(forum.getName() + " " + forum);

            List<Thread> threads = connector.getStructureReader().listThreads(forum);
            for (Thread thread : threads) {
                System.out.println(thread.getName() + " " + thread);
                connector.getPostWriter().writePost(replyPost, thread);

                List<Post> posts = connector.getPostReader().readNewPosts(null, -1, thread);

                for (Post post : posts) {
                    System.out.println(post.getContent() + " " + post);
                }
            }
        }

        System.out.println();
        System.out.println("read replies");
        List<Post> posts = connector.getPostReader().readNewReplies(null, -1,
                Post.getInstance(model, Builder.createURI("http://localhost/moodle/post/1")));
        for (Post post : posts) {
            System.out.println(post.getContent() + " " + post);
        }
    }

}
