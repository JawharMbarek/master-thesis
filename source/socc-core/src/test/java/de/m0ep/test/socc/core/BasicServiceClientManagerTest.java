
package de.m0ep.test.socc.core;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.rdfs.sioc.UserAccount;

import de.m0ep.socc.core.service.BasicServiceClientManager;
import de.m0ep.socc.core.service.IServiceClientManager;

public class BasicServiceClientManagerTest {
    private static final String TEST_CLIENT1_URI = "http://www.example.com/user/1";

    private static final String TEST_CLIENT1 = "client1";

    public static final String DEFAULT_CLIENT = "default";

    private Model model;

    @Before
    public void setUp() throws Exception {
        model = RDF2Go.getModelFactory().createModel();
        model.open();
    }

    @After
    public void tearDown() throws Exception {
        model.close();
    }

    @Test
    public void testSetGetDefaultClient() {
        IServiceClientManager<String> instance = new BasicServiceClientManager<>(DEFAULT_CLIENT);
        instance.setDefaultClient(DEFAULT_CLIENT);
        assertEquals(DEFAULT_CLIENT, instance.getDefaultClient());
    }

    @Test(expected = NullPointerException.class)
    public void testDefaultClientNullPointer() {
        IServiceClientManager<String> instance = new BasicServiceClientManager<>(DEFAULT_CLIENT);
        instance.setDefaultClient(null);
    }

    @Test
    public void testAddGet() {
        IServiceClientManager<String> instance = new BasicServiceClientManager<>(DEFAULT_CLIENT);
        UserAccount client1Acc = new UserAccount(model, TEST_CLIENT1_URI, false);
        instance.add(client1Acc, TEST_CLIENT1);
        assertEquals(TEST_CLIENT1, instance.get(client1Acc));
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullPointerException1() {
        IServiceClientManager<String> instance = new BasicServiceClientManager<>(DEFAULT_CLIENT);
        instance.add(null, TEST_CLIENT1);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullPointerException2() {
        IServiceClientManager<String> instance = new BasicServiceClientManager<>(DEFAULT_CLIENT);
        instance.add(new UserAccount(model, TEST_CLIENT1_URI, false), null);
    }

    @Test
    public void testRemove() {
        IServiceClientManager<String> instance = new BasicServiceClientManager<>(DEFAULT_CLIENT);
        UserAccount client1Acc = new UserAccount(model, TEST_CLIENT1_URI, false);
        instance.add(client1Acc, TEST_CLIENT1);
        assertEquals(TEST_CLIENT1, instance.get(client1Acc));
        instance.remove(client1Acc);
        assertEquals(DEFAULT_CLIENT, instance.get(client1Acc));
    }

    @Test
    public void testGetNotFound() {
        IServiceClientManager<String> instance = new BasicServiceClientManager<>(DEFAULT_CLIENT);
        UserAccount client1Acc = new UserAccount(model, TEST_CLIENT1_URI, false);
        assertEquals(DEFAULT_CLIENT, instance.get(client1Acc));
    }

    @Test
    public void testGetNull() {
        IServiceClientManager<String> instance = new BasicServiceClientManager<>(DEFAULT_CLIENT);
        assertEquals(DEFAULT_CLIENT, instance.get(null));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetIllegalStateException() {
        IServiceClientManager<String> instance = new BasicServiceClientManager<>();
        UserAccount client1Acc = new UserAccount(model, TEST_CLIENT1_URI, false);
        instance.get(client1Acc);
    }
}