package de.m0ep.canvaslms.test.gson;

import java.util.List;

import de.m0ep.canvaslms.CanvasLMSClient;
import de.m0ep.canvaslms.Pagination;
import de.m0ep.canvaslms.exceptions.CanvasLMSException;
import de.m0ep.canvaslms.model.DiscussionTopicResponse;

public class CanvasLMSClientTest {
    public static void main(String[] args) {
	CanvasLMSClient client = new CanvasLMSClient(
		"https://canvas.instructure.com",
		"7~w8C7LIFV0hVr94zD2watmgXWGv2uCG4TKcTWXRF4KoAgkpTavEDUdlZPExRRMvIO");

	Pagination<DiscussionTopicResponse> discussionPages;
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
	for (List<DiscussionTopicResponse> discussions : discussionPages) {
	    System.out.println("Page " + (++page));
	    System.out.println("====================");
	    for (DiscussionTopicResponse discussion : discussions) {
		System.out.println(discussion.getId());
		System.out.println(discussion.getTitle());
		System.out.println(discussion.getMessage());
		System.out.println(discussion.getAuthor().getDisplayName());
		System.out.println("--------------------");
	    }
	}
    }
}
