package de.m0ep.socc.connectors.google.plus;

import java.util.Properties;

import org.ontoware.rdf2go.model.Model;
import org.rdfs.sioc.Site;
import org.rdfs.sioc.UserAccount;

import de.m0ep.socc.connectors.AbstractConnector;

public class GooglePlusConnector extends AbstractConnector {

    public static final String CONFIG_ACCESS_TOKEN = "access_token";
    public static final String CONFIG_REFRESH_TOKEN = "refresh_token";
    public static final String CONFIG_CLIENT_ID = "client_id";
    public static final String CONFIG_CLIENT_SECRET = "client_secret";

    public GooglePlusConnector(String id, Model model, Properties config) {
	super(id, model, config);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String getURL() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Site getSite() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public UserAccount getUser() {
	// TODO Auto-generated method stub
	return null;
    }

}
