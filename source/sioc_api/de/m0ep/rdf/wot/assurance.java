package de.m0ep.rdf.wot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openrdf.annotations.Iri;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER,
        ElementType.ANNOTATION_TYPE, ElementType.PACKAGE } )
public @interface assurance {
    @Iri( "http://xmlns.com/wot/0.1/assurance" )
    String[] value();

}
