package de.m0ep.uni.ma.rdf.rdfreactor;

import org.ontoware.rdf2go.util.VocabularyWriter;

public class GenVocabular {

    /**
     * @param args
     * @throws Exception
     */
    public static void main( String[] args ) throws Exception {
        args = new String[]{
 "-i", "src/main/resources/ontologies/sioc.rdf",
                "-o", "gen/vocabularies", "--package",
                "de.m0ep.uni.ma.rdf.vocabularies", "-n", "SIOC", "-a",
                "http://rdfs.org/sioc/ns#", "-namespacestrict", "true"
        };

        VocabularyWriter.main( args );

        args = new String[] { "-i", "src/main/resources/ontologies/foaf.rdf",
                "-o", "gen/vocabularies", "--package",
                "de.m0ep.uni.ma.rdf.vocabularies", "-n", "FOAF", "-a",
                "http://xmlns.com/foaf/0.1/", "-namespacestrict", "true" };

        VocabularyWriter.main( args );

        args = new String[] { "-i",
                "src/main/resources/ontologies/dcterms.rdf", "-o",
                "gen/vocabularies", "--package",
                "de.m0ep.uni.ma.rdf.vocabularies", "-n", "DCTerms", "-a",
                "http://purl.org/dc/terms/", "-namespacestrict", "true" };

        VocabularyWriter.main( args );
    }

}
