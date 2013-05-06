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
    private SOCC socc;

    public SOCCComponent() {
    }

    public SOCCComponent(final SOCC socc) {
	this.socc = Preconditions.checkNotNull(socc, "Socc can not be null");
    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining,
	    Map<String, Object> parameters) throws Exception {

	EndpointProperties configuration = new EndpointProperties();
	setProperties(configuration, parameters);
	IConnector connector = socc.getConnector(remaining);

	if (null != configuration.getPostId()) {
	    return new PostEndpoint(this, uri, configuration, connector);
	} else if (null != configuration.getForumId()
		&& null == configuration.getThreadId()) {
	    return new ForumEndpoint(this, uri, configuration, connector);
	} else if (null != configuration.getThreadId()) {
	    return new ThreadEndpoint(this, uri, configuration, connector);
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
    public SOCC getSOCC() {
	return this.socc;
    }

    /**
     * @param socc
     *            the socc to set
     */
    public void setSOCC(SOCC socc) {
	this.socc = socc;
    }
}
