
package de.m0ep.rdf2go;

import de.m0ep.rdf2go.generator.controller.Rdf2JavaGeneratorController;
import de.m0ep.rdf2go.generator.model.GeneratorParameter;
import de.m0ep.rdf2go.generator.view.Rdf2JavaGeneratorView;

public class Rdf2JavaGenerator {
    public static void main( String[] args ) throws Exception {
        GeneratorParameter model = new GeneratorParameter();
        Rdf2JavaGeneratorView view = new Rdf2JavaGeneratorView(
                new Rdf2JavaGeneratorController(
                        model ) );
        view.setVisible( true );
    }
}
