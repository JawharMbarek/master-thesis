/*
 * The MIT License (MIT) Copyright © 2013 "Florian Mueller"
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

package de.m0ep.socc.core.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;

import com.google.api.client.util.Lists;
import com.google.common.base.Objects;

/**
 * Class that help to extract HTML-Links from a text.
 * 
 * @author "Florian Mueller"
 */
public class HtmlLinkExtractor {
	private static final String REGEX_HTML_LINK = "(?i)<a([^>]+)>(.+?)</a>";

	private static final String REGEX_HTML_LINK_HREF =
	        "\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))";

	private static Pattern linkPattern;
	private static Pattern hrefPattern;

	static {
		linkPattern = Pattern.compile( REGEX_HTML_LINK );
		hrefPattern = Pattern.compile( REGEX_HTML_LINK_HREF );
	}

	private HtmlLinkExtractor() {
	}

	/**
	 * Extracts all HTML-Links from a String.
	 * 
	 * @param html
	 *            Text where the Links should be extract from.
	 * @return List of all found HTML-Links
	 */
	public static List<Link> extractLinks( String html ) {
		List<Link> result = Lists.newArrayList();

		Matcher linkMatcher = linkPattern.matcher( html );

		while ( linkMatcher.find() ) {
			String linkHref = linkMatcher.group( 1 );
			String linkText = linkMatcher.group( 2 );

			Matcher hrefMatcher = hrefPattern.matcher( linkHref );

			while ( hrefMatcher.find() ) {
				String href = hrefMatcher.group( 1 );

				if ( href.startsWith( "'" ) || href.startsWith( "\"" ) ) {
					href = href.substring( 1 );
				}

				if ( href.endsWith( "'" ) || href.endsWith( "\"" ) ) {
					href = href.substring( 0, href.length() - 1 );
				}

				result.add( new Link( Builder.createURI( href.trim() ), linkText ) );
			}
		}

		return result;
	}

	/**
	 * Class that stores a HTML-Link with URI and text.
	 * 
	 * @author "Florian Mueller"
	 */
	public static class Link {
		private final URI uri;
		private final String text;

		/**
		 * Constructs a new inmutable HTML-Link
		 * 
		 * @param uri
		 * @param text
		 */
		public Link( URI uri, String text ) {
			this.uri = uri;
			this.text = text;
		}

		public URI getUri() {
			return uri;
		}

		public String getText() {
			return text;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode( uri, text );
		}

		@Override
		public boolean equals( Object obj ) {
			if ( this == obj ) {
				return true;
			}

			if ( null == obj || this.getClass() != obj.getClass() ) {
				return false;
			}

			HtmlLinkExtractor.Link other = (HtmlLinkExtractor.Link) obj;

			return Objects.equal( this.uri, other.uri )
			        && Objects.equal( this.text, other.text );
		}

		@Override
		public String toString() {
			return "<a href=\"" + uri + "\">" + text + "</a>";
		}
	}
}
