package de.m0ep.canvaslms;

import de.m0ep.canvaslms.model.CourseInfo;
import de.m0ep.canvaslms.model.DiscussionTopicInfo;

public class Course {
    private final CanvasLMSClient client;

    private final CourseInfo info;

    public Course(final CanvasLMSClient client, final CourseInfo info) {
	this.client = client;
	this.info = info;
    }

    public CanvasLMSClient getClient() {
	return client;
    }

    public CourseInfo getInfo() {
	return info;
    }

    public Pagination<DiscussionTopicInfo> listDiscussionTopics() {
	return null;
    }
}
