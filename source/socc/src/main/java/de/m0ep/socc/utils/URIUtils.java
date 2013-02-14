package de.m0ep.socc.utils;

import org.ontoware.rdf2go.model.node.Literal;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.PlainLiteralImpl;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

public class URIUtils {

    public static URI createURI( final String value ) {
        return new URIImpl( value, true );
    }

    public static URI createMailtoURI( final String email ) {
        return new URIImpl( "mailto:" + email );
    }

    public static Literal createLiteral( final String value ) {
        return new PlainLiteralImpl( value );
    }
}
