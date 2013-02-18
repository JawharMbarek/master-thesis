package de.m0ep.uni.ma;

import java.io.IOException;
import java.util.Properties;

import net.patrickpollet.moodlews_gson.core.ForumPostDatum;
import net.patrickpollet.moodlews_gson.core.LoginReturn;
import net.patrickpollet.moodlews_gson.core.Mdl_restserverBindingStub;

public class MoodleWASTest {
    public static void main( String[] args ) throws IOException {
        Properties config = new Properties();
        config.load( ClassLoader.getSystemResourceAsStream( "user.properties" ) );

	// Mdl_soapserverBindingStub moodle = new Mdl_soapserverBindingStub(
	// config.getProperty("moodle.url") + "wspp/service_pp2.php",
	// "CFGWWWROOT/wspp/wsdl2", true);
	//
	// LoginReturn login =
	// moodle.login(config.getProperty("moodle.username"),
	// config.getProperty("moodle.password"));
	//
	// // ForumDiscussionDatum discussionDatum = new ForumDiscussionDatum(
	// // moodle.getNAMESPACE());
	// // discussionDatum.setMessage("Hallo, Welt!");
	// // discussionDatum.setSubject("Test titel");
	// // moodle.forum_add_discussion(login.getClient(),
	// login.getSessionkey(),
	// // 1, discussionDatum);
	//
	// ForumPostDatum post = new ForumPostDatum(moodle.getNAMESPACE());
	// post.setMessage("Eclipse here");
	// post.setSubject("Post from eclipse");
	// moodle.forum_add_reply(login.getClient(), login.getSessionkey(), 5,
	// post);
	//
	// moodle.logout(login.getClient(), login.getSessionkey());
	//
	// // WSDL2ksoap
	// // .main(new String[] {
	// // "http://localhost/florian/moodle24/wspp/moodlewsdl2.xml" });

	Mdl_restserverBindingStub moodle = new Mdl_restserverBindingStub(
		config.getProperty("moodle.url") + "wspp/service_pp2.php",
		"dump", true);
	
	LoginReturn login = moodle.login(config.getProperty("moodle.username"),
		config.getProperty("moodle.password"));

	ForumPostDatum post = new ForumPostDatum();
	post.setMessage("Eclipse here");
	post.setSubject("Post from eclipse");
	moodle.forum_add_reply(login.getClient(), login.getSessionkey(), 5,
		post);

    }
}
