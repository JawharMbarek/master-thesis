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

package de.m0ep.camel.socc.data;

import java.util.ArrayList;
import java.util.List;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.util.RDFTool;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SIOC;

import com.google.common.base.Preconditions;

import de.m0ep.socc.utils.RDF2GoUtils;

public class SIOCPostData {
	private String uri;
	private List<Statement> statements;

	public SIOCPostData(final String uri, final List<Statement> statements) {
		this.uri = Preconditions.checkNotNull(uri, "Uri can not be null");
		Preconditions.checkArgument(!uri.isEmpty(), "Uri can not be empty");

		this.statements = new ArrayList<Statement>(
				Preconditions.checkNotNull(
						statements,
						"Statements can not be null"));
		Preconditions.checkArgument(
				!statements.isEmpty(),
				"Statements can not be empty");
	}

	public SIOCPostData(final String xml) {
		Preconditions.checkNotNull(xml, "Xml can not be null");
		Preconditions.checkArgument(!xml.isEmpty(), "Xml can not be empty");

		Model model = RDFTool.stringToModel(xml);

		try {
			List<Post> postList = SIOC.listAllPosts(model);

			if (!postList.isEmpty()) {
				Post post = postList.get(0);
				uri = post.asURI().toString();
				this.statements = new ArrayList<Statement>(
						RDF2GoUtils.getAllStatements(model, post));
			}
			else {
				throw new IllegalArgumentException("XML data contains no post");
			}
		} finally {
			model.close();
		}
	}

	public String getId() {
		return this.uri;
	}

	public List<Statement> getRDFStatements() {
		return this.statements;
	}
}
