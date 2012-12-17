package de.m0ep.uni.ma.test.rdf.sioc;

import org.junit.Before;
import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileUtils;

import de.m0ep.uni.ma.rdf.sioc.Container;
import de.m0ep.uni.ma.rdf.sioc.Item;
import de.m0ep.uni.ma.rdf.sioc.UserAccount;
import de.m0ep.uni.ma.rdf.vocabulary.FOAF;
import de.m0ep.uni.ma.rdf.vocabulary.SIOC;

public class ContainerTest {

    public static final String NS = "http://m0ep.de/uni/ma/";

    Model                      model;

    @Before
    public void setUp() throws Exception {
        model = ModelFactory.createDefaultModel();
        model.setNsPrefix( "sioc", SIOC.NS );
        model.setNsPrefix( "foaf", FOAF.NS );
    }

    @Test
    public void test() {
        Container container = Container.create( model, NS + "test1" );
        Item item = Item.createItem( model, NS + "item1" );
        Item item2 = Item.createItem( model, NS + "item2" );
        UserAccount account = UserAccount.create( model, NS + "user1" );

        container.addItem( item );
        container.addItem( item2 );
        container.addSubscriber( account );

        model.write( System.out, FileUtils.langXMLAbbrev );
        System.out.println( "-----" );
        container.removeItem( item );
        model.write( System.out, FileUtils.langXMLAbbrev );
    }
}
