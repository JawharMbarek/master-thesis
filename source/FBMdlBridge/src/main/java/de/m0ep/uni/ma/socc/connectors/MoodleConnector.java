package de.m0ep.uni.ma.socc.connectors;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.patrickpollet.moodlews_gson.core.CourseRecord;
import net.patrickpollet.moodlews_gson.core.ForumDiscussionRecord;
import net.patrickpollet.moodlews_gson.core.ForumPostDatum;
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

    private int                       limit;
    private String                    url;

    public void init( SIOCModel model, Properties config ) {
        this.model = model;
        this.url = config.getProperty( "moodle.url" );

        this.moodle = new Mdl_restserverBindingStub(
                config.getProperty( "moodle.url" ) + "wspp/service_pp2.php",
                "json", true );
        
        this.login = moodle.login( config.getProperty( "moodle.username" ),
                config.getProperty( "moodle.password" ) );

        this.myId = moodle.get_my_id( login.getClient(), login.getSessionkey() );
        this.limit = Integer
                .parseInt( config.getProperty( "post_limit", "25" ) );
    }

    public void destroy() {
        moodle.logout( login.getClient(), login.getSessionkey() );
    }

    public String getURL() {
        return url;
    }

    public String getUserFriendlyName() {
        return NAME;
    }

    public SIOCModel getModel() {
        return model;
    }

    public List<Forum> getForums() {
        List<Forum> result = new ArrayList<Forum>();
        ForumRecord[] forumRecords = moodle.get_all_forums( login.getClient(),
                login.getSessionkey(), "", "" );

        Map<Integer, CourseRecord> courses = new HashMap<Integer, CourseRecord>();

        for ( ForumRecord forumRecord : forumRecords ) {
            Forum forum = model.createForum( getURL() + "forum/"
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

            Thread thread = model.createThread( getURL() + "thread/"
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
        Post post = model.createPost( getURL() + "post/" + postRecord.getId() );

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

        result.add( post );

        for ( ForumPostRecord child : postRecord.getChildren() ) {
            addPost( result, child, discussion, post );
        }
    }

    public boolean canPostOn( Container container ) {

        return container.getResource().toString()
                .startsWith( getURL() + "thread/" );
    }

    public void publishPost( Post post, Container container ) {
        Preconditions.checkArgument( post.hasSIOCContent() );
        Preconditions.checkArgument( canPostOn( container ) );

        ForumPostDatum datum = new ForumPostDatum();
        datum.setMessage( post.getAllSIOCContent_as().firstValue() );
        if( post.hasDCTermsTitle() ) {
            datum.setSubject( post.getAllDCTermsTitle_as().firstValue() );
        } else if( post.hasDCTermsSubject() ) {
            datum.setSubject( post.getAllDCTermsSubject_as().firstValue() );
        } else {
            datum.setSubject( "" );
        }

        // get first post of the discussion to post a reply
        ForumPostRecord[] posts = moodle
                .get_forum_posts( login.getClient(), login.getSessionkey(),
                Integer.parseInt( container.getAllSIOCId_as().firstValue() ), 1 );

        if( 0 != posts.length )
            moodle.forum_add_reply( login.getClient(), login.getSessionkey(),
                posts[0].getId(), datum );
    }

    public void commentPost( Post post, Post parent ) {
        Preconditions.checkArgument( post.hasSIOCContent() );

        ForumPostDatum datum = new ForumPostDatum();
        datum.setMessage( post.getAllSIOCContent_as().firstValue() );
        if( post.hasDCTermsTitle() ) {
            datum.setSubject( post.getAllDCTermsTitle_as().firstValue() );
        } else if( post.hasDCTermsSubject() ) {
            datum.setSubject( post.getAllDCTermsSubject_as().firstValue() );
        } else {
            datum.setSubject( "" );
        }

        moodle.forum_add_reply( login.getClient(), login.getSessionkey(),
                Integer.parseInt( parent.getAllSIOCId_as().firstValue() ),
                datum );
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
