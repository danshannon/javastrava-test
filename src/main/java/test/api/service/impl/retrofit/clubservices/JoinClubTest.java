package test.api.service.impl.retrofit.clubservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.model.StravaClubMembershipResponse;
import javastrava.api.v3.service.ClubServices;
import javastrava.api.v3.service.impl.retrofit.ClubServicesImpl;

import org.junit.Test;

import test.utils.TestUtils;

public class JoinClubTest {
	// Test cases
	// 1. Valid club which authenticated user is not already a member of
	@Test
	public void testJoinClub_nonMember() {
		final Integer id = TestUtils.CLUB_PUBLIC_NON_MEMBER_ID;

		final StravaClubMembershipResponse response = service().joinClub(id);

		// Make sure the athlete now a member
		final List<StravaClub> clubs = service().listAuthenticatedAthleteClubs();
		final boolean member = checkIsMember(clubs, id);

		// Leave the club again, just to be sure
		service().leaveClub(id);

		assertNotNull(response);
		assertTrue(response.getSuccess());
		assertTrue(response.getActive());
		assertTrue(member);
	}

	// 2. Valid club which authenticated user is already a member of
	@Test
	public void testJoinClub_member() {
		final Integer id = TestUtils.CLUB_VALID_ID;

		final StravaClubMembershipResponse response = service().joinClub(id);

		// Make sure the athlete now (still) a member
		final List<StravaClub> clubs = service().listAuthenticatedAthleteClubs();
		final boolean member = checkIsMember(clubs, id);

		assertNotNull(response);
		assertTrue(response.getSuccess());
		assertTrue(response.getActive());
		assertTrue(member);
	}

	// 3. Invalid club
	@Test
	public void testJoinClub_invalidClub() {
		final Integer id = TestUtils.CLUB_INVALID_ID;

		final StravaClubMembershipResponse response = service().joinClub(id);
		assertEquals(Boolean.FALSE, response.getSuccess());
	}

	// 4. Private club which authenticated user is NOT a member of
	@Test
	public void testJoinClub_privateClub() {
		final Integer id = TestUtils.CLUB_PRIVATE_NON_MEMBER_ID;

		final StravaClubMembershipResponse response = service().joinClub(id);
		assertNotNull(response);
		assertEquals(Boolean.FALSE, response.getSuccess());

	}

	// 5. Attempt to join a club without having write access
	@Test
	public void testJoinClub_noWriteAccess() {
		final Integer id = TestUtils.CLUB_VALID_ID;

		final StravaClubMembershipResponse response = serviceWithoutWriteAccess().joinClub(id);
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
