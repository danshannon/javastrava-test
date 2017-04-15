package test.api.rest.club.async;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.rest.API;
import test.api.rest.callback.APIListCallback;
import test.api.rest.club.ListClubMembersTest;
import test.api.rest.util.ArrayCallback;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific config and tests for {@link API#listClubMembersAsync(Integer, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListClubMembersAsyncTest extends ListClubMembersTest {
	@Override
	protected APIListCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listClubMembersAsync(id, null, null).get();
	}

	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listClubMembersAsync(validId(), paging.getPage(), paging.getPageSize()).get();
	}

	@Override
	public void testListClubMembers_privateClubIsMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete[] members = api().listClubMembersAsync(ClubDataUtils.CLUB_PRIVATE_MEMBER_ID, null, null).get();
			assertNotNull(members);
			assertFalse(members.length == 0);
			for (final StravaAthlete athlete : members) {
				validate(athlete);
			}
		});
	}

	@Override
	public void testListClubMembers_privateClubNotMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			apiWithFullAccess().leaveClub(ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID);
			final StravaAthlete[] members = api().listClubMembersAsync(ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID, null, null).get();
			assertNotNull(members);
		});
	}
}
