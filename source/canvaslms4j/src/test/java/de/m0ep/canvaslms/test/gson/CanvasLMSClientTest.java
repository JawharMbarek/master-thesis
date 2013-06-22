package de.m0ep.canvaslms.test.gson;

import de.m0ep.canvaslms.CanvasLMSClient;
import de.m0ep.canvaslms.exceptions.CanvasLMSException;
import de.m0ep.canvaslms.model.DiscussionTopicResponse;

public class CanvasLMSClientTest {
    public static void main(String[] args) {
	CanvasLMSClient client = new CanvasLMSClient(
		"https://canvas.instructure.com",
		"7~w8C7LIFV0hVr94zD2watmgXWGv2uCG4TKcTWXRF4KoAgkpTavEDUdlZPExRRMvIO");

	DiscussionTopicResponse[] discussions;
	try {
	    discussions = client
		    .listCourseDiscussionTopics(798152);
	} catch (CanvasLMSException e) {
	    e.printStackTrace();
	    return;
	}

	System.out.println();
	System.out.println();
	for (DiscussionTopicResponse discussion : discussions) {
	    System.out.println(discussion.getId());
	    System.out.println(discussion.getTitle());
	    System.out.println(discussion.getMessage());
	    System.out.println(discussion.getAuthor().getDisplayName());
	    System.out.println("--------------------");
	}
    }
}
