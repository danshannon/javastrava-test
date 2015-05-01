package test.api.rest.club.async;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.model.StravaClubMembershipResponse;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaClubTest;
import test.api.rest.club.JoinClubTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class JoinClubAsyncTest extends JoinClubTest {
	// 3. Invalid club
	@Override
	@Test
	public void testJoinClub_invalidClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Integer id = TestUtils.CLUB_INVALID_ID;

			try {
				apiWithWriteAccess().joinClubAsync(id).get();
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Joined a non-existent club!");
		} );
	}

	// 2. Valid club which authenticated user is already a member of
	@Override
	@Test
	public void testJoinClub_member() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Integer id = TestUtils.CLUB_PUBLIC_MEMBER_ID;

			final StravaClubMembershipResponse response = apiWithWriteAccess().joinClubAsync(id).get();

			// Make sure the athlete now (still) a member
			final StravaClub[] clubs = api().listAuthenticatedAthleteClubsAsync().get();
			final boolean member = StravaClubTest.checkIsMember(clubs, id);

			assertNotNull(response);
			assertTrue(response.getSuccess());
			assertTrue(response.getActive());
			assertTrue(member);
		} );
	}

	// Test cases
	// 1. Valid club which authenticated user is not already a member of
	@Override
	@Test
	public void testJoinClub_nonMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Integer id = TestUtils.CLUB_PUBLIC_NON_MEMBER_ID;

			final StravaClubMembershipResponse response = apiWithWriteAccess().joinClubAsync(id).get();

			// Make sure the athlete now a member
			final StravaClub[] clubs = api().listAuthenticatedAthleteClubsAsync().get();
			final boolean member = StravaClubTest.checkIsMember(clubs, id);

			// Leave the club again, just to be sure
			apiWithWriteAccess().leaveClubAsync(id).get();

			assertNotNull(response);
			assertTrue(response.getSuccess());
			assertTrue(response.getActive());
			assertTrue(member);
		} );
	}

	// 5. Attempt to join a club without having write access
	@Override
	@Test
	public void testJoinClub_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Integer id = TestUtils.CLUB_PUBLIC_MEMBER_ID;

			try {
				api().joinClubAsync(id).get();
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Joined a club successfully without write access");
		} );
	}

	// 4. Private club which authenticated user is NOT a member of
	@Override
	@Test
	public void testJoinClub_privateClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Integer id = TestUtils.CLUB_PRIVATE_NON_MEMBER_ID;

			try {
				apiWithWriteAccess().joinClubAsync(id).get();
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Joined a private club successfully");

		} );
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaClub result) throws Exception {
		StravaClubTest.validate(result);

	}

}
