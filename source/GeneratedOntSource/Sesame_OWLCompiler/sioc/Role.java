package sioc;

import java.util.Set;

import org.openrdf.annotations.Iri;

/**
 * A Role is a function of a UserAccount within a scope of a particular Forum,
 * Site, etc.
 */
@Iri( "http://rdfs.org/sioc/ns#Role" )
public interface Role extends Resource {
    /** A UserAccount that has this Role. */
    @Iri( "http://rdfs.org/sioc/ns#function_of" )
    Set<Object> getSiocFunction_of();

    /** A UserAccount that has this Role. */
    @Iri( "http://rdfs.org/sioc/ns#function_of" )
    void setSiocFunction_of( Set<?> siocFunction_of );

    /** A resource that this Role applies to. */
    @Iri( "http://rdfs.org/sioc/ns#has_scope" )
    Set<Object> getSiocHas_scope();

    /** A resource that this Role applies to. */
    @Iri( "http://rdfs.org/sioc/ns#has_scope" )
    void setSiocHas_scope( Set<?> siocHas_scope );

}
