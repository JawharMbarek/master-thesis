package de.m0ep.uni.ma.socc.connectors;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.patrickpollet.moodlews_gson.core.CourseRecord;
import net.patrickpollet.moodlews_gson.core.ForumDiscussionRecord;
import net.patrickpollet.moodlews_gson.core.ForumPostRecord;
import net.patrickpollet.moodlews_gson.core.ForumRecord;
import net.patrickpollet.moodlews_gson.core.LoginReturn;
import net.patrickpollet.moodlews_gson.core.Mdl_restserverBindingStub;
import net.patrickpollet.moodlews_gson.core.UserRecord;

import org.ontoware.rdf2go.util.RDFTool;

import com.google.common.base.Preconditions;

import de.m0ep.uni.ma.rdf.sioc.Container;
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

    int                               limit = 25;

    public void init( SIOCModel model, Properties config ) {
        this.model = model;

        moodle = new Mdl_restserverBindingStub(
                config.getProperty( "moodle.url" ) + "wspp/service_pp2.php",
                "json", false );
        
        login = moodle.login( config.getProperty( "moodle.username" ),
                config.getProperty( "moodle.password" ) );

        myId = moodle.get_my_id( login.getClient(), login.getSessionkey() );
        limit = Integer.parseInt( config.getProperty( "post_limit", "25" ) );
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
            Forum forum = model.createForum( URL + "forum/"
                    + forumRecord.getId() );
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
        int forumId = Integer.parseInt( forum.getAllSIOCId_as().firstValue() );
        
        ForumDiscussionRecord[] discussionRecords = moodle
                .get_forum_discussions( login.getClient(),
                        login.getSessionkey(),
 forumId, 25 );
        
        for ( ForumDiscussionRecord record : discussionRecords ) {
            if( null != record.getError() && !record.getError().isEmpty() ) {
                threads.clear();
                return threads;
            }

            Thread thread = model.createThread( URL + "thread/"
 + record.getId() );

            thread.setSIOCId( Integer.toString( record.getId() ) );
            thread.setSIOCName( record.getName() );
            thread.setSIOCLastitemdate( RDFTool.dateTime2String( new Date(
                    (long) record.getTimemodified() * 1000 ) ) );
            thread.setSIOCParent( forum );

            threads.add( thread );
        }

        return threads;
    }

    public List<Post> getPost( Container container ) {
        Preconditions.checkArgument( canPostOn( container ) );
        System.out.println( "getPost" );
        
        List<Post> result = new ArrayList<Post>();
        ForumPostRecord[] postRecords = moodle.get_forum_posts(
                login.getClient(), login.getSessionkey(),
                Integer.parseInt( container.getAllSIOCId_as().firstValue() ),
                limit );
        
        for ( ForumPostRecord postRecord : postRecords ) {
            addPost( result, postRecord, container, null );
        }
        
        return result;
    }

    public void addPost( List<Post> result, ForumPostRecord postRecord,
            Container discussion, Post parent ) {
        Post post = model.createPost( URL + "post/" + postRecord.getId() );

        System.out.println( postRecord );
        if( null != parent )
            post.setSIOCReplyof( parent );
        post.setSIOCContainer( discussion );
        post.setSIOCId( Integer.toString( postRecord.getId() ) );
        post.setDCTermsSubject( postRecord.getSubject() );
        post.setSIOCContent( postRecord.getMessage() );
        post.setDCTermsCreated( RDFTool.dateTime2String( new Date(
                (long) postRecord.getCreated() * 1000 ) ) );
        post.setDCTermsModified( RDFTool.dateTime2String( new Date(
                (long) postRecord.getModified() * 1000 ) ) );
        // if( null != postRecord.getAttachment()
        // && !postRecord.getAttachment().isEmpty() ) {
        // // FileRecord attachment = moodle.get_resourcefile_byid(
        // // login.getClient(),
        // // login.getSessionkey(),
        // // Integer.parseInt( postRecord.getAttachment() ) );
        // // post.setSIOCAttachment( ( (DefaultSIOCModel) model )
        // // .getDelegatingModel()
        // // .createURI( attachment.getFileurl() ) );
        // System.out.println( postRecord.getAttachment() );
        // }

        result.add( post );

        for ( ForumPostRecord child : postRecord.getChildren() ) {
            addPost( result, child, discussion, post );
        }
    }

    public boolean canPostOn( Container container ) {

        return container.getResource().toString()
                .startsWith( URL + "thread/" );
    }

    public void publishPost( Container container ) {
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
