package de.m0ep.uni.ma.rdf;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

public class RDFBase {
    private Resource resource = null;
    private Model    model    = null;

    public RDFBase( final Model model, final Resource resource ) {
        this.model = model;
        this.resource = resource;
    }

    public Resource resource() {
        return resource;
    }

    public Model model() {
        return model;
    }

    public String uri() {
        return resource.getURI();
    }

    public boolean isRDFType( final Resource type ) {
        return resource.hasProperty( RDF.type, type );
    }

    public boolean isRDFSSubClassOf( final Resource type ) {
        return resource.hasProperty( RDFS.subClassOf, type );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( model == null ) ? 0 : model.hashCode() );
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

        RDFBase other = (RDFBase) obj;

        if( model == null ) {
            if( other.model != null )
                return false;
        } else if( !model.equals( other.model ) )
            return false;

        if( resource == null ) {
            if( other.resource != null )
                return false;
        } else if( !resource.equals( other.resource ) )
            return false;

        return true;
    }
}
