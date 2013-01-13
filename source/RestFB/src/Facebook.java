import java.util.Date;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.types.Comment;
import com.restfb.types.Post;
import com.restfb.types.User;

public class Facebook {

    public static void main( String[] args ) {
        DefaultFacebookClient client = new DefaultFacebookClient(
                "AAACEdEose0cBAJ2thHEzchlpF6t9ViYK9rtaez9IIYAjcNruw"
                        + "O6atKlYdcFQchcs5LjqZAzAtkKh8kDCKLwNuVKOv6ZCO7G16Cs"
                        + "snf0wxy1Dd23RMh" );

        User user = client.fetchObject( "me", User.class );

        System.out.println( user.getName() );
        System.out.println( user.getHometownName() );
        System.out.println( user.getRelationshipStatus() );

        // Connection<StatusMessage> statuses = client.fetchConnection(
        // "statuses", StatusMessage.class );
        //
        // for ( StatusMessage status : statuses.getData() ) {
        // System.out.println( status.getId() );
        // System.out.println( status.getUpdatedTime() );
        // System.out.println( status.getMessage() );
        //
        // System.out.println( "Likes: " + status.getLikes().size() );
        //
        // for ( Comment cmt : status.getComments() ) {
        // System.out.println( "\t" + cmt.getId() );
        // System.out.println( "\t" + cmt.getCreatedTime() );
        // System.out.println( "\t" + cmt.getMessage() );
        // }
        // System.out.println( "-------------------------------------" );
        // }

        Date oneWeekAgo = new Date( System.currentTimeMillis() - 1000L * 60L
                * 60L * 24L * 7L );

        Connection<Post> filteredFeed = client.fetchConnection( "me/feed",
                Post.class );

        System.out.println( "Filtered feed count: "
                + filteredFeed.getData().size() );

        for ( Post post : filteredFeed.getData() ) {
            System.out.println( post.getId() );
            System.out.println( post.getName() );
            System.out.println( post.getFrom().getName() );
            System.out.println( post.getUpdatedTime() );
            System.out.println( post.getMessage() );
            System.out.println( post.getType() );
            System.out.println( post.getStatusType() );

            System.out.println( "Likes: " + post.getLikesCount() );

            for ( Comment cmt : post.getComments().getData() ) {
                System.out.println( "\t" + cmt.getId() );
                System.out.println( "\t" + cmt.getCreatedTime() );
                System.out.println( "\t" + cmt.getMessage() );
            }
            System.out.println( "-------------------------------------" );
        }

    }
}
