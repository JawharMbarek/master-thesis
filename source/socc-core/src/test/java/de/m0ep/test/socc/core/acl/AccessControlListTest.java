
package de.m0ep.test.socc.core.acl;

import java.util.EnumSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.ontoware.aifbcommons.collection.ClosableIterator;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.Variable;
import org.ontoware.rdf2go.util.Builder;
import org.ontoware.rdfreactor.schema.rdfs.Class;
import org.rdfs.sioc.Post;
import org.w3.ns.auth.acl.AclVocabulary;
import org.w3.ns.auth.acl.Authorization;

import com.xmlns.foaf.Agent;

import de.m0ep.socc.core.acl.Access;
import de.m0ep.socc.core.acl.AccessControlList;
import de.m0ep.socc.core.acl.IAccessControlList;

public class AccessControlListTest {

    private static final URI SOCC_BOT_AGENT_URI = Builder
            .createURI("http://example.com/agent/soccBot");
    private static final URI SAMPLE_USERACCOUNT_URI = Builder
            .createURI("http://example.com/agent/johnDoe");
    private static final URI SAMPLE_USERACCOUNT2_URI = Builder
            .createURI("http://example.com/agent/jennyDoe");
    private static final URI SAMPLE_RESOURCE_URI = Builder
            .createURI("http://example.com/resource");
    private static final URI SAMPLE_CLASS_URI = Post.RDFS_CLASS;

    @Test
    public void testAddAuthorizationForResource() {
        Model model = createEmptyModel();

        IAccessControlList acl = new AccessControlList(model, SOCC_BOT_AGENT_URI);
        Agent user = Agent.getInstance(model, SAMPLE_USERACCOUNT_URI);

        acl.addAuthorizationForResource(
                user,
                SAMPLE_RESOURCE_URI,
                EnumSet.of(
                        Access.READ,
                        Access.WRITE,
                        Access.APPEND,
                        Access.CONTROL));

        Assert.assertTrue(
                "No new auhtorization found.",
                model.contains(Variable.ANY, AclVocabulary.owner, user));
        ClosableIterator<Statement> stmtIter = model.findStatements(
                Variable.ANY,
                AclVocabulary.owner,
                user);

        Statement stmt = stmtIter.next();
        Assert.assertFalse("There should only be one entry with this creator", stmtIter.hasNext());
        stmtIter.close();

        Authorization authorization = Authorization.getInstance(model, stmt.getSubject());
        Assert.assertNotNull("There should be at least one authorization.", authorization);

        Assert.assertTrue("Agent property should be '" + SOCC_BOT_AGENT_URI + "'.",
                model.contains(authorization, Authorization.AGENT, SOCC_BOT_AGENT_URI));

        Assert.assertTrue("AccessTo should point to '" + SAMPLE_RESOURCE_URI + "'.",
                model.contains(authorization, Authorization.ACCESS_TO, SAMPLE_RESOURCE_URI));

        Assert.assertTrue("Read permission missing.",
                model.contains(
                        authorization,
                        Authorization.ACCESSMODE,
                        Access.READ.asClass(model)));

        Assert.assertTrue("Read permission missing.",
                model.contains(
                        authorization,
                        Authorization.ACCESSMODE,
                        Access.WRITE.asClass(model)));

        Assert.assertTrue("Read permission missing.",
                model.contains(
                        authorization,
                        Authorization.ACCESSMODE,
                        Access.APPEND.asClass(model)));

        Assert.assertTrue("Read permission missing.",
                model.contains(
                        authorization,
                        Authorization.ACCESSMODE,
                        Access.CONTROL.asClass(model)));

        model.close();
    }

    @Test
    public void testAddAuthorizationForClass() {
        Model model = createEmptyModel();

        IAccessControlList acl = new AccessControlList(model, SOCC_BOT_AGENT_URI);
        Agent user = Agent.getInstance(model, SAMPLE_USERACCOUNT_URI);

        acl.addAuthorizationForClass(
                user,
                SAMPLE_CLASS_URI,
                EnumSet.of(
                        Access.READ,
                        Access.WRITE,
                        Access.APPEND,
                        Access.CONTROL));

        Assert.assertTrue(
                "No new auhtorization found.",
                model.contains(Variable.ANY, AclVocabulary.owner, user));
        ClosableIterator<Statement> stmtIter = model.findStatements(
                Variable.ANY,
                AclVocabulary.owner,
                user);

        Statement stmt = stmtIter.next();
        Assert.assertFalse("There should only be one entry with this creator", stmtIter.hasNext());

        Authorization authorization = Authorization.getInstance(model, stmt.getSubject());
        Assert.assertNotNull("There should be at least one authorization.", authorization);

        Assert.assertTrue("Agent property should be '" + SOCC_BOT_AGENT_URI + "'.",
                model.contains(authorization, Authorization.AGENT, SOCC_BOT_AGENT_URI));

        Assert.assertTrue("AccessTo should point to '" + SAMPLE_RESOURCE_URI + "'.",
                model.contains(authorization, Authorization.ACCESS_TO_CLASS, SAMPLE_CLASS_URI));

        Assert.assertTrue("Read permission missing.",
                model.contains(
                        authorization,
                        Authorization.ACCESSMODE,
                        Access.READ.asClass(model)));

        Assert.assertTrue("Read permission missing.",
                model.contains(
                        authorization,
                        Authorization.ACCESSMODE,
                        Access.WRITE.asClass(model)));

        Assert.assertTrue("Read permission missing.",
                model.contains(
                        authorization,
                        Authorization.ACCESSMODE,
                        Access.APPEND.asClass(model)));

        Assert.assertTrue("Read permission missing.",
                model.contains(
                        authorization,
                        Authorization.ACCESSMODE,
                        Access.CONTROL.asClass(model)));

        model.close();
    }

    @Test
    public void testListAuthorizations() {
        Model model = createFilledModel();

        IAccessControlList acl = new AccessControlList(model, SOCC_BOT_AGENT_URI);
        Agent user = Agent.getInstance(model, SAMPLE_USERACCOUNT_URI);

        List<Authorization> authorizations = acl.listAuthorizations(user);

        Assert.assertNotNull("Should return empty list not 'null'.", authorizations);
        Assert.assertEquals(
                "There should be 2 authorizations created by '" + SAMPLE_USERACCOUNT_URI + ".",
                2, authorizations.size());

        for (Authorization authorization : authorizations) {
            Assert.assertTrue(authorization.hasOwner(user));
            Assert.assertTrue(authorization.hasAgent(SOCC_BOT_AGENT_URI));

            if (authorization.hasAccessTo()) {
                Assert.assertTrue(authorization.hasAccessTo(SAMPLE_RESOURCE_URI));
            }

            if (authorization.hasAccessToClass()) {
                Assert.assertTrue(authorization.hasAccessToClass(SAMPLE_CLASS_URI));
            }
        }

        model.close();
    }

    @Test
    public void testCheckAuthorizationForResource() {
        Model model = createFilledModel();

        IAccessControlList acl = new AccessControlList(model, SOCC_BOT_AGENT_URI);
        Agent user = Agent.getInstance(model, SAMPLE_USERACCOUNT_URI);

        Assert.assertTrue(acl.checkAuthorizationForResource(
                user,
                SAMPLE_RESOURCE_URI,
                EnumSet.of(Access.READ)));

        Assert.assertTrue(acl.checkAuthorizationForResource(
                user,
                SAMPLE_RESOURCE_URI,
                EnumSet.of(Access.READ, Access.WRITE)));

        Assert.assertFalse(acl.checkAuthorizationForResource(
                user,
                SAMPLE_RESOURCE_URI,
                EnumSet.of(Access.APPEND)));

        Assert.assertFalse(acl.checkAuthorizationForResource(
                user,
                SAMPLE_RESOURCE_URI,
                EnumSet.of(Access.WRITE, Access.CONTROL)));

        model.close();
    }

    @Test
    public void testCheckAuthorizationForClass() {
        Model model = createFilledModel();

        IAccessControlList acl = new AccessControlList(model, SOCC_BOT_AGENT_URI);
        Agent user = Agent.getInstance(model, SAMPLE_USERACCOUNT_URI);

        Assert.assertTrue(acl.checkAuthorizationForClass(
                user,
                SAMPLE_CLASS_URI,
                EnumSet.of(Access.READ)));

        Assert.assertFalse(acl.checkAuthorizationForClass(
                user,
                SAMPLE_CLASS_URI,
                EnumSet.of(Access.READ, Access.WRITE)));

        Assert.assertFalse(acl.checkAuthorizationForClass(
                user,
                SAMPLE_CLASS_URI,
                EnumSet.of(Access.APPEND)));

        Assert.assertFalse(acl.checkAuthorizationForClass(
                user,
                SAMPLE_CLASS_URI,
                EnumSet.of(Access.WRITE, Access.CONTROL)));

        model.close();
    }

    private Model createEmptyModel() {
        Model model = RDF2Go.getModelFactory().createModel();
        model.open();

        new Agent(model, SAMPLE_USERACCOUNT_URI, true);

        return model;
    }

    private Model createFilledModel() {
        Model model = createEmptyModel();
        Agent user = Agent.getInstance(model, SAMPLE_USERACCOUNT_URI);
        Agent user2 = Agent.getInstance(model, SAMPLE_USERACCOUNT2_URI);

        Authorization resAuth = new Authorization(model, model.createBlankNode(), true);
        resAuth.setOwner(user);
        resAuth.setAccessTo(SAMPLE_RESOURCE_URI);
        resAuth.setAgent(new Agent(model, SOCC_BOT_AGENT_URI, false));
        resAuth.addAccessMode(Access.READ.asClass(model));
        resAuth.addAccessMode(Access.WRITE.asClass(model));

        Authorization resAuth2 = new Authorization(model, model.createBlankNode(), true);
        resAuth2.setOwner(user);
        resAuth2.setAccessToClass(new Class(model, SAMPLE_CLASS_URI, false));
        resAuth2.setAgent(new Agent(model, SOCC_BOT_AGENT_URI, false));
        resAuth2.addAccessMode(Access.READ.asClass(model));

        Authorization resAuth3 = new Authorization(model, model.createBlankNode(), true);
        resAuth3.setOwner(user2);
        resAuth3.setAccessToClass(new Class(model, SAMPLE_CLASS_URI, false));
        resAuth3.setAgent(new Agent(model, SOCC_BOT_AGENT_URI, false));
        resAuth3.addAccessMode(Access.READ.asClass(model));

        return model;
    }
}
