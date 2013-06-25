package de.m0ep.canvaslms.test.gson;

import java.util.List;

import de.m0ep.canvas.Canvas;
import de.m0ep.canvas.Pagination;
import de.m0ep.canvas.exceptions.CanvasException;
import de.m0ep.canvas.model.Course;
import de.m0ep.canvas.model.DiscussionTopicEntry;
import de.m0ep.canvas.model.DiscussionTopicInfo;
import de.m0ep.canvas.model.UserProfile;

public class CanvasTest {
    public static void main(String[] args) {
	Canvas client = new Canvas(
		"https://canvas.instructure.com",
		"7~w8C7LIFV0hVr94zD2watmgXWGv2uCG4TKcTWXRF4KoAgkpTavEDUdlZPExRRMvIO",
		81259);

	Pagination<Course> coursePages;
	try {
	    coursePages = client.listCourses();
	} catch (CanvasException e) {
	    e.printStackTrace();
	    return;
	}

	for (List<Course> courses : coursePages) {
	    for (Course course : courses) {
		System.out.println("id:          " + course.getId());
		System.out.println("account:     " + course.getAccountId());
		System.out.println("name:        " + course.getName());
		System.out.println("start_at:    " + course.getStartAt());
		System.out.println("Course code: " + course.getCourseCode());
		System.out.println("Account id:  " + course.getAccountId());
		System.out.println("----------------------------------------");
	    }
	}

	Pagination<DiscussionTopicInfo> discussionPages;
	try {
	    discussionPages = client
		    .listCourseDiscussionTopics(798152);
	} catch (CanvasException e) {
	    e.printStackTrace();
	    return;
	}

	System.out.println();
	System.out.println();

	int page = 0;
	for (List<DiscussionTopicInfo> discussions : discussionPages) {
	    System.out.println("Page " + (++page));
	    System.out.println("====================");
	    for (DiscussionTopicInfo discussion : discussions) {
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
		System.out.println("--------------------");
	    }
	}

	Pagination<DiscussionTopicEntry> entryPages;
	try {
	    entryPages = client.listDiscussionTopicEntries(798152, 1424406);
	} catch (CanvasException e) {
	    e.printStackTrace();
	    return;
	}

	System.out.println();
	System.out.println();

	for (List<DiscussionTopicEntry> entries : entryPages) {
	    for (DiscussionTopicEntry entry : entries) {
		System.out.println(entry.getId());
		System.out.println(entry.getUserName());
		System.out.println(entry.getCreatedAt());
		System.out.println(entry.getMessage());
		System.out.println(entry.hasMoreReplies());
	    }
	}

	// System.out.println();
	// System.out.println();
	//
	// try {
	// DiscussionTopicEntry entry = client.postDiscussionEntry(
	// "Automatic post @ " + new Date(),
	// 798152,
	// 1424406);
	// System.out.println(entry.getId());
	// System.out.println(entry.getUserName());
	// System.out.println(entry.getCreatedAt());
	// System.out.println(entry.getMessage());
	// System.out.println(entry.hasMoreReplies());
	// } catch (CanvasLMSException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// System.out.println();
	// System.out.println();
	//
	// try {
	// DiscussionTopicEntry entry = client.postReply(
	// "Automatic reply @ " + new Date(),
	// 798152,
	// 1424406,
	// 3084155);
	// System.out.println(entry.getId());
	// System.out.println(entry.getUserName());
	// System.out.println(entry.getCreatedAt());
	// System.out.println(entry.getMessage());
	// System.out.println(entry.hasMoreReplies());
	// } catch (CanvasLMSException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }

	System.out.println();
	System.out.println();

	try {
	    UserProfile flo = client.getUserProfile(3106185);
	    System.out.println("id:     " + flo.getId());
	    System.out.println("name:   " + flo.getName());
	    System.out.println("email:  " + flo.getPrimaryEmail());

	    System.out.println();

	    UserProfile kai = client.getUserProfile(3084157);
	    System.out.println("id:     " + kai.getId());
	    System.out.println("name:   " + kai.getName());
	    System.out.println("email:  " + kai.getPrimaryEmail());
	} catch (CanvasException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
