package de.m0ep.uni.ma;

import java.io.IOException;
import java.util.Properties;

import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdfreactor.runtime.ReactorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m0ep.uni.ma.rdf.sioc.Forum;
import de.m0ep.uni.ma.rdf.sioc.UserAccount;
import de.m0ep.uni.ma.socc.SIOCFactory;
import de.m0ep.uni.ma.socc.provider.FacebookProvider;

public class FBMdlBridge {
    private static final Logger log = LoggerFactory.getLogger( FBMdlBridge.class );

    public static Properties config;

    static {
        config = new Properties();
        try {
            config.load( ClassLoader
                    .getSystemResourceAsStream( "local.properties" ) );
        } catch ( IOException e ) {
            log.warn( "failed to load config", e );
        }
    }

    /**
     * @param args
     */
    public static void main( String[] args ) {
        Model model = RDF2Go.getModelFactory().createModel();
        model.open();
        
        SIOCFactory siocFactory = new SIOCFactory( model, FacebookProvider.URL );
        FacebookProvider fb = new FacebookProvider( siocFactory );
        fb.init( config );

        for ( Forum forum : fb.getForums() ) {
            System.out.println( forum.getResource().toString() );
            System.out.println( forum.getAllSIOCName_as().firstValue() );

            ReactorResult<String> desc = forum.getAllDCTermsDescription_as();
            if( 0 < desc.count() )
                System.out.println( forum.getAllDCTermsDescription_as()
                        .firstValue() );
            System.out.println( "======================" );
        }

        UserAccount user = fb.getUser();

        System.out.println( user.getAllFOAFAccountname_as().firstValue() );
        System.out.println( user.getAllFOAFName_as().firstValue() );


    }
}
