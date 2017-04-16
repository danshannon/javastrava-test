package test.api.rest.club.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeFalse;

import org.junit.Test;

import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.model.StravaClubMembershipResponse;
import javastrava.api.v3.model.reference.StravaClubMembershipStatus;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.rest.club.JoinClubTest;
import test.issues.strava.Issue164;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific tests and config for {@link API#joinClubAsync(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class JoinClubAsyncTest extends JoinClubTest {
	private static boolean issue164 = Issue164.issue();

	// 3. Invalid club
	@Override
	@Test
	public void testJoinClub_invalidClub() throws Exception {
		assumeFalse(issue164);

		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_INVALID_ID;

			try {
				apiWithWriteAccess().joinClubAsync(id).get();
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Joined a non-existent club!"); //$NON-NLS-1$
		});
	}

	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testJoinClub_member() throws Exception {
		assumeFalse(issue164);

		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PUBLIC_MEMBER_ID;

			final StravaClubMembershipResponse response = apiWithWriteAccess().joinClubAsync(id).get();

			// Make sure the athlete now (still) a member
			final StravaClub[] clubs = api().listAuthenticatedAthleteClubsAsync().get();
			final boolean member = ClubDataUtils.checkIsMember(clubs, id);

			assertNotNull(response);
			assertTrue(response.getSuccess());
			assertTrue(response.getActive());
			assertTrue(member);
		});
	}

	@SuppressWarnings("boxing")
	@Override
	@Test
	public void testJoinClub_nonMember() throws Exception {
		assumeFalse(issue164);

		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PUBLIC_NON_MEMBER_ID;

			final StravaClubMembershipResponse response = apiWithWriteAccess().joinClubAsync(id).get();

			// Make sure the athlete now a member
			final StravaClub[] clubs = api().listAuthenticatedAthleteClubsAsync().get();
			final boolean member = ClubDataUtils.checkIsMember(clubs, id);

			// Leave the club again, just to be sure
			apiWithWriteAccess().leaveClubAsync(id).get();

			assertNotNull(response);
			assertTrue(response.getSuccess());
			assertTrue(response.getActive());
			assertTrue(member);
		});
	}

	@Override
	@Test
	public void testJoinClub_noWriteAccess() throws Exception {
		assumeFalse(issue164);

		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PUBLIC_MEMBER_ID;

			try {
				api().joinClubAsync(id).get();
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Joined a club successfully without write access"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testJoinClub_privateClub() throws Exception {
		assumeFalse(issue164);

		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID;

			final StravaClubMembershipResponse response = apiWithWriteAccess().joinClubAsync(id).get();
			assertEquals("Attempt to join club " + ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID + ", which is private and of which the authenticated user is not a member, should result in pending status", //$NON-NLS-1$ //$NON-NLS-2$
					StravaClubMembershipStatus.PENDING, response.getMembership());

		});
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaClub result) throws Exception {
		ClubDataUtils.validate(result);

	}

}
