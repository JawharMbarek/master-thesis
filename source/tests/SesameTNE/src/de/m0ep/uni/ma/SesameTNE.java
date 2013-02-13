package de.m0ep.uni.ma;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.openrdf.repository.object.ObjectConnection;
import org.openrdf.repository.object.ObjectRepository;
import org.openrdf.repository.object.config.ObjectRepositoryConfig;
import org.openrdf.repository.object.config.ObjectRepositoryFactory;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.memory.MemoryStore;

import de.m0ep.rdf.sioc.Post;

public class SesameTNE {

    /**
     * @param args
     * @throws RepositoryException
     * @throws RepositoryConfigException
     */
    public static void main( String[] args ) throws RepositoryConfigException,
            RepositoryException {
        ObjectRepositoryConfig config = new ObjectRepositoryConfig();
        ObjectRepositoryFactory factory = new ObjectRepositoryFactory();

        ObjectRepository repo = factory.createRepository( config,
                new SailRepository( new MemoryStore() ) );

        repo.initialize();
        ObjectConnection con = repo.getConnection();

        Object obj = con.getObjectFactory().createObject(
                "http://m0ep.de/rdf/test" );

        Post post = con.addDesignation( obj, Post.class );
        post.setDcDate( new HashSet<String>( Arrays.asList( new Date()
                .toString() ) ) );

        for ( Object s : post.getDcDate() ) {
            System.out.println( s );
        }
    }
}
