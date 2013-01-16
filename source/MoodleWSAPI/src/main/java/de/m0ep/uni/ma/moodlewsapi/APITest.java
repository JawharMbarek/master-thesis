package de.m0ep.uni.ma.moodlewsapi;

import java.util.Collection;

import de.m0ep.uni.ma.moodlewsapi.exception.RESTException;
import de.m0ep.uni.ma.moodlewsapi.model.CourseRecord;
import de.m0ep.uni.ma.moodlewsapi.model.UserRecord;

public class APITest {

    /**
     * @param args
     */
    public static void main( String[] args ) {
        MoodleWS ws = new MoodleWS(
                "http://localhost/moodle/wspp/service_pp2.php" );

        try {
            ws.login( "Admin", "Nterdem1.mufl.net" );
            int id = ws.getMyId();
            UserRecord user = ws.getUserById( id );
            System.out.println( user );

            Collection<CourseRecord> cources = ws.getMyCourses( id );

            for ( CourseRecord course : cources ) {
                System.out.println( course );
            }
            ws.logout();

        } catch ( RESTException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
