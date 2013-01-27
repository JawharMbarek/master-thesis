package de.m0ep.uni.ma.socc.connectors;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.patrickpollet.moodlews_gson.core.CourseRecord;
import net.patrickpollet.moodlews_gson.core.ForumDiscussionRecord;
import net.patrickpollet.moodlews_gson.core.ForumRecord;
import net.patrickpollet.moodlews_gson.core.LoginReturn;
import net.patrickpollet.moodlews_gson.core.Mdl_restserverBindingStub;
import net.patrickpollet.moodlews_gson.core.UserRecord;

import org.ontoware.rdf2go.util.RDFTool;

import de.m0ep.uni.ma.rdf.sioc.Forum;
import de.m0ep.uni.ma.rdf.sioc.Post;
import de.m0ep.uni.ma.rdf.sioc.Thread;
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

    public List<Thread> getThreads( Forum forum ) {
        List<Thread> threads = new ArrayList<Thread>();
        int id = Integer.parseInt( forum.getAllSIOCId_as().firstValue() );
        
        ForumDiscussionRecord[] discussionRecords = moodle
                .get_forum_discussions( login.getClient(),
                        login.getSessionkey(),
                id, 25 );
        
        for ( ForumDiscussionRecord record : discussionRecords ) {
            if( null != record.getError() && !record.getError().isEmpty() ) {
                threads.clear();
                return threads;
            }

            Thread thread = model.createThread( URL + id + "/"
 + record.getId() );

            System.out.println( record );

            thread.setSIOCId( Integer.toString( record.getId() ) );
            thread.setSIOCName( record.getName() );
            thread.setSIOCLastitemdate( RDFTool.dateTime2String( new Date(
                    (long) record.getTimemodified() * 1000 ) ) );
            thread.setSIOCParent( forum );

            threads.add( thread );
        }

        return threads;
    }

    public boolean canPostOn( Forum forum ) {
        return false;
    }

    public boolean canPostOn( Thread thread ) {
        return true;
    }

    public void publishPost( Forum forum ) {
        // TODO Auto-generated method stub
    }

    public void publishPost( Thread thread ) {
        // TODO Auto-generated method stub
    }

    public void commentPost( Post parent ) {
        // TODO Auto-generated method stub
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
