package de.m0ep.uni.ma.rdf.sioc;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import de.m0ep.uni.ma.rdf.RDFBase;
import de.m0ep.uni.ma.rdf.vocabulary.SIOC;

public class Item extends RDFBase {

    Item( Model model, Resource resource ) {
        super( model, resource );
    }

    public static Item getItem( final Model model, final Resource resource ) {
        return new Item( model, resource );
    }

    public static Item createItem( final Model model, final String uri ) {
        return createItem( model, model.createResource( uri ) );
    }

    public static Item createItem( final Model model, final Resource resource ) {
        Item item = new Item( model, resource );

        if( !item.isRDFType( SIOC.Item ) )
            item.resource().addProperty( RDF.type, SIOC.Item );

        return item;
    }
}
