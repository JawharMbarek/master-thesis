package de.m0ep.uni.ma;

import net.patrickpollet.moodlews_gson.core.CourseRecord;
import net.patrickpollet.moodlews_gson.core.ForumRecord;
import net.patrickpollet.moodlews_gson.core.UserRecord;

public interface MoodleWSAdapter {
    public boolean login( String username, String password );

    public int getMyId();
    public UserRecord getUserById( int id );

    public CourseRecord[] getMyCourses();

    public ForumRecord[] getAllForums();
}
