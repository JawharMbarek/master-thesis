/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.m0ep.socc.connectors.google.youtube;

import java.util.Map;

import org.ontoware.rdf2go.model.Model;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.UserAccount;

import de.m0ep.socc.connectors.AbstractConnector;
import de.m0ep.socc.connectors.ConnectorConfig;
import de.m0ep.socc.utils.ConfigUtils;

public class YoutubeConnector extends AbstractConnector {

    YoutubeConnectorConfig ytConfig;

    public YoutubeConnector(String id, Model model,
	    Map<String, Object> parameters) {
	super(id, model, parameters);

	this.ytConfig = new YoutubeConnectorConfig();
	ConfigUtils.setProperties(ytConfig, parameters);
    }

    @Override
    public String getURL() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ConnectorConfig saveConfiguration() {
	return ytConfig;
    }

    @Override
    public Site getSite() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public UserAccount getLoginUser() {
	// TODO Auto-generated method stub
	return null;
    }

}
