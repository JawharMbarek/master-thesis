package de.m0ep.test.socc.core.connector.google.plus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.util.Builder;

import de.m0ep.socc.core.connector.google.plus.GooglePlusSiocUtils;

public class GooglePlusSiocUtilsTest {
	private static final String USER_ID = "113551191017950459231";

	private static final String ACTIVITY_ID = "z124vtiqgubyhv50c22vspexqvqjud3hj04";

	private static final String COMMENT_ID = "z13kenc4qlilc5w3a22stxx53njmevzhw04.1379025172618341";

	private static final String PUBLIC_COLLECTION = "public";

	private static final URI PERSON_URI = Builder.createURI(
	        "https://www.googleapis.com/plus/v1/people/"
	                + USER_ID );

	private static final URI ACTIVITY_URI = Builder.createURI(
	        "https://www.googleapis.com/plus/v1/activities/"
	                + ACTIVITY_ID );
	private static final URI COMMENT_URI = Builder.createURI(
	        "https://www.googleapis.com/plus/v1/comments/"
	                + COMMENT_ID );
	private static final URI ACTIVITY_FEED_URI = Builder.createURI(
	        "https://www.googleapis.com/plus/v1/people/"
	                + USER_ID
	                + "/activities/"
	                + PUBLIC_COLLECTION );
	private static final URI NON_GP_URI = Builder.createURI(
	        "http://www.example.com" );

	@Test
	public void createPersonUri() throws Exception {
		URI expected = PERSON_URI;
		URI actual = GooglePlusSiocUtils.createPersonUri( USER_ID );
		assertEquals( expected, actual );
	}

	@Test
	public void createActivityUri() throws Exception {
		URI expected = ACTIVITY_URI;
		URI actual = GooglePlusSiocUtils.createActivityUri( ACTIVITY_ID );
		assertEquals( expected, actual );
	}

	@Test
	public void createActivityFeedUri() throws Exception {
		URI expected = ACTIVITY_FEED_URI;
		URI actual = GooglePlusSiocUtils.createActivityFeedUri( USER_ID, PUBLIC_COLLECTION );
		assertEquals( expected, actual );
	}

	@Test
	public void createCommentUri() throws Exception {
		URI expected = COMMENT_URI;
		URI actual = GooglePlusSiocUtils.createCommentUri( COMMENT_ID );
		assertEquals( expected, actual );
	}

	@Test
	public void isPersonUriTest() throws Exception {
		assertTrue( GooglePlusSiocUtils.isPersonUri( PERSON_URI ) );
		assertFalse( GooglePlusSiocUtils.isPersonUri( ACTIVITY_URI ) );
		assertFalse( GooglePlusSiocUtils.isPersonUri( COMMENT_URI ) );
		assertFalse( GooglePlusSiocUtils.isPersonUri( ACTIVITY_FEED_URI ) );
		assertFalse( GooglePlusSiocUtils.isPersonUri( NON_GP_URI ) );
	}

	@Test
	public void isActivityUriTest() throws Exception {
		assertTrue( GooglePlusSiocUtils.isActivityUri( ACTIVITY_URI ) );
		assertFalse( GooglePlusSiocUtils.isActivityUri( PERSON_URI ) );
		assertFalse( GooglePlusSiocUtils.isActivityUri( COMMENT_URI ) );
		assertFalse( GooglePlusSiocUtils.isActivityUri( ACTIVITY_FEED_URI ) );
		assertFalse( GooglePlusSiocUtils.isActivityUri( NON_GP_URI ) );
	}

	@Test
	public void isActivityFeedUriTest() throws Exception {
		assertTrue( GooglePlusSiocUtils.isActivityFeedUri( ACTIVITY_FEED_URI ) );
		assertFalse( GooglePlusSiocUtils.isActivityFeedUri( PERSON_URI ) );
		assertFalse( GooglePlusSiocUtils.isActivityFeedUri( COMMENT_URI ) );
		assertFalse( GooglePlusSiocUtils.isActivityFeedUri( ACTIVITY_URI ) );
		assertFalse( GooglePlusSiocUtils.isActivityFeedUri( NON_GP_URI ) );
	}

	@Test
	public void isCommentUriTest() throws Exception {
		assertTrue( GooglePlusSiocUtils.isCommentUri( COMMENT_URI ) );
		assertFalse( GooglePlusSiocUtils.isCommentUri( ACTIVITY_FEED_URI ) );
		assertFalse( GooglePlusSiocUtils.isCommentUri( PERSON_URI ) );
		assertFalse( GooglePlusSiocUtils.isCommentUri( ACTIVITY_URI ) );
		assertFalse( GooglePlusSiocUtils.isCommentUri( NON_GP_URI ) );
	}
}
