package de.m0ep;

import org.openrdf.rdf2go.RepositoryModel;
import org.openrdf.repository.http.HTTPRepository;

import de.m0ep.ui.ServiceTestApp;

public class SesameTest {

    /**
     * @param args
     */
    public static void main(String[] args) {

	HTTPRepository repository = new HTTPRepository(
		"http://localhost:8080/openrdf-sesame/",
		"master-thesis");

	RepositoryModel model = new RepositoryModel(repository);
	model.open();

	ServiceTestApp app = new ServiceTestApp(model);
	app.getFrame().setVisible(true);
    }

}
