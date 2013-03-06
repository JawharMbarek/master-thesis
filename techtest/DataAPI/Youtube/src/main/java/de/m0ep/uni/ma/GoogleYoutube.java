package de.m0ep.uni.ma;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.Person;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.youtube.CommentEntry;
import com.google.gdata.data.youtube.CommentFeed;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ServiceException;

public class GoogleYoutube {
    public static void main( String[] args ) throws MalformedURLException,
            IOException, ServiceException {
        YouTubeService service = new YouTubeService(
                "m0ep:ma:youtube",
                "AI39si48dEjhAE9RrY6w1HnlmyrUUTDt-xssOKkEEcpOIMD1gFcQ-0Xv40YNl-H1MxFzGzbHih4ootWo1cRrPH9gV-5UdazEbQ" );

        service.setUserCredentials( "email.mufl", "email.mufl.net" );

        VideoFeed feed = service.getFeed( new URL(
                "http://gdata.youtube.com/feeds/api/users/m0eper/uploads" ),
                VideoFeed.class );

        System.out.println( feed.getEntries().size() );

        for ( VideoEntry entry : feed.getEntries() ) {
            System.out.println( "----------" );
            System.out.println( "id: " + entry.getId() );
            System.out.println( "title: " + entry.getTitle().getPlainText() );
            System.out.println( "Authors:" );
            for ( Person p : entry.getAuthors() ) {
                System.out.println( "\t" + p.getName() );
            }

            String cmtHref = entry.getComments().getFeedLink().getHref();

            CommentFeed cmtFeed = service.getFeed( new URL( cmtHref ),
                    CommentFeed.class );

            System.out.println( "Comments:" );
            for ( CommentEntry cmt : cmtFeed.getEntries() ) {
                System.out.println( "\t" + cmt.getId() );
                System.out.println( "\t" + cmt.getPlainTextContent() );
                System.out.println( "\t" + cmt.getAuthors().get( 0 ).getName() );
                System.out.println( "\t----------" );
            }

            CommentEntry cmt = new CommentEntry();
            cmt.setContent( new PlainTextConstruct( "Test comment "
                    + new Date() ) );
            service.insert( new URL( cmtHref ), cmt );
        }
    }
}
