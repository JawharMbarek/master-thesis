package de.m0ep.canvaslms;

import de.m0ep.canvaslms.model.DiscussionTopicEntry;
import de.m0ep.canvaslms.model.DiscussionTopicInfo;

public class DiscussionTopic {
    private CanvasLMSClient client;
    private DiscussionTopicInfo info;

    public CanvasLMSClient getClient() {
	return client;
    }

    public DiscussionTopicInfo getInfo() {
	return info;
    }

    public Pagination<DiscussionTopicEntry> listEntries() {
	return null;
    }

    public Pagination<DiscussionTopicEntry> listReplies(final long id) {
	return null;
    }
}
