package de.m0ep.uni.ma.rdf.rdfreactor;

import org.ontoware.rdfreactor.generator.CodeGenerator;

public class GenSIOCApi {

    /**
     * @param args
     * @throws Exception
     */
    public static void main( String[] args ) throws Exception {
        CodeGenerator.generate( "src/main/resources/ontologies/sioc.rdf",
                "./gen/api", "de.m0ep.uni.ma.rdf.sioc", "rdfs", true, true,
                "SIOC" );

        CodeGenerator.generate( "src/main/resources/ontologies/sioc_types.rdf",
                "./gen/api", "de.m0ep.uni.ma.rdf.sioctypes", "rdfs", true,
                true,
 "SIOCTypes" );

        CodeGenerator.generate( "src/main/resources/ontologies/foaf.rdf",
                "./gen/api", "de.m0ep.uni.ma.rdf.foaf", "rdfs", true, true,
                "FOAF" );
        
    }

}
