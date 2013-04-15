package de.m0ep.camel.socc;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.SOCC;

public class SOCCComponent extends DefaultComponent {
    private static final Logger LOG = LoggerFactory
	    .getLogger(SOCCComponent.class);
    SOCC socc;

    public SOCCComponent() {
    }

    public SOCCComponent(final SOCC socc) {
	this.socc = Preconditions.checkNotNull(socc, "Socc can not be null");
    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining,
	    Map<String, Object> parameters) throws Exception {

	SOCCComponentConfiguration configuration = new SOCCComponentConfiguration();
	setProperties(configuration, parameters);

	System.err.println(socc.getConnectorIds().size());
	for (String con : socc.getConnectorIds()) {
	    System.err.println(con);
	    System.err.println(socc.getConnector(con));
	    System.err.println();
	}

	IConnector connector = socc.getConnector(remaining);

	if (null != configuration.getPostId()) {
	    return new SOCCPostEndpoint(uri, connector, configuration);
	} else if (null != configuration.getForumId()
		&& null == configuration.getThreadId()) {
	    return new SOCCForumEndpoint(uri, connector, configuration);
	} else if (null != configuration.getThreadId()) {
	    return new SOCCThreadEndpoint(uri, connector, configuration);
	}

	throw new Exception("");
    }

    @Override
    public void start() throws Exception {
	super.start();
	LOG.debug("start component");
    }

    @Override
    public void stop() throws Exception {
	super.stop();

	LOG.debug("stop component");
    }

    /**
     * @return the socc
     */
    public SOCC getSocc() {
	return this.socc;
    }

    /**
     * @param socc
     *            the socc to set
     */
    public void setSocc(SOCC socc) {
	this.socc = socc;
    }
}
