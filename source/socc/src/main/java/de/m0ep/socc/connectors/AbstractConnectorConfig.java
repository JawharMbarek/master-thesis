package de.m0ep.socc.connectors;


public abstract class AbstractConnectorConfig implements IConnectorConfig {
    private static final long serialVersionUID = 1992138033098739047L;

    public static final String MAX_NEW_POSTS_ON_POLL = "maxNewPostsOnPoll";
    public static final String FACTORY_NAME = "factoryName";

    private int maxNewPostsOnPoll;
    private String factoryName;

    public int getMaxNewPostsOnPoll() {
	return maxNewPostsOnPoll;
    }

    public void setMaxNewPostsOnPoll(int maxNewPostsOnPoll) {
	this.maxNewPostsOnPoll = maxNewPostsOnPoll;
    }

    public String getFactoryName() {
	return factoryName;
    }

    public void setFactoryName(String factoryName) {
	this.factoryName = factoryName;
    }
}
