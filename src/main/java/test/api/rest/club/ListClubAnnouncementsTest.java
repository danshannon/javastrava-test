package test.api.rest.club;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaClubAnnouncement;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class ListClubAnnouncementsTest extends StravaTest {
	@Test
	public void testListClubAnnouncements_invalidClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaClubAnnouncement> announcements = strava()
					.listClubAnnouncements(TestUtils.CLUB_INVALID_ID);
			assertNull(announcements);
		} );
	}

	@Test
	public void testListClubAnnouncements_privateClubMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaClubAnnouncement> announcements = strava()
					.listClubAnnouncements(TestUtils.CLUB_PRIVATE_MEMBER_ID);
			assertNotNull(announcements);
		} );
	}

	@Test
	public void testListClubAnnouncements_privateClubNonMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaClubAnnouncement> announcements = strava()
					.listClubAnnouncements(TestUtils.CLUB_PRIVATE_NON_MEMBER_ID);
			assertNotNull(announcements);
			assertTrue(announcements.isEmpty());
		} );
	}

	@Test
	public void testListClubAnnouncements_validClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaClubAnnouncement> announcements = strava().listClubAnnouncements(TestUtils.CLUB_VALID_ID);
			assertNotNull(announcements);
		} );
	}
}
