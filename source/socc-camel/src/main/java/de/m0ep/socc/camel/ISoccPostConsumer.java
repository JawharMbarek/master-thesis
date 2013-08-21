package de.m0ep.socc.camel;

import org.rdfs.sioc.Post;

public interface ISoccPostConsumer extends ISoccConsumer {
    public abstract Post getPost();

    public abstract void setPost( Post post );
}
