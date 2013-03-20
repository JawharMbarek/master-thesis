package de.m0ep.socc.connectors;


public abstract class DefaultConnectorConfig implements IConnectorConfig {
    private static final long serialVersionUID = 1992138033098739047L;

    public static final String MAX_NEW_POSTS_ON_POLL = "maxNewPostsOnPoll";
    public static final String POLL_COOLDOWN = "pollCooldownMillis";

    private int maxNewPostsOnPoll;
    private int pollCooldownMillis;

    public int getMaxNewPostsOnPoll() {
	return maxNewPostsOnPoll;
    }

    public void setMaxNewPostsOnPoll(int maxNewPostsOnPoll) {
	this.maxNewPostsOnPoll = maxNewPostsOnPoll;
    }

    public int getPollCooldownMillis() {
	return pollCooldownMillis;
    }

    public void setPollCooldownMillis(int pollCoolDown) {
	this.pollCooldownMillis = pollCoolDown;
    }
}
