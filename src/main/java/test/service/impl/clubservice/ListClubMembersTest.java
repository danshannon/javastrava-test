package test.service.impl.clubservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import test.service.standardtests.PagingListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.callbacks.PagingListCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for listClubMembers methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListClubMembersTest extends PagingListMethodTest<StravaAthlete, Integer> {
	@Override
	protected Integer idInvalid() {
		return ClubDataUtils.CLUB_INVALID_ID;
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer idValidWithEntries() {
		return ClubDataUtils.CLUB_VALID_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected ListCallback<StravaAthlete, Integer> lister() {
		return ((strava, id) -> strava.listClubMembers(id));
	}

	@Override
	protected PagingListCallback<StravaAthlete, Integer> pagingLister() {
		return ((strava, paging, id) -> strava.listClubMembers(id, paging));
	}

	/**
	 * <p>
	 * Private club of which current authenticated athlete is a member
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@Test
	public void testListClubMembers_privateClubIsMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> members = TestUtils.strava().listClubMembers(ClubDataUtils.CLUB_PRIVATE_MEMBER_ID);
			assertNotNull(members);
			assertFalse(members.size() == 0);
			for (final StravaAthlete athlete : members) {
				validate(athlete);
			}
		});
	}

	/**
	 * <p>
	 * Private club of which current authenticated athlete is NOT a member
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListClubMembers_privateClubNotMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> members = TestUtils.strava().listClubMembers(ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID);
			assertNotNull(members);
		});
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		AthleteDataUtils.validateAthlete(athlete);
	}

}
