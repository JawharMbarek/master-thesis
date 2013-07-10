
package de.m0ep.canvaslms.test.gson;

import java.util.List;
import java.util.Scanner;

import de.m0ep.canvas.CanvasLmsClient;
import de.m0ep.canvas.Pagination;
import de.m0ep.canvas.exceptions.CanvasLmsException;
import de.m0ep.canvas.model.Course;
import de.m0ep.canvas.model.DiscussionTopic;
import de.m0ep.canvas.model.Entry;

public class CanvasTest {
    public static void main(String[] args) {
        CanvasLmsClient client = new CanvasLmsClient(
                "https://canvas.instructure.com",
                /* "7~w8C7LIFV0hVr94zD2watmgXWGv2uCG4TKcTWXRF4KoAgkpTavEDUdlZPExRRMvIO" */
                "7~LUpV7B3lJYadvZ2sHlpJiTcyJ6HaduVb3Ho8YjBNXSdIE4AEFzLFfORcOHRHh1fU"
                /* ,81259 */);

        System.out.println();
        System.out.println("Get courses");
        System.out.println("-----------------");

        Pagination<Course> coursePages;
        try {
            coursePages = client.courses()
                    .list().executePagination();
        } catch (CanvasLmsException e1) {
            e1.printStackTrace();
            return;
        }

        for (List<Course> courses : coursePages) {
            for (Course course : courses) {
                System.out.println(course);
            }
        }

        System.out.println();
        System.out.println("Get course id=798152");
        System.out.println("-----------------");

        try {
            Course course = client.courses().get(798152).execute();
            System.out.println(course);
        } catch (CanvasLmsException e) {
            e.printStackTrace();
            return;
        }

        System.out.println();
        System.out.println("Get topics course=798152");
        System.out.println("-----------------");

        Pagination<DiscussionTopic> discussionPages;
        try {
            discussionPages = client.courses()
                    .discussionTopics(798152)
                    .list()
                    .executePagination();
        } catch (CanvasLmsException e) {
            e.printStackTrace();
            return;
        }

        for (List<DiscussionTopic> discussions : discussionPages) {
            for (DiscussionTopic discussion : discussions) {
                System.out.println(discussion);
            }
        }

        System.out.println();
        System.out.println("Get topic course=798152, id=1424406");
        System.out.println("-----------------");

        try {
            DiscussionTopic topic = client.courses()
                    .discussionTopics(798152)
                    .get(1424406)
                    .execute();
            System.out.println(topic);
        } catch (CanvasLmsException e) {
            e.printStackTrace();
            return;
        }

        System.out.println();
        System.out.println("Get entries course=798152, topic=1424406");
        System.out.println("-----------------");

        Pagination<Entry> entryPages;
        try {
            entryPages = client.courses()
                    .discussionTopics(798152)
                    .entries(1424406)
                    .list()
                    .executePagination();
        } catch (CanvasLmsException e) {
            e.printStackTrace();
            return;
        }

        for (List<Entry> entries : entryPages) {
            for (Entry entry : entries) {
                System.out.println(entry);
            }
        }

        System.out.println();
        System.out
                .println("Create entry in topic course=798152, id=1424406");
        System.out.println("-----------------");

        Scanner scn = new Scanner(System.in);
        try {
            System.out.print("Message:");
            String message = scn.nextLine();

            Entry entry = client.courses()
                    .discussionTopics(798152)
                    .entries(1424406)
                    .post(message)
                    .execute();

            System.out.println(entry);
        } catch (CanvasLmsException e) {
            e.printStackTrace();
            scn.close();
            return;
        }

        System.out.println();
        System.out
                .println("Create reply to entry course=798152, topic=1424406, id=3084155");
        System.out.println("-----------------");

        try {
            System.out.print("Message:");
            String message = scn.nextLine();

            Entry entry = client.courses()
                    .discussionTopics(798152)
                    .entries(1424406)
                    .postReply(message, 3084155)
                    .execute();

            System.out.println(entry);
        } catch (CanvasLmsException e) {
            e.printStackTrace();
            scn.close();
            return;
        }

        scn.close();
    }
}
