package test.api.service.impl.clubservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaClubAnnouncement;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

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
			try {
				strava().listClubAnnouncements(TestUtils.CLUB_INVALID_ID);
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			fail("Returned announcements for an invalid club!");

		});
	}

	@Test
	public void testListClubAnnouncements_privateClubMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaClubAnnouncement> announcements = strava().listClubAnnouncements(TestUtils.CLUB_PRIVATE_MEMBER_ID);
			assertNotNull(announcements);
		});
	}

	@Test
	public void testListClubAnnouncements_privateClubNonMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				strava().listClubAnnouncements(TestUtils.CLUB_PRIVATE_NON_MEMBER_ID);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned announcements for a club the authenticated athlete is not a member of!");
		});
	}

	@Test
	public void testListClubAnnouncements_validClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaClubAnnouncement> announcements = strava().listClubAnnouncements(TestUtils.CLUB_VALID_ID);
			assertNotNull(announcements);
		});
	}
}
