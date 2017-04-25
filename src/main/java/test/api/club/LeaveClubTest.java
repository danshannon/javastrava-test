package test.api.club;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeFalse;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javastrava.api.API;
import javastrava.model.StravaClub;
import javastrava.service.exception.NotFoundException;
import javastrava.service.exception.UnauthorizedException;
import test.api.APITest;
import test.issues.strava.Issue164;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific config and tests for {@link API#joinClub(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class LeaveClubTest extends APITest<StravaClub> {
	/**
	 * Set up the test data
	 *
	 * @throws Exception
	 *             If the setup fails in an unexpected way
	 */
	@BeforeClass
	public static void testSetup() throws Exception {
		if (!Issue164.issue) {

			RateLimitedTestRunner.run(() -> {
				final API api = apiWithFullAccess();
				api.joinClub(ClubDataUtils.CLUB_PRIVATE_MEMBER_ID);
				api.joinClub(ClubDataUtils.CLUB_PUBLIC_MEMBER_ID);
				api.leaveClub(ClubDataUtils.CLUB_PUBLIC_NON_MEMBER_ID);
			});
		}
	}

	/**
	 * Leave the data like you find it
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@AfterClass
	public static void testTeardown() throws Exception {
		assumeFalse(Issue164.issue);

		RateLimitedTestRunner.run(() -> {
			final API api = apiWithFullAccess();
			api.joinClub(ClubDataUtils.CLUB_PRIVATE_MEMBER_ID);
			api.joinClub(ClubDataUtils.CLUB_PUBLIC_MEMBER_ID);
			api.leaveClub(ClubDataUtils.CLUB_PUBLIC_NON_MEMBER_ID);
		});
	}

	/**
	 * Invalid club
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testLeaveClub_invalidClub() throws Exception {
		assumeFalse(Issue164.issue);

		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_INVALID_ID;

			try {
				apiWithWriteAccess().leaveClub(id);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Successfully left a non-existent club!"); //$NON-NLS-1$
		});
	}

	/**
	 * Valid club which authenticated user is already a member of
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testLeaveClub_member() throws Exception {
		assumeFalse(Issue164.issue);

		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PUBLIC_MEMBER_ID;

			apiWithWriteAccess().leaveClub(id);

			final StravaClub[] clubs = api().listAuthenticatedAthleteClubs();
			final boolean member = ClubDataUtils.checkIsMember(clubs, id);

			// Join the club again
			// apiWithWriteAccess().joinClub(id);

			assertFalse(member);
		});
	}

	/**
	 * Valid club which authenticated user is not already a member of
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testLeaveClub_nonMember() throws Exception {
		assumeFalse(Issue164.issue);

		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PUBLIC_NON_MEMBER_ID;

			apiWithWriteAccess().leaveClub(id);

			final StravaClub[] clubs = api().listAuthenticatedAthleteClubs();
			final boolean member = ClubDataUtils.checkIsMember(clubs, id);

			assertFalse(member);
		});
	}

	/**
	 * Leave a club using a token with no write access
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testLeaveClub_noWriteAccess() throws Exception {
		assumeFalse(Issue164.issue);

		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PUBLIC_MEMBER_ID;

			try {
				api().leaveClub(id);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Succesfully left a club using a token without write access"); //$NON-NLS-1$
		});
	}

	/**
	 * Private club which authenticated user is a member of
	 *
	 * CAN'T DO THIS IN TESTING AS YOU'LL NEVER BE ABLE TO JOIN IT AGAIN!!
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testLeaveClub_privateClubMember() throws Exception {
		assumeFalse(Issue164.issue);

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

	/**
	 * @see test.api.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaClub result) throws Exception {
		ClubDataUtils.validate(result);

	}

}
