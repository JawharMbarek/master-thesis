package de.m0ep.canvaslms;

public enum DiscussionParent {
    COURSE, GROUP;

    public static String toString(DiscussionParent type) {

	switch (type) {
	case COURSE:
	    return "course";
	case GROUP:
	    return "group";
	default:
	    return "";
	}
    }
}
