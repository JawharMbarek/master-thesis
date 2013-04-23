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

import java.util.Map;

import org.ontoware.rdf2go.model.Model;

import de.m0ep.socc.IConnector;
import de.m0ep.socc.IConnectorFactory;
import de.m0ep.socc.SOCCConstants;
import de.m0ep.socc.config.DataField;
import de.m0ep.socc.config.DataForm;
import de.m0ep.socc.config.DataType;
import de.m0ep.socc.exceptions.ConnectorException;

public class GooglePlusConnectorFactory implements IConnectorFactory {

    @Override
    public String getConnectorName() {
	return "Google+";
    }

    @Override
    public String getId() {
	return "GooglePlusConnectorFactory";
    }

    @Override
    public DataForm getParameterForm() {
	DataForm dataForm = new DataForm();

	dataForm.addField(new DataField.Builder()
		.setName(GooglePlusConnectorConfig.CLIENT_ID)
		.setLabel("Client Id").setType(DataType.STRING).isHidden()
		.isRequired().create());

	dataForm.addField(new DataField.Builder()
		.setName(GooglePlusConnectorConfig.CLIENT_SECRET)
		.setLabel("Client Secret").setType(DataType.STRING).isHidden()
		.isRequired().create());

	dataForm.addField(new DataField.Builder()
		.setName(GooglePlusConnectorConfig.ACCESS_TOKEN)
		.setLabel("Accesstoken").setType(DataType.STRING).isHidden()
		.isRequired().create());

	dataForm.addField(new DataField.Builder()
		.setName(GooglePlusConnectorConfig.REFRESH_TOKEN)
		.setLabel("Refreshtoken").setType(DataType.STRING).isHidden()
		.isRequired().create());

	dataForm.addField(new DataField.Builder()
		.setName(GooglePlusConnectorConfig.EXPIRES_IN_SECONDS)
		.setLabel("Expires in (s)").setType(DataType.INTEGER).create());

	dataForm.addField(new DataField.Builder()
		.setName(GooglePlusConnectorConfig.MAX_NEW_POSTS_ON_POLL)
		.setLabel("Max Post per Poll").setType(DataType.INTEGER)
		.setDefaultValue(SOCCConstants.POLL_MAX_NEW_POST).isPositive()
		.create());

	dataForm.addField(new DataField.Builder()
		.setName(GooglePlusConnectorConfig.POLL_COOLDOWN)
		.setLabel("Poll Cooldown").setType(DataType.INTEGER)
		.setDefaultValue(SOCCConstants.POLL_COOLDOWN_MILLIS)
		.isPositive().create());

	return dataForm;
    }

    @Override
    public IConnector createConnector(String id, Model model,
	    Map<String, Object> parameters) throws ConnectorException {

	IConnector connector = new GooglePlusConnector();
	connector.initialize(id, model, parameters);
	return connector;
    }

}
