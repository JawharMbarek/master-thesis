package de.m0ep.uni.ma.rdf.sioc;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

import de.m0ep.uni.ma.rdf.RDFBase;
import de.m0ep.uni.ma.rdf.vocabulary.FOAF;
import de.m0ep.uni.ma.rdf.vocabulary.SIOC;

public class UserAccount extends RDFBase {
    public UserAccount( Model model, Resource resource ) {
        super( model, resource );
    }

    public static UserAccount get( final Model model, final Resource resource ) {
        return new UserAccount( model, resource );
    }

    public static UserAccount create( final Model model, final String uri ) {
        return create( model, model.createResource( uri ) );
    }

    public static UserAccount create( final Model model, final Resource resource ) {
        UserAccount account = new UserAccount( model, resource );

        if( !account.isRDFType( SIOC.UserAccount ) )
            account.resource().addProperty( RDF.type, SIOC.UserAccount );

        if( !account.isRDFSSubClassOf( FOAF.OnlineAccount ) )
            account.resource()
                    .addProperty( RDFS.subClassOf, FOAF.OnlineAccount );

        return account;
    }
}
