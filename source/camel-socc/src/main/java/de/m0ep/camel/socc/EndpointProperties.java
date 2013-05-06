package de.m0ep.camel.socc;

import java.io.Serializable;

public class EndpointProperties implements Serializable {
    private static final long serialVersionUID = -5608053926194170370L;

    private String forumId;
    private String threadId;
    private String postId;
    private int delay = 500;

    /**
     * @return the forumId
     */
    public String getForumId() {
	return this.forumId;
    }

    /**
     * @param forumId
     *            the forumId to set
     */
    public void setForumId(String forumId) {
	this.forumId = forumId;
    }

    /**
     * @return the threadId
     */
    public String getThreadId() {
	return this.threadId;
    }

    /**
     * @param threadId
     *            the threadId to set
     */
    public void setThreadId(String threadId) {
	this.threadId = threadId;
    }

    /**
     * @return the postId
     */
    public String getPostId() {
	return this.postId;
    }

    /**
     * @param postId
     *            the postId to set
     */
    public void setPostId(String postId) {
	this.postId = postId;
    }

    /**
     * @return the delay
     */
    public int getDelay() {
	return this.delay;
    }

    /**
     * @param delay
     *            the delay to set
     */
    public void setDelay(int delay) {
	this.delay = delay;
    }

}
