package de.m0ep.uni.ma.socc.connectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.patrickpollet.moodlews_gson.core.CourseRecord;
import net.patrickpollet.moodlews_gson.core.ForumRecord;
import net.patrickpollet.moodlews_gson.core.LoginReturn;
import net.patrickpollet.moodlews_gson.core.Mdl_restserverBindingStub;
import net.patrickpollet.moodlews_gson.core.UserRecord;
import de.m0ep.uni.ma.rdf.sioc.Forum;
import de.m0ep.uni.ma.rdf.sioc.UserAccount;
import de.m0ep.uni.ma.socc.SIOCModel;

public class MoodleConnector implements Connector {

    public static final String        NAME = "Moodle";
    public static final String        ID   = "moodle";
    public static final String        URL  = "http://moodle.org/";

    private Mdl_restserverBindingStub moodle;
    private int                       myId = -1;
    private LoginReturn               login;

    private SIOCModel                 model;

    public void init( SIOCModel model, Properties config ) {
        this.model = model;

        moodle = new Mdl_restserverBindingStub(
                config.getProperty( "moodle.url" ), "json", false );
        
        login = moodle.login( config.getProperty( "moodle.username" ),
                config.getProperty( "moodle.password" ) );

        myId = moodle.get_my_id( login.getClient(), login.getSessionkey() );
    }

    public void destroy() {
        moodle.logout( login.getClient(), login.getSessionkey() );
    }

    public String getId() {
        return ID;
    }

    public String getUserFriendlyName() {
        return NAME;
    }

    public List<Forum> getForums() {
        List<Forum> result = new ArrayList<Forum>();
        ForumRecord[] forumRecords = moodle.get_all_forums( login.getClient(),
                login.getSessionkey(), "", "" );

        Map<Integer, CourseRecord> courses = new HashMap<Integer, CourseRecord>();

        for ( ForumRecord forumRecord : forumRecords ) {
            Forum forum = model.createForum( URL + forumRecord.getId() );
            forum.setSIOCId( Integer.toString( forumRecord.getId() ) );

            CourseRecord course = courses.get( forumRecord.getCourse() );
            if( null == course ) {
                CourseRecord[] courseRecords = moodle.get_course_byid(
                    login.getClient(), login.getSessionkey(),
                    Integer.toString( forumRecord.getCourse() ) );

                course = courseRecords[0];
                courses.put( course.getId(), course );
            }

            forum.setSIOCName( course.getFullname() + "/"
                    + forumRecord.getName() );

            result.add( forum );
        }

        return result;
    }

    public UserAccount getUser() {
        UserRecord[] userRecords = moodle.get_user_byid( login.getClient(), login.getSessionkey(), myId );
        
        if(null != userRecords && 0 < userRecords.length){
            UserRecord user = userRecords[0];
            UserAccount result = model.createUserAccount( URL + user.getId() );

            result.setSIOCId( Integer.toString( user.getId() ) );
            result.setFOAFName( user.getFirstname() + " " + user.getLastname() );
            result.setFOAFAccountname( user.getUsername() );
            
            return result;
        }
        else{
            throw new RuntimeException("failed to get userinfos");
        }
    }
}
