package test.api.club.async;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeFalse;

import org.junit.Test;

import javastrava.api.API;
import javastrava.model.StravaClub;
import javastrava.service.exception.NotFoundException;
import javastrava.service.exception.UnauthorizedException;
import test.api.club.LeaveClubTest;
import test.issues.strava.Issue164;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific tests and config for {@link API#leaveClubAsync(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class LeaveClubAsyncTest extends LeaveClubTest {
	@Override
	@Test
	public void testLeaveClub_invalidClub() throws Exception {
		assumeFalse(Issue164.issue);

		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_INVALID_ID;

			try {
				apiWithWriteAccess().leaveClubAsync(id).get();
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Left a non-existent club!"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testLeaveClub_member() throws Exception {
		assumeFalse(Issue164.issue);

		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PUBLIC_MEMBER_ID;

			apiWithWriteAccess().leaveClubAsync(id).get();

			final StravaClub[] clubs = api().listAuthenticatedAthleteClubsAsync().get();
			final boolean member = ClubDataUtils.checkIsMember(clubs, id);

			// Join the club again
			apiWithWriteAccess().joinClubAsync(id).get();

			assertFalse(member);
		});
	}

	@Override
	@Test
	public void testLeaveClub_nonMember() throws Exception {
		assumeFalse(Issue164.issue);

		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PUBLIC_NON_MEMBER_ID;

			apiWithWriteAccess().leaveClubAsync(id).get();

			final StravaClub[] clubs = api().listAuthenticatedAthleteClubsAsync().get();
			final boolean member = ClubDataUtils.checkIsMember(clubs, id);

			assertFalse(member);
		});
	}

	@Override
	@Test
	public void testLeaveClub_noWriteAccess() throws Exception {
		assumeFalse(Issue164.issue);

		RateLimitedTestRunner.run(() -> {
			final Integer id = ClubDataUtils.CLUB_PUBLIC_MEMBER_ID;

			try {
				api().leaveClubAsync(id).get();
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Left a club without write access"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testLeaveClub_privateClubMember() throws Exception {
		assumeFalse(Issue164.issue);

		// CAN'T DO THIS IN TESTING AS YOU'LL NEVER BE ABLE TO JOIN IT AGAIN!!
		return;
		// RateLimitedTestRunner.run(() -> {
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
		// });
	}

}
