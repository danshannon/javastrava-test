package test.api.rest.club;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.rest.util.ArrayCallback;
import test.api.rest.util.PagingArrayMethodTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListClubMembersTest extends PagingArrayMethodTest<StravaAthlete, Integer> {
	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return (paging -> api().listClubMembers(TestUtils.CLUB_VALID_ID, paging.getPage(), paging.getPageSize()));
	}

	// 2. Invalid club
	@Test
	public void testListClubMembers_invalidClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listClubMembers(TestUtils.CLUB_INVALID_ID, null, null);
			} catch (NotFoundException e) {
				// Expected
				return;
			}
			fail("Returned a list of members for a non-existent club");
		});
	}

	// 3. Private club of which current authenticated athlete is a member
	@Test
	public void testListClubMembers_privateClubIsMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete[] members = api().listClubMembers(TestUtils.CLUB_PRIVATE_MEMBER_ID, null, null);
			assertNotNull(members);
			assertFalse(members.length == 0);
			for (final StravaAthlete athlete : members) {
				validate(athlete);
			}
		});
	}

	// 4. Private club of which current authenticated athlete is NOT a member
	@Test
	public void testListClubMembers_privateClubNotMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listClubMembers(TestUtils.CLUB_PRIVATE_NON_MEMBER_ID, null, null);
			} catch (UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned list of members for a club of which the authenticated athlete is not a member!");
		});
	}

	// Test cases
	// 1. Valid club
	@Test
	public void testListClubMembers_validClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete[] members = api().listClubMembers(TestUtils.CLUB_VALID_ID, null, null);
			assertNotNull(members);
			assertFalse(members.length == 0);
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
