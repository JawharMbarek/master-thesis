package de.m0ep.uni.ma.rdf.rdfreactor;

import org.ontoware.rdfreactor.generator.CodeGenerator;

public class GenSIOCApi {

    /**
     * @param args
     * @throws Exception
     */
    public static void main( String[] args ) throws Exception {
        CodeGenerator.generate( "src/main/resources/ontologies/sioc.rdf",
                "./gen", "de.m0ep.uni.ma.rdf.sioc", "rdfs", true, true, "SIOC" );

        CodeGenerator.generate( "src/main/resources/ontologies/foaf.rdf",
                "./gen", "de.m0ep.uni.ma.rdf.foaf", "rdfs", true,true,"FOAF" );
        
    }

}
