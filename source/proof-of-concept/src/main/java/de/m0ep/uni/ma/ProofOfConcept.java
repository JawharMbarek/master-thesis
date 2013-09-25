package de.m0ep.uni.ma;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.io.FileUtils;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.util.RDFTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m0ep.socc.camel.SoccComponent;
import de.m0ep.socc.core.SoccContext;

public class ProofOfConcept {
	private static final Logger LOG = LoggerFactory.getLogger( ProofOfConcept.class );

	public static final String CANVAS_TOPIC = "canvas-topic";
	public static final String FACEBOOK_TOPIC = "facebook-topic";

	public static void main( String[] args ) {
		ProofOfConcept poc = new ProofOfConcept();
		poc.start();
	}

	private BrokerService activeMqBroker;
	private DefaultCamelContext camelContext;
	private Model model;

	public void start() {
		initModel();
		initCamelContext();
		startActiveMqBroker();

		try {
			camelContext.addRoutes( routeBuilder );
			camelContext.start();
		} catch ( Exception e ) {
			LOG.error( "Failed to add routes and start CamelContext", e );
			System.exit( 1 );
		}

		try {
			Thread.sleep( 30000 );
		} catch ( InterruptedException e ) {
			LOG.warn( "Thread was interrupted", e );
		}

		try {
			camelContext.stop();
			activeMqBroker.stop();
		} catch ( Exception e ) {
			LOG.error( "Errors while shutting down", e );
		}
	}

	private void initModel() {
		File defaultModelFile = null;
		try {
			URL defaultModelUrl = getClass().getClassLoader().getResource( "default_model.ttl" );
			defaultModelFile = new File( defaultModelUrl.toURI() );
		} catch ( URISyntaxException e ) {
			LOG.error( "Failed to get URI of default model file", e );
			System.exit( 1 );
		}

		try {
			String turtleContent = FileUtils.readFileToString( defaultModelFile );

			if ( LOG.isDebugEnabled() ) {
				LOG.debug( "Loaded default model content:\n{}", turtleContent );
			} else {
				LOG.info( "Loaded {} bytes of the default model content.", turtleContent.length() );
			}

			model = RDFTool.stringToModel( turtleContent, Syntax.Turtle );
		} catch ( IOException e ) {
			LOG.error( "Failed to load default model", e );
			System.exit( 1 );
		}
	}

	private final RouteBuilder routeBuilder = new RouteBuilder() {
		@Override
		public void configure() throws Exception {
			from( "socc://poc-canvas?uri=https://canvas.instructure.com/courses/798152/"
			        + "discussion_topics/1440784&delay=10000" )
			        .to( "activemq:topic:" + CANVAS_TOPIC );

			from( "activemq:topic:" + CANVAS_TOPIC )
			        .to( "log:poc-log" );
		}
	};

	private void startActiveMqBroker() {
		LOG.info( "Starting ActiveMQ Broker..." );
		activeMqBroker = new BrokerService();
		activeMqBroker.setPersistent( false );

		try {
			activeMqBroker.addConnector( "tcp://localhost:61616" );
		} catch ( Exception e ) {
			LOG.error( "Failed to add Connector to ActiveMQ Broker", e );
		}

		try {
			activeMqBroker.start();
		} catch ( Exception e ) {
			LOG.error( "Failed to start ActiveMQ Broker", e );
			System.exit( 1 );
		}
	}

	private void initCamelContext() {
		LOG.info( "Starting CamelContext..." );
		camelContext = new DefaultCamelContext();
		camelContext.addComponent( "activemq",
		        ActiveMQComponent.activeMQComponent( "tcp://localhost:61616" ) );

		try {
			camelContext.addComponent(
			        "socc",
			        new SoccComponent(
			                camelContext,
			                new SoccContext( model ) ) );
		} catch ( Exception e ) {
			LOG.error( "Failed to add SoccComponent to CamelContext.", e );
			System.exit( 1 );
		}
	}
}