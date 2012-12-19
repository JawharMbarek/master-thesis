package de.m0ep.rdf.sioc;

/**
 * Interface Post sioc:Post - An article or message that can be posted to a
 * Forum.
 */
public interface Post extends Item {

    /**
     * sioc:related_to - Related Posts for this Post, perhaps determined
     * implicitly from topics or references.
     * 
     * @return de.m0ep.rdf.sioc.Post
     */
    public Post getRelatedTo();

    /**
     * sioc:related_to - Related Posts for this Post, perhaps determined
     * implicitly from topics or references.
     * 
     * @param post
     */
    public void setRelatedTo( Post post );

}
