package test.api.rest.club;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.model.StravaClubMembershipResponse;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.rest.APITest;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific tests and configuration for {@link API#joinClub(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class JoinClubTest extends APITest<StravaClub> {
	/**
	 * Invalid club
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
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
			fail("Joined a non-existent club!"); //$NON-NLS-1$
		});
	}

	/**
	 * Valid club which authenticated user is already a member of
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public void testJoinClub_member() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PUBLIC_MEMBER_ID;

			final StravaClubMembershipResponse response = apiWithWriteAccess().joinClub(id);

			// Make sure the athlete now (still) a member
			final StravaClub[] clubs = api().listAuthenticatedAthleteClubs();
			final boolean member = ClubDataUtils.checkIsMember(clubs, id);

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
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings({ "static-method", "boxing" })
	@Test
	public void testJoinClub_nonMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PUBLIC_NON_MEMBER_ID;

			final StravaClubMembershipResponse response = apiWithWriteAccess().joinClub(id);

			// Make sure the athlete now a member
			final StravaClub[] clubs = api().listAuthenticatedAthleteClubs();
			final boolean member = ClubDataUtils.checkIsMember(clubs, id);

			// Leave the club again, just to be sure
			apiWithWriteAccess().leaveClub(id);

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
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
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
			fail("Joined a club successfully without write access"); //$NON-NLS-1$
		});
	}

	/**
	 * Private club which authenticated user is NOT a member of
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testJoinClub_privateClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID;

			// Leave first, just to be sure
			apiWithFullAccess().leaveClub(id);

			try {
				apiWithWriteAccess().joinClub(id);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Joined a private club successfully"); //$NON-NLS-1$

		});
	}

	@Override
	protected void validate(final StravaClub result) throws Exception {
		ClubDataUtils.validate(result);

	}

}
