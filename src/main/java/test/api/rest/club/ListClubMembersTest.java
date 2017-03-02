package test.api.rest.club;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaAthleteTest;
import test.api.rest.APIPagingListTest;
import test.api.rest.TestListArrayCallback;
import test.api.rest.util.ArrayCallback;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListClubMembersTest extends APIPagingListTest<StravaAthlete, Integer> {
	/**
	 * @see test.api.rest.APIPagingListTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaAthlete> pagingCallback() {
		return paging -> api().listClubMembers(validId(), paging.getPage(), paging.getPageSize());
	}

	/**
	 * @see test.api.rest.APIListTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaAthlete, Integer> listCallback() {
		return (api, id) -> api.listClubMembers(id, null, null);
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return TestUtils.CLUB_INVALID_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
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
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned list of members for a club of which the authenticated athlete is not a member!");
		});
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		StravaAthleteTest.validateAthlete(athlete);

	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaAthlete[] list) {
		StravaAthleteTest.validateList(Arrays.asList(list));

	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return TestUtils.CLUB_VALID_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

}
