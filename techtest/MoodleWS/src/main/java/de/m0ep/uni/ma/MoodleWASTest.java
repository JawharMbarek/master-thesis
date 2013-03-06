package de.m0ep.uni.ma;

import java.io.IOException;
import java.util.Properties;

import de.m0ep.moodlews.soap.ForumDiscussionDatum;
import de.m0ep.moodlews.soap.ForumPostDatum;
import de.m0ep.moodlews.soap.LoginReturn;
import de.m0ep.moodlews.soap.Mdl_soapserverBindingStub;


public class MoodleWASTest {
    public static void main( String[] args ) throws IOException {
        Properties config = new Properties();
        config.load( ClassLoader.getSystemResourceAsStream( "user.properties" ) );

	Mdl_soapserverBindingStub moodle = new Mdl_soapserverBindingStub(
		config.getProperty("moodle.url") + "wspp/service_pp2.php",
		config.getProperty("moodle.url") + "/wspp/wsdl2", true);
	
	LoginReturn login = moodle.login(config.getProperty("moodle.username"),
		config.getProperty("moodle.password"));
	int client = login.getClient();
	String sesskey = login.getSessionkey();
	

	ForumDiscussionDatum discussionDatum = new ForumDiscussionDatum(
		moodle.getNAMESPACE());
	discussionDatum.setMessage("Hallo, Welt!");
	discussionDatum.setSubject("Test titel");
	moodle.forum_add_discussion(client, sesskey, 1, discussionDatum);

	ForumPostDatum post = new ForumPostDatum(moodle.getNAMESPACE());
	post.setMessage("Ich zeig Kai mal was");
	post.setSubject("testpost");
	moodle.forum_add_reply(client, sesskey, 5, post);

	moodle.get_all_forums(client, sesskey, "", "");
	
	moodle.get_forum_discussions(client, sesskey, 1, 99);
	
	moodle.get_forum_posts(client, sesskey, 2, 99);

	moodle.logout(login.getClient(), login.getSessionkey());
	
        
//	WSDL2ksoap
//		.main(new String[] { "http://localhost/florian/moodle24/wspp/wsdl_pp2.php" });

//	Mdl_restserverBindingStub moodle = new Mdl_restserverBindingStub(
//		config.getProperty("moodle.url") + "wspp/service_pp2.php",
//		"json", true);
//	
//	LoginReturn login = moodle.login(config.getProperty("moodle.username"),
//		config.getProperty("moodle.password"));
//
//	ForumPostDatum post = new ForumPostDatum();
//	post.setMessage("Eclipse here");
//	post.setSubject("Post from eclipse");
//	moodle.forum_add_reply(login.getClient(), login.getSessionkey(), 5,
//		post);
//
//	ForumDiscussionDatum discussion = new ForumDiscussionDatum();
//	discussion.setMessage("test discussion");
//	discussion.setSubject("useless subject");
//	moodle.forum_add_discussion(login.getClient(), login.getSessionkey(),
//		1, discussion);
//
//	moodle.logout(login.getClient(), login.getSessionkey());

    }
}
