/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.m0ep.socc.config;

import org.mortbay.jetty.Connector;

/**
 * A default configuration JavaBean with some useful properties for all
 * {@link Connector}s.
 * 
 * @author Florian Müller
 * 
 */
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
