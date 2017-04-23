package test.service.impl.clubservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.model.StravaClubMembershipResponse;
import javastrava.api.v3.service.Strava;
import test.issues.strava.Issue164;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Tests for {@link Strava#joinClub(Integer)}
 *
 * @author Dan Shannon
 *
 */
public class JoinClubTest {
	private static boolean issue164 = Issue164.issue;

	/**
	 * @param clubs
	 *            List of clubs to check
	 * @param id
	 *            Id of the club we're checking for membership
	 * @return <code>true</code> if one of the clubs has the given id
	 */
	private static boolean checkIsMember(final List<StravaClub> clubs, final Integer id) {
		for (final StravaClub club : clubs) {
			if (club.getId().intValue() == id.intValue()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Setup data for the test
	 *
	 * @throws Exception
	 *             If setup fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Before
	public void setup() throws Exception {
		assumeFalse(issue164);

		RateLimitedTestRunner.run(() -> {
			final Strava strava = TestUtils.stravaWithWriteAccess();
			strava.leaveClub(ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID);
			strava.leaveClub(ClubDataUtils.CLUB_PUBLIC_NON_MEMBER_ID);
			strava.joinClub(ClubDataUtils.CLUB_PUBLIC_MEMBER_ID);
		});
	}

	/**
	 * Invalid club
	 *
	 * @throws Exception
	 *             if the test fails in an unexected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testJoinClub_invalidClub() throws Exception {
		assumeFalse(issue164);

		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_INVALID_ID;

			final StravaClubMembershipResponse response = TestUtils.stravaWithWriteAccess().joinClub(id);
			assertEquals(Boolean.FALSE, response.getSuccess());
		});
	}

	/**
	 * Valid club which authenticated user is already a member of
	 *
	 * @throws Exception
	 *             if the test fails in an unexected way
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public void testJoinClub_member() throws Exception {
		assumeFalse(issue164);

		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PUBLIC_MEMBER_ID;

			final StravaClubMembershipResponse response = TestUtils.stravaWithWriteAccess().joinClub(id);

			// Make sure the athlete now (still) a member
			final List<StravaClub> clubs = TestUtils.strava().listAuthenticatedAthleteClubs();
			final boolean member = checkIsMember(clubs, id);

			assertNotNull(response);
			assertTrue(response.getSuccess());
			assertTrue(response.getActive());
			assertTrue(member);
		});
	}

	/**
	 * Valid club which authenticated user is not already a member of
	 *
	 * @throws Exception
	 *             if the test fails in an unexected way
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public void testJoinClub_nonMember() throws Exception {
		assumeFalse(issue164);

		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PUBLIC_NON_MEMBER_ID;

			final StravaClubMembershipResponse response = TestUtils.stravaWithWriteAccess().joinClub(id);

			// Make sure the athlete now a member
			final List<StravaClub> clubs = TestUtils.strava().listAuthenticatedAthleteClubs();
			final boolean member = checkIsMember(clubs, id);

			// Leave the club again, just to be sure
			TestUtils.stravaWithWriteAccess().leaveClub(id);

			assertNotNull(response);
			assertTrue(response.getSuccess());
			assertTrue(response.getActive());
			assertTrue(member);
		});
	}

	/**
	 * Attempt to join a club without having write access
	 *
	 * @throws Exception
	 *             if the test fails in an unexected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testJoinClub_noWriteAccess() throws Exception {
		assumeFalse(issue164);

		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PUBLIC_MEMBER_ID;

			final StravaClubMembershipResponse response = TestUtils.strava().joinClub(id);
			assertNotNull(response);
			assertEquals(Boolean.FALSE, response.getSuccess());
		});
	}

	/**
	 * Private club which authenticated user is NOT a member of
	 *
	 * @throws Exception
	 *             if the test fails in an unexected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testJoinClub_privateClub() throws Exception {
		assumeFalse(issue164);

		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID;

			final StravaClubMembershipResponse response = TestUtils.stravaWithWriteAccess().joinClub(id);
			assertNotNull(response);
			assertEquals(Boolean.TRUE, response.getSuccess());
			assertEquals(Boolean.FALSE, response.getActive());
		});
	}

}
