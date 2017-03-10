package test.api.rest.club;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.model.StravaClubMembershipResponse;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaClubTest;
import test.api.rest.APITest;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.RateLimitedTestRunner;

public class JoinClubTest extends APITest<StravaClub> {
	// 3. Invalid club
	@Test
	public void testJoinClub_invalidClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_INVALID_ID;

			try {
				apiWithWriteAccess().joinClub(id);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Joined a non-existent club!");
		});
	}

	// 2. Valid club which authenticated user is already a member of
	@Test
	public void testJoinClub_member() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PUBLIC_MEMBER_ID;

			final StravaClubMembershipResponse response = apiWithWriteAccess().joinClub(id);

			// Make sure the athlete now (still) a member
			final StravaClub[] clubs = api().listAuthenticatedAthleteClubs();
			final boolean member = StravaClubTest.checkIsMember(clubs, id);

			assertNotNull(response);
			assertTrue(response.getSuccess());
			assertTrue(response.getActive());
			assertTrue(member);
		});
	}

	// Test cases
	// 1. Valid club which authenticated user is not already a member of
	@Test
	public void testJoinClub_nonMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PUBLIC_NON_MEMBER_ID;

			final StravaClubMembershipResponse response = apiWithWriteAccess().joinClub(id);

			// Make sure the athlete now a member
			final StravaClub[] clubs = api().listAuthenticatedAthleteClubs();
			final boolean member = StravaClubTest.checkIsMember(clubs, id);

			// Leave the club again, just to be sure
			apiWithWriteAccess().leaveClub(id);

			assertNotNull(response);
			assertTrue(response.getSuccess());
			assertTrue(response.getActive());
			assertTrue(member);
		});
	}

	// 5. Attempt to join a club without having write access
	@Test
	public void testJoinClub_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PUBLIC_MEMBER_ID;

			try {
				api().joinClub(id);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Joined a club successfully without write access");
		});
	}

	// 4. Private club which authenticated user is NOT a member of
	@Test
	public void testJoinClub_privateClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID;

			try {
				apiWithWriteAccess().joinClub(id);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Joined a private club successfully");

		});
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaClub result) throws Exception {
		StravaClubTest.validate(result);

	}

}
