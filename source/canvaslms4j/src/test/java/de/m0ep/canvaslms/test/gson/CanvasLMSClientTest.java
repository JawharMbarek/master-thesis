package de.m0ep.canvaslms.test.gson;

import java.util.Date;
import java.util.List;

import de.m0ep.canvaslms.CanvasLMSClient;
import de.m0ep.canvaslms.Pagination;
import de.m0ep.canvaslms.exceptions.CanvasLMSException;
import de.m0ep.canvaslms.model.CourseInfo;
import de.m0ep.canvaslms.model.DiscussionTopicEntry;
import de.m0ep.canvaslms.model.DiscussionTopicInfo;

public class CanvasLMSClientTest {
    public static void main(String[] args) {
	CanvasLMSClient client = new CanvasLMSClient(
		"https://canvas.instructure.com",
		"7~w8C7LIFV0hVr94zD2watmgXWGv2uCG4TKcTWXRF4KoAgkpTavEDUdlZPExRRMvIO");

	Pagination<CourseInfo> coursePages;
	try {
	    coursePages = client.listCourses();
	} catch (CanvasLMSException e) {
	    e.printStackTrace();
	    return;
	}

	for (List<CourseInfo> courses : coursePages) {
	    for (CourseInfo course : courses) {
		System.out.println("id:          " + course.getId());
		System.out.println("account:     " + course.getAccountId());
		System.out.println("name:        " + course.getName());
		System.out.println("start_at:    " + course.getStartAt());
		System.out.println("Course code: " + course.getCourseCode());
		System.out.println("----------------------------------------");
	    }
	}

	Pagination<DiscussionTopicInfo> discussionPages;
	try {
	    discussionPages = client
		    .listCourseDiscussionTopics(798152);
	} catch (CanvasLMSException e) {
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
	} catch (CanvasLMSException e) {
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

	System.out.println();
	System.out.println();

	try {
	    DiscussionTopicEntry entry = client.postDiscussionEntry(
		    "Automatic post @ " + new Date(),
		    798152,
		    1424406);
	    System.out.println(entry.getId());
	    System.out.println(entry.getUserName());
	    System.out.println(entry.getCreatedAt());
	    System.out.println(entry.getMessage());
	    System.out.println(entry.hasMoreReplies());
	} catch (CanvasLMSException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	System.out.println();
	System.out.println();

	try {
	    DiscussionTopicEntry entry = client.postReply(
		    "Automatic reply @ " + new Date(),
		    798152,
		    1424406,
		    3084155);
	    System.out.println(entry.getId());
	    System.out.println(entry.getUserName());
	    System.out.println(entry.getCreatedAt());
	    System.out.println(entry.getMessage());
	    System.out.println(entry.hasMoreReplies());
	} catch (CanvasLMSException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
