package de.m0ep.canvaslms.test.gson;

import java.util.List;
import java.util.Scanner;

import de.m0ep.canvas.Canvas;
import de.m0ep.canvas.Pagination;
import de.m0ep.canvas.exceptions.CanvasException;
import de.m0ep.canvas.model.Course;
import de.m0ep.canvas.model.DiscussionTopic;
import de.m0ep.canvas.model.DiscussionTopicEntry;

public class CanvasTest {
    public static void main(String[] args) {
	Canvas client = new Canvas(
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
	} catch (CanvasException e1) {
	    e1.printStackTrace();
	    return;
	}

	for (List<Course> courses : coursePages) {
	    for (Course course : courses) {
		printCourse(course);
	    }
	}

	System.out.println();
	System.out.println("Get course id=798152");
	System.out.println("-----------------");

	try {
	    Course course = client.courses().get(798152).execute();
	    printCourse(course);
	} catch (CanvasException e) {
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
	} catch (CanvasException e) {
	    e.printStackTrace();
	    return;
	}

	for (List<DiscussionTopic> discussions : discussionPages) {
	    for (DiscussionTopic discussion : discussions) {
		printDiscussionTopic(discussion);
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
	    printDiscussionTopic(topic);
	} catch (CanvasException e) {
	    e.printStackTrace();
	    return;
	}

	System.out.println();
	System.out.println("Get entries course=798152, topic=1424406");
	System.out.println("-----------------");

	Pagination<DiscussionTopicEntry> entryPages;
	try {
	    entryPages = client.courses()
		    .discussionTopics(798152)
		    .entries(1424406)
		    .list()
		    .executePagination();
	} catch (CanvasException e) {
	    e.printStackTrace();
	    return;
	}

	for (List<DiscussionTopicEntry> entries : entryPages) {
	    for (DiscussionTopicEntry entry : entries) {
		printDiscussionTopicEntry(entry);
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

	    DiscussionTopicEntry entry = client.courses()
		    .discussionTopics(798152)
		    .entries(1424406)
		    .post(message)
		    .execute();

	    printDiscussionTopicEntry(entry);
	} catch (CanvasException e) {
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

	    DiscussionTopicEntry entry = client.courses()
		    .discussionTopics(798152)
		    .entries(1424406)
		    .replies(3084155)
		    .post(message)
		    .execute();

	    printDiscussionTopicEntry(entry);
	} catch (CanvasException e) {
	    e.printStackTrace();
	    scn.close();
	    return;
	}

	scn.close();
    }

    /**
     * @param course
     */
    public static void printCourse(Course course) {
	System.out.println("id:          " + course.getId());
	System.out.println("account:     " + course.getAccountId());
	System.out.println("name:        " + course.getName());
	System.out.println("start_at:    " + course.getStartAt());
	System.out.println("Course code: " + course.getCourseCode());
	System.out.println("Account id:  " + course.getAccountId());
	System.out.println("=================");
    }

    /**
     * @param discussion
     */
    public static void printDiscussionTopic(DiscussionTopic discussion) {
	System.out.println(
		"id:        " + discussion.getId());
	System.out.println(
		"titel:     " + discussion.getTitle());
	System.out.println(
		"message:   " + discussion.getMessage());
	System.out
		.println(
		"author:    " + discussion.getAuthor().getDisplayName());
	System.out.println(
		"author id: " + discussion.getAuthor().getId());
	System.out.println("=================");
    }

    /**
     * @param entry
     */
    public static void printDiscussionTopicEntry(DiscussionTopicEntry entry) {
	System.out.println(entry.getId());
	System.out.println(entry.getUserName());
	System.out.println(entry.getCreatedAt());
	System.out.println(entry.getMessage());
	System.out.println(entry.hasMoreReplies());
	System.out.println("=================");
    }

}
