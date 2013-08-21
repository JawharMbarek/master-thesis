package de.m0ep.socc.camel;

import org.rdfs.sioc.Post;

public interface ISoccPostProducer extends ISoccProducer {
    public abstract Post getPost();

    public abstract void setPost( Post post );
}
