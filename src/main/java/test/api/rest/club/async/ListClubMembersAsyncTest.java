package test.api.rest.club.async;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.rest.club.ListClubMembersTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListClubMembersAsyncTest extends ListClubMembersTest {
	/**
	 *
	 */
	public ListClubMembersAsyncTest() {
		this.listCallback = (api, id) -> api.listClubMembersAsync(id, null, null).get();
		this.pagingCallback = paging -> api().listClubMembersAsync(validId(), paging.getPage(), paging.getPageSize())
				.get();
	}

	// 3. Private club of which current authenticated athlete is a member
	@Override
	public void testListClubMembers_privateClubIsMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete[] members = api().listClubMembersAsync(TestUtils.CLUB_PRIVATE_MEMBER_ID, null, null)
					.get();
			assertNotNull(members);
			assertFalse(members.length == 0);
			for (final StravaAthlete athlete : members) {
				validate(athlete);
			}
		} );
	}

	// 4. Private club of which current authenticated athlete is NOT a member
	@Override
	public void testListClubMembers_privateClubNotMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listClubMembersAsync(TestUtils.CLUB_PRIVATE_NON_MEMBER_ID, null, null).get();
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned list of members for a club of which the authenticated athlete is not a member!");
		} );
	}

}
