package test.api.service.impl.retrofit.clubservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.model.StravaClubMembershipResponse;
import javastrava.api.v3.service.ClubServices;
import javastrava.api.v3.service.impl.retrofit.ClubServicesImpl;

import org.junit.Test;

import test.utils.TestUtils;

public class LeaveClubTest {
	// Test cases
	// 1. Valid club which authenticated user is not already a member of
	@Test
	public void testLeaveClub_nonMember() {
		final Integer id = TestUtils.CLUB_PUBLIC_NON_MEMBER_ID;

		service().leaveClub(id);

		final List<StravaClub> clubs = service().listAuthenticatedAthleteClubs();
		final boolean member = checkIsMember(clubs, id);

		assertFalse(member);
	}

	// 2. Valid club which authenticated user is already a member of
	@Test
	public void testLeaveClub_member() {
		final Integer id = TestUtils.CLUB_PUBLIC_MEMBER_ID;

		service().leaveClub(id);

		final List<StravaClub> clubs = service().listAuthenticatedAthleteClubs();
		final boolean member = checkIsMember(clubs, id);

		// Join the club again
		service().joinClub(id);

		assertFalse(member);
	}

	// 3. Invalid club
	@Test
	public void testLeaveClub_invalidClub() {
		final Integer id = TestUtils.CLUB_INVALID_ID;

		final StravaClubMembershipResponse response = service().leaveClub(id);
		assertEquals(Boolean.FALSE, response.getSuccess());
	}

	// 4. Private club which authenticated user is a member of
	// CAN'T DO THIS IN TESTING AS YOU'LL NEVER BE ABLE TO JOIN IT AGAIN!!
	// @Test
	// public void testLeaveClub_privateClubMember() throws UnauthorizedException, NotFoundException {
	// ClubServices service = getClubService();
	// Integer id = TestUtils.CLUB_PRIVATE_MEMBER_ID;
	//
	// service.leaveClub(id);
	//
	// List<StravaClub> clubs = service.listAuthenticatedAthleteClubs();
	// boolean member = checkIsMember(clubs, id);
	//
	// // Join the club again
	// service.joinClub(id);
	// assertFalse(member);
	// }

	// 5. Leave a club using a token with no write access
	@Test
	public void testLeaveClub_noWriteAccess() {
		final Integer id = TestUtils.CLUB_PUBLIC_MEMBER_ID;

		final StravaClubMembershipResponse response = serviceWithoutWriteAccess().leaveClub(id);
		assertNotNull(response);
		assertEquals(Boolean.FALSE, response.getSuccess());
	}

	private ClubServices service() {
		return ClubServicesImpl.implementation(TestUtils.getValidToken());
	}

	private ClubServices serviceWithoutWriteAccess() {
		return ClubServicesImpl.implementation(TestUtils.getValidTokenWithoutWriteAccess());
	}

	/**
	 * @param clubs
	 *            List of clubs to check
	 * @param id
	 *            Id of the club we're checking for membership
	 * @return <code>true</code> if one of the clubs has the given id
	 */
	private boolean checkIsMember(final List<StravaClub> clubs, final Integer id) {
		for (final StravaClub club : clubs) {
			if (club.getId().intValue() == id.intValue()) {
				return true;
			}
		}
		return false;
	}

}
