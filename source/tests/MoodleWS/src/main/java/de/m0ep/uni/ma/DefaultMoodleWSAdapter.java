package de.m0ep.uni.ma;

import net.patrickpollet.moodlews_gson.core.CourseRecord;
import net.patrickpollet.moodlews_gson.core.ForumRecord;
import net.patrickpollet.moodlews_gson.core.LoginReturn;
import net.patrickpollet.moodlews_gson.core.Mdl_restserverBindingStub;
import net.patrickpollet.moodlews_gson.core.UserRecord;

import com.google.common.base.Preconditions;

public class DefaultMoodleWSAdapter implements MoodleWSAdapter {

    private Mdl_restserverBindingStub moodle;
    private LoginReturn loginData;
    private int myId;

    public DefaultMoodleWSAdapter(final String url) {
	this.moodle = new Mdl_restserverBindingStub(url, "json", true);
	this.loginData = null;
	this.myId = -1;

    }

    private boolean checkLogin() {
	return null != loginData;
    }

    public boolean login(String username, String password) {
	Preconditions.checkState(checkLogin(), "not logged in");
	Preconditions.checkNotNull(username, "username can't be null");
	Preconditions.checkNotNull(password, "password can't be null");

	loginData = moodle.login(username, password);
	if (null != loginData) {
	    myId = getMyId();
	}

	return checkLogin();
    }

    public void logout() {
	if (checkLogin()) {
	    moodle.logout(loginData.getClient(), loginData.getSessionkey());
	}

	loginData = null;
	myId = -1;
    }

    public int getMyId() {
	Preconditions.checkState(checkLogin(), "not logged in");

	if (-1 == myId)
	    myId = moodle.get_my_id(loginData.getClient(),
		    loginData.getSessionkey());

	return myId;
    }

    public UserRecord getUserById(int id) {
	Preconditions.checkState(checkLogin(), "not logged in");

	UserRecord[] result = moodle.get_user_byid(loginData.getClient(),
		loginData.getSessionkey(),
		id);

	if (null == result)
	    throw new MoodleWSException("failed to load userdetails for id = "
		    + id);

	if (0 < result.length && !result[0].getError().isEmpty())
	    throw new MoodleWSException("Server reported an error: "
		    + result[0].getError());

	return result[0];
    }

    public CourseRecord[] getMyCourses() {
	Preconditions.checkState(checkLogin(), "not logged in");

	CourseRecord[] result = moodle.get_my_courses(loginData.getClient(),
		loginData.getSessionkey(), Integer.toString(myId), "asc");


	if (null == result)
	    throw new MoodleWSException("failed to load courses");

	if (0 < result.length && null != result[0].getError()
		&& !result[0].getError().isEmpty())
	    throw new MoodleWSException("Server reported an error: "
		    + result[0].getError());

	return result;
    }

    public ForumRecord[] getAllForums() {
	Preconditions.checkState(checkLogin(), "not logged in");

	ForumRecord[] result = moodle.get_all_forums(loginData.getClient(),
		loginData.getSessionkey(), "", "");

	if (null == result)
	    throw new MoodleWSException("failed to load forums");

	if (0 < result.length && null != result[0].getError()
		&& !result[0].getError().isEmpty())
	    throw new MoodleWSException("Server reported an error: "
		    + result[0].getError());

	return result;
    }

}
