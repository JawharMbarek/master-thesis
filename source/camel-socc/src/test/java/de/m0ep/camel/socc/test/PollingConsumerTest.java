package de.m0ep.camel.socc.test;


import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ontoware.rdf2go.model.Model;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Thread;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.SOCC;

public class PollingConsumerTest extends TestCase {

    private Model model;
    private Forum forum;
    private Thread thread;
    
    @Before
    protected void setUp() throws Exception {
	super.setUp();
	
	this.model = createPollingTestModel();
    }
    
    protected Model createPollingTestModel() {
	Model result = SOCC.createDefaultMemoryModel();
	
	forum = new Forum(result, result.newRandomUniqueURI(),true);
	forum.setId("test-forum");
	
	thread = new Thread(result, result.newRandomUniqueURI(), true);
	thread.setId("test-thread");
	
	return result;
    }

    @Test
    public void testPolling() {
	fail("Not yet implemented");
	
	IConnector connector = Mockito.mock(IConnector.class);
	Mockito.when(connector.getId()).thenReturn("mock-connector");
	Mockito.when(connector.getModel()).thenReturn(model);
	
    }

}

