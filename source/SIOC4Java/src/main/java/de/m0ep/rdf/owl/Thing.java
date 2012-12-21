package de.m0ep.rdf.owl;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Interface Thing
 */
public interface Thing {

    /**
     * @return String
     */
    public String getUri();

    /**
     * @return Resource
     */
    public Resource resource();

    /**
     * @return Model
     */
    public Model model();

    /*
     * 
     */
    public boolean isRDFType( Resource type );

}
