package com.xmlns.foaf;

import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.purl.dc.terms.DCTermsVocabulary;

public class FOAF {
    public static Model createDefaultMemoryModel() {
	Model result = RDF2Go.getModelFactory().createModel();
	result.open();
	result.setNamespace("foaf", FOAFVocabulary.NS_FOAF.toString());
	result.setNamespace("dcterms", DCTermsVocabulary.NS_DCTerms.toString());

	return result;
    }
}
