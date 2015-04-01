package test.api.rest.club;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.model.StravaClubMembershipResponse;

import org.junit.Test;

import test.api.rest.APITest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class LeaveClubTest extends APITest {
	/**
	 * @param clubs
	 *            List of clubs to check
	 * @param id
	 *            Id of the club we're checking for membership
	 * @return <code>true</code> if one of the clubs has the given id
	 */
	private boolean checkIsMember(final StravaClub[] clubs, final Integer id) {
		for (final StravaClub club : clubs) {
			if (club.getId().intValue() == id.intValue()) {
				return true;
			}
		}
		return false;
	}

	// 3. Invalid club
	@Test
	public void testLeaveClub_invalidClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Integer id = TestUtils.CLUB_INVALID_ID;

			final StravaClubMembershipResponse response = apiWithWriteAccess().leaveClub(id);
			assertEquals(Boolean.FALSE, response.getSuccess());
		});
	}

	// 2. Valid club which authenticated user is already a member of
	@Test
	public void testLeaveClub_member() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Integer id = TestUtils.CLUB_PUBLIC_MEMBER_ID;

			apiWithWriteAccess().leaveClub(id);

			final StravaClub[] clubs = api().listAuthenticatedAthleteClubs();
			final boolean member = checkIsMember(clubs, id);

			// Join the club again
				apiWithWriteAccess().joinClub(id);

				assertFalse(member);
			});
	}

	// Test cases
	// 1. Valid club which authenticated user is not already a member of
	@Test
	public void testLeaveClub_nonMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Integer id = TestUtils.CLUB_PUBLIC_NON_MEMBER_ID;

			apiWithWriteAccess().leaveClub(id);

			final StravaClub[] clubs = api().listAuthenticatedAthleteClubs();
			final boolean member = checkIsMember(clubs, id);

			assertFalse(member);
		});
	}

	// 5. Leave a club using a token with no write access
	@Test
	public void testLeaveClub_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Integer id = TestUtils.CLUB_PUBLIC_MEMBER_ID;

			final StravaClubMembershipResponse response = api().leaveClub(id);
			assertNotNull(response);
			assertEquals(Boolean.FALSE, response.getSuccess());
		});
	}

	// 4. Private club which authenticated user is a member of
	// CAN'T DO THIS IN TESTING AS YOU'LL NEVER BE ABLE TO JOIN IT AGAIN!!
	@Test
	public void testLeaveClub_privateClubMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// ClubService service = getClubService();
			// Integer id = TestUtils.CLUB_PRIVATE_MEMBER_ID;
			//
			// serviceWithWriteAccess.leaveClub(id);
			//
			// List<StravaClub> clubs = service.listAuthenticatedAthleteClubs();
			// boolean member = checkIsMember(clubs, id);
			//
			// // Join the club again
			// serviceWithWriteAccess.joinClub(id);
			// assertFalse(member);
			});
	}

}
