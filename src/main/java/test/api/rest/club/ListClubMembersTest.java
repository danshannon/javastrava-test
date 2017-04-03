package test.api.rest.club;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.rest.API;
import test.api.rest.APIPagingListTest;
import test.api.rest.callback.APIListCallback;
import test.api.rest.util.ArrayCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific tests and config for {@link API#listClubMembers(Integer, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListClubMembersTest extends APIPagingListTest<StravaAthlete, Integer> {
	@Override
	protected Integer invalidId() {
		return ClubDataUtils.CLUB_INVALID_ID;
	}

	@Override
	protected APIListCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listClubMembers(id, null, null);
	}

	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listClubMembers(validId(), paging.getPage(), paging.getPageSize());
	}

	@Override
	protected Integer privateId() {
		return null;
	}

	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * Private club of which current authenticated athlete is a member
	 *
	 * @throws Exception
	 *             if the test fails for an unexpected reason
	 */
	@Test
	public void testListClubMembers_privateClubIsMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaAthlete[] members = api().listClubMembers(ClubDataUtils.CLUB_PRIVATE_MEMBER_ID, null, null);
			assertNotNull(members);
			assertFalse(members.length == 0);
			for (final StravaAthlete athlete : members) {
				validate(athlete);
			}
		});
	}

	/**
	 * Private club of which current authenticated athlete is NOT a member
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListClubMembers_privateClubNotMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			apiWithFullAccess().leaveClub(ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID);
			final StravaAthlete[] members = api().listClubMembers(ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID, null, null);
			assertNotNull(members);
		});
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		AthleteDataUtils.validateAthlete(athlete);

	}

	@Override
	protected void validateArray(final StravaAthlete[] athletes) {
		for (final StravaAthlete athlete : athletes) {
			AthleteDataUtils.validateAthlete(athlete);
		}
	}

	@Override
	protected Integer validId() {
		return ClubDataUtils.CLUB_VALID_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

}
