package test.api.rest.club.async;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.rest.club.LeaveClubTest;
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
