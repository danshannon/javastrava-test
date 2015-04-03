package test.api.rest.club;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaClubTest;
import test.api.rest.APITest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetClubTest extends APITest {
	// 2. Invalid club
	@Test
	public void testGetClub_invalidClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getClub(TestUtils.CLUB_INVALID_ID);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Got club result despite club being invalid");
		});
	}

	// 3. Private club of which current authenticated athlete is a member
	@Test
	public void testGetClub_privateClubIsMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaClub club = api().getClub(TestUtils.CLUB_PRIVATE_MEMBER_ID);
			assertNotNull(club);
			StravaClubTest.validate(club, TestUtils.CLUB_PRIVATE_MEMBER_ID, club.getResourceState());
		});
	}

	// 4. Private club of which current authenticated athlete is NOT a member
	@Test
	public void testGetClub_privateClubIsNotMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getClub(TestUtils.CLUB_PRIVATE_NON_MEMBER_ID);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned details of a private club of which the authenticated athlete is not a member");
		});
	}

	// Test cases
	// 1. Valid club
	@Test
	public void testGetClub_validClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaClub club = api().getClub(TestUtils.CLUB_VALID_ID);
			assertNotNull(club);
			StravaClubTest.validate(club, TestUtils.CLUB_VALID_ID, club.getResourceState());
		});
	}

}
