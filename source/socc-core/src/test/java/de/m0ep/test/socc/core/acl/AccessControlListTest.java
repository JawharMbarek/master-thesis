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

package de.m0ep.test.socc.core.acl;

import org.junit.Assert;
import org.junit.Test;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;
import org.rdfs.sioc.SiocVocabulary;
import org.w3.ns.auth.acl.AclVocabulary;
import org.w3.ns.auth.acl.Authorization;

import com.google.common.collect.Sets;
import com.xmlns.foaf.Agent;
import com.xmlns.foaf.FoafVocabulary;

import de.m0ep.socc.core.acl.AccessControl;
import de.m0ep.socc.core.acl.IAccessControl;

public class AccessControlListTest {
	private static final URI SAMPLE_USERACCOUNT_URI = Builder
	        .createURI( "http://example.com/agent/johnDoe" );
	private static final URI SAMPLE_USERACCOUNT2_URI = Builder
	        .createURI( "http://example.com/agent/jennyDoe" );
	private static final URI SAMPLE_RESOURCE_URI = Builder
	        .createURI( "http://example.com/resource" );

	@Test
	public void testCheckAccessTo() {
		Model model = createFilledModel();

		IAccessControl instance = new AccessControl( model );
		Agent user = Agent.getInstance( model, SAMPLE_USERACCOUNT_URI );

		Assert.assertTrue( instance.checkAccessTo(
		        user,
		        SAMPLE_RESOURCE_URI,
		        Sets.newHashSet(
		                AclVocabulary.Read ) ) );

		Assert.assertTrue( instance.checkAccessTo(
		        user,
		        SAMPLE_RESOURCE_URI,
		        Sets.newHashSet(
		                AclVocabulary.Read,
		                AclVocabulary.Write ) ) );

		Assert.assertFalse( instance.checkAccessTo(
		        user,
		        SAMPLE_RESOURCE_URI,
		        Sets.newHashSet(
		                AclVocabulary.Append ) ) );

		Assert.assertFalse( instance.checkAccessTo(
		        user,
		        SAMPLE_RESOURCE_URI,
		        Sets.newHashSet(
		                AclVocabulary.Write,
		                AclVocabulary.Control ) ) );

		model.close();
	}

	@Test
	public void testCheckAccessToClass() {
		Model model = createFilledModel();

		IAccessControl acl = new AccessControl( model );
		Agent user = Agent.getInstance( model, SAMPLE_USERACCOUNT_URI );

		Assert.assertTrue( acl.checkAccessToClass(
		        user,
		        SiocVocabulary.Post,
		        Sets.newHashSet(
		                AclVocabulary.Read ) ) );

		Assert.assertFalse( acl.checkAccessToClass(
		        user,
		        SiocVocabulary.Post,
		        Sets.newHashSet(
		                AclVocabulary.Read,
		                AclVocabulary.Write ) ) );

		Assert.assertFalse( acl.checkAccessToClass(
		        user,
		        SiocVocabulary.Post,
		        Sets.newHashSet(
		                AclVocabulary.Append ) ) );

		Assert.assertFalse( acl.checkAccessToClass(
		        user,
		        SiocVocabulary.Post,
		        Sets.newHashSet(
		                AclVocabulary.Write,
		                AclVocabulary.Control ) ) );

		model.close();
	}

	private Model createEmptyModel() {
		Model model = RDF2Go.getModelFactory().createModel();
		model.open();

		new Agent( model, SAMPLE_USERACCOUNT_URI, true );
		new Agent( model, SAMPLE_USERACCOUNT2_URI, true );

		return model;
	}

	private Model createFilledModel() {
		Model model = createEmptyModel();
		Agent user = Agent.getInstance( model, SAMPLE_USERACCOUNT_URI );
		Agent user2 = Agent.getInstance( model, SAMPLE_USERACCOUNT2_URI );

		Authorization resAuth = new Authorization( model, true );
		resAuth.setOwner( user );
		resAuth.setAccessTo( SAMPLE_RESOURCE_URI );
		resAuth.setAgentClass( FoafVocabulary.Agent );
		resAuth.addAccessMode( AclVocabulary.Read );
		resAuth.addAccessMode( AclVocabulary.Write );

		Authorization resAuth2 = new Authorization( model, true );
		resAuth2.setOwner( user );
		resAuth2.setAccessToClass( SiocVocabulary.Post );
		resAuth2.setAgentClass( FoafVocabulary.Agent );
		resAuth2.addAccessMode( AclVocabulary.Read );

		Authorization resAuth3 = new Authorization( model, true );
		resAuth3.setOwner( user2 );
		resAuth3.setAccessToClass( SiocVocabulary.Post );
		resAuth3.setAgentClass( FoafVocabulary.Agent );
		resAuth3.addAccessMode( AclVocabulary.Read );

		return model;
	}
}
