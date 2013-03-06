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

package de.m0ep.socc.connectors.google.plus;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.UserAccount;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import com.google.api.services.plus.model.Person.Emails;
import com.google.common.base.Throwables;

import de.m0ep.socc.connectors.AbstractConnector;
import de.m0ep.socc.connectors.ConnectorConfig;
import de.m0ep.socc.utils.ConfigUtils;
import de.m0ep.socc.utils.RDF2GoUtils;

public class GooglePlusConnector extends AbstractConnector {

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    GooglePlusConnectorConfig gpConfig;
    GoogleCredential credential;

    Plus plus;
    String myId;

    public GooglePlusConnector(String id, Model model,
	    Map<String, Object> parameters) {
	super(id, model, parameters);

	this.gpConfig = new GooglePlusConnectorConfig();
	ConfigUtils.setProperties(gpConfig, parameters);

	credential = new GoogleCredential.Builder().setClientSecrets(
		gpConfig.getClientId(), gpConfig.getClientSecret()).build();
	credential.setAccessToken(gpConfig.getAccessToken());
	credential.setRefreshToken(gpConfig.getRefreshToken());

	plus = new Plus.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
		.setApplicationName("SOCC").build();

	try {
	    Person me = plus.people().get("me").setFields("id").execute();
	    myId = me.getId();
	} catch (IOException e) {
	    Throwables.propagate(e);
	}
    }

    @Override
    public String getURL() {
	return "https://plus.google.com/";
    }

    @Override
    public ConnectorConfig saveConfiguration() {
	return gpConfig;
    }

    @Override
    public Site getSite() {
	URI uri = RDF2GoUtils.createURI(getURL());

	if (!Site.hasInstance(getModel(), uri)) {
	    Site result = new Site(getModel(), uri, true);
	    result.setName("Google+");
	    return result;
	} else {
	    return Site.getInstance(getModel(), uri);
	}
    }

    @Override
    public UserAccount getLoginUser() {
	return getUserAccount(myId);
    }

    public UserAccount getUserAccount(String id) {
	Person user = null;
	try {
	    user = plus
		    .people()
		    .get(id)
		    .setFields(
			    "aboutMe,displayName,emails/value,id,image,nickname,url")
		    .execute();

	} catch (IOException e) {
	    Throwables.propagate(e);
	}

	URI uri = RDF2GoUtils.createURI(getURL() + user.getId());

	if (!UserAccount.hasInstance(getModel(), uri)) {
	    UserAccount result = new UserAccount(getModel(), uri, true);

	    result.setId(user.getId());
	    result.setIsPartOf(getSite());

	    if (null != user.getAboutMe()) {
		result.setDescription(user.getAboutMe());
	    }

	    if (null != user.getDisplayName()) {
		result.setName(user.getDisplayName());
	    }

	    if (null != user.getNickname()) {
		result.setAccountname(user.getNickname());
	    }

	    if (null != user.getUrl()) {
		result.setAccountservicehomepage(RDF2GoUtils.createURI(user
			.getUrl()));
	    }

	    if (null != user.getEmails()) {
		for (Emails email : user.getEmails()) {
		    result.addEmail(RDF2GoUtils.createMailtoURI(email
			    .getValue()));
		    result.addEmailsha1(DigestUtils.sha1Hex(email.getValue()));
		}
	    }

	    if (null != user.getImage()) {
		result.setAvatar(RDF2GoUtils
			.createURI(user.getImage().getUrl()));
	    }

	    return result;
	} else {
	    return UserAccount.getInstance(getModel(), uri);
	}
    }

}
