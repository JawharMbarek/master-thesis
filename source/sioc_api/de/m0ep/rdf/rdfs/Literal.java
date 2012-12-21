package de.m0ep.rdf.rdfs;

import org.openrdf.annotations.Iri;

@Iri( "http://www.w3.org/2000/01/rdf-schema#Literal" )
public class Literal {
    public static Literal valueOf( String value ) {
        return new Literal( value );
    }

    private String value;

    public Literal( String value ) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public int hashCode() {
        return value.hashCode();
    }

    public boolean equals( Object o ) {
        return getClass().equals( o.getClass() )
                && toString().equals( o.toString() );
    }

}
