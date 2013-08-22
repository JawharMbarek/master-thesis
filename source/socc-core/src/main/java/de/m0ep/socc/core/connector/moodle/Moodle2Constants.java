package de.m0ep.socc.core.connector.moodle;

public final class Moodle2Constants {
	private Moodle2Constants() {
	}

	public static final String REGEX_USER_URI =
	        "/user/profile.php?id=([0-9]+)";

	public static final String REGEX_FORUM_URI =
	        "/mod/forum/view\\.php\\?id=([0-9]+)";

	public static final String REGEX_DISCUSSION_URI =
	        "/mod/forum/discuss\\.php\\?d=([0-9]+)";

	public static final String REGEX_ENTRY_URI =
	        "/mod/forum/discuss.php?d=([0-9]+)#p([0-9]+)";

	public static final String TEMPLATE_USER_URI =
	        "/user/profile.php?id={userId}";

	public static final String TEMPLATE_FORUM_URI =
	        "/mod/forum/view.php?id={forumId}";

	public static final String TEMPLATE_DISCUSSION_URI =
	        "/mod/forum/discuss.php?d={discussionId}";

	public static final String TEMPLATE_ENTRY_URI =
	        "/mod/forum/discuss.php?d={discussionId}#p{postId}";

	public static final String TEMPLATE_VAR_USER_ID =
	        "userId";

	public static final String TEMPLATE_VAR_FORUM_ID =
	        "forumId";

	public static final String TEMPLATE_VAR_DISCUSSION_ID =
	        "discussionId";

	public static final String TEMPLATE_VAR_ENTRY_ID =
	        "postId";
}
