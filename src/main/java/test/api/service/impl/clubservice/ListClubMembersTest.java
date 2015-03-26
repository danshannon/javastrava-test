package test.api.service.impl.clubservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.service.impl.util.ListCallback;
import test.api.service.impl.util.PagingListMethodTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListClubMembersTest extends PagingListMethodTest<StravaAthlete, Integer> {
	@Override
	protected ListCallback<StravaAthlete> callback() {
		return (paging -> strava().listClubMembers(TestUtils.CLUB_VALID_ID, paging));
	}

	// 2. Invalid club
	@Test
	public void testListClubMembers_invalidClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			List<StravaAthlete> members;

			members = strava().listClubMembers(TestUtils.CLUB_INVALID_ID);
			assertNull("Returned a list of members for a non-existent club", members);
		});
	}

	// 3. Private club of which current authenticated athlete is a member
	@Test
	public void testListClubMembers_privateClubIsMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> members = strava().listClubMembers(TestUtils.CLUB_PRIVATE_MEMBER_ID);
			assertNotNull(members);
			assertFalse(members.size() == 0);
			for (final StravaAthlete athlete : members) {
				validate(athlete);
			}
		});
	}

	// 4. Private club of which current authenticated athlete is NOT a member
	@Test
	public void testListClubMembers_privateClubNotMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> members = strava().listClubMembers(TestUtils.CLUB_PRIVATE_NON_MEMBER_ID);
			assertNotNull(members);
			assertEquals(0, members.size());
		});
	}

	// Test cases
	// 1. Valid club
	@Test
	public void testListClubMembers_validClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> members = strava().listClubMembers(TestUtils.CLUB_VALID_ID);
			assertNotNull(members);
			assertFalse(members.size() == 0);
			for (final StravaAthlete athlete : members) {
				validate(athlete);
			}
		});
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		assertNotNull(athlete);
		validate(athlete, athlete.getId(), athlete.getResourceState());

	}

	@Override
	protected void validate(final StravaAthlete athlete, final Integer id, final StravaResourceState state) {
		StravaAthleteTest.validateAthlete(athlete, id, state);

	}

}
