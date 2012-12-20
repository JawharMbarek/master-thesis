package de.m0ep.rdf.owl;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * Implemantation of an OWL Thing
 * 
 * @author Florian Müller
 */
public class ThingImpl implements Thing {
    private Model    model;
    private Resource resource;

    protected ThingImpl( final Resource resource, final Model model ) {
        this.model = model;
        this.resource = resource;
    }

    /**
     * Static method to access an existing resource as a Thing
     * 
     * @param resource
     * @param model
     * @return
     */
    public static ThingImpl get( final Resource resource, final Model model ) {
        return new ThingImpl( resource, model );
    }

    /**
     * Static mehtod to create a new Thing
     * 
     * @param uri
     * @param model
     * @return
     */
    public static ThingImpl create( final String uri, final Model model ) {
        return create( model.createResource( uri ), model );
    }

    /**
     * Static mehtod to create a new Thing
     * 
     * @param resource
     * @param model
     * @return
     */
    public static ThingImpl create( final Resource resource, final Model model ) {
        ThingImpl impl = new ThingImpl( resource, model );

        return impl;
    }

    /**
     * Get the URI of this Thing
     */
    public String getUri() {
        return resource.getURI();
    }

    /**
     * Get the Jena Resource behind this Thing
     */
    public Resource resource() {
        return resource;
    }

    /**
     * Get the Jena Model which stores this resource
     */
    public Model model() {
        return model;
    }

    /**
     * Return true if this Thing has the give RDF Type, false otherwise
     */
    public boolean isRDFType( Resource type ) {
        return resource.hasProperty( RDF.type, type );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result
                + ( ( resource == null ) ? 0 : resource.hashCode() );

        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if( this == obj )
            return true;

        if( obj == null )
            return false;

        if( getClass() != obj.getClass() )
            return false;

        ThingImpl other = (ThingImpl) obj;

        if( resource == null ) {
            if( other.resource != null )
                return false;
        } else if( !resource.equals( other.resource ) )
            return false;
        return true;
    }

}
