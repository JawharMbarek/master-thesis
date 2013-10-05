
package de.m0ep.test.socc.core.utils;

import org.junit.Assert;
import org.junit.Test;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.vocabulary.RDF;
import org.purl.dc.terms.DCTermsVocabulary;
import org.rdfs.sioc.Post;
import org.rdfs.sioc.SiocVocabulary;
import org.rdfs.sioc.Site;

import de.m0ep.socc.core.utils.RdfUtils;
import de.m0ep.socc.core.utils.SoccUtils;

public class SoccUtilsTests {

    @Test
    public void findSiblingTest() {
        Model model = RDF2Go.getModelFactory().createModel();
        model.open();

        model.setNamespace("sioc", SiocVocabulary.NS_SIOC.toString());
        model.setNamespace("rdf", RDF.RDF_NS);
        model.setNamespace("dct", DCTermsVocabulary.NS_DCTerms.toString());

        Site site1 = new Site(model, "http://www.example.org", true);
        Site site2 = new Site(model, "http://www.example.com", true);
        Post post1 = new Post(model, "http://www.example.org/post/1", true);
        post1.setIsPartOf(site1);
        Post post2 = new Post(model, "http://www.example.org/post/2", true);
        post2.setIsPartOf(site1);
        Post post3 = new Post(model, "http://www.example.com/post/1", true);
        post3.setIsPartOf(site2);
        Post post4 = new Post(model, "http://www.example.com/post/2", true);
        post4.setIsPartOf(site2);

        post1.setSibling(post3);
        post3.setSibling(post1);
        post2.setSibling(post4);
        post4.setSibling(post2);

        Post actual = SoccUtils.findSibling(model, post1, site2);
        Assert.assertEquals(post3, actual);

        actual = SoccUtils.findSibling(model, post4, site1);
        Assert.assertEquals(post2, actual);

        System.out.println(RdfUtils.modelToString(model, Syntax.Turtle));
        model.close();
    }
}
