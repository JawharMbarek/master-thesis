
package de.m0ep.socc.core.connector;

import java.io.IOException;
import java.util.List;

import org.rdfs.sioc.Container;
import org.rdfs.sioc.Forum;
import org.rdfs.sioc.Thread;

import de.m0ep.socc.core.exceptions.NotFoundException;

/**
 * An interface to read some structural information about a service of an online
 * community.
 * 
 * @author Florian Müller
 */
public interface IServiceStructureReader {

    /**
     * Returns a {@link Forum} by its <code>id</code>.
     * 
     * @param id
     * @throws NullPointerException
     *             Thrown if <code>id</code> is <code>null</code>.
     * @throws IllegalArgumentException
     *             Thrown if <code>id</code> is empty.
     * @throws IOException
     *             Thrown if there is a problem with the connection to the
     *             service endpoint.
     * @throws NotFoundException
     *             Thrown if there is no {@link Forum} with this <code>id</code>
     *             .
     */
    public Forum getForum(String id);

    /**
     * Returns a {@link List} with all {@link Forum}s of this service.
     */
    public List<Forum> listForums();

    /**
     * Returns a {@link Thread} by its <code>id</code>.
     * 
     * @param id
     * @throws NullPointerException
     *             Thrown if <code>id</code> is <code>null</code>.
     * @throws IllegalArgumentException
     *             Thrown if <code>id</code> is empty.
     * @throws IOException
     *             Thrown if there is a problem with the connection to the
     *             service endpoint.
     * @throws NotFoundException
     *             Thrown if there is no {@link Thread} with this
     *             <code>id</code> .
     */
    public Thread getThread(String id);

    /**
     * Returns a {@link List} with all {@link Thread}s inside the
     * <code>parent</code> {@link Container}.
     * 
     * @param parent
     */
    public List<Thread> listThreads(Container parent);
}
