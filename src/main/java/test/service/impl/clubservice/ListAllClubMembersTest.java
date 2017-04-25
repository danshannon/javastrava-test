package test.service.impl.clubservice;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import javastrava.model.StravaAthlete;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for listAllClubMembers methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListAllClubMembersTest extends ListMethodTest<StravaAthlete, Integer> {
	@Override
	protected Class<StravaAthlete> classUnderTest() {
		return StravaAthlete.class;

	}

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
		return ((strava, id) -> strava.listAllClubMembers(id));
	}

	/**
	 * <p>
	 * Check can list all members of a private club that the authenticated user is a member of
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAllClubMembers_privateMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> athletes = TestUtils.strava().listAllClubMembers(ClubDataUtils.CLUB_PRIVATE_MEMBER_ID);
			assertNotNull(athletes);
			for (final StravaAthlete athlete : athletes) {
				AthleteDataUtils.validateAthlete(athlete);
			}
		});
	}

	/**
	 * <p>
	 * Check CANNOT list all members of a private club that the authenticated user is NOT a member of
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAllClubMembers_privateNonMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> athletes = TestUtils.strava().listAllClubMembers(ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID);
			assertNotNull(athletes);
		});
	}

	/**
	 * <p>
	 * Check can list all members of a public club that the authenticated user is NOT a member of
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testListAllClubMembers_publicNonMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaAthlete> athletes = TestUtils.strava().listAllClubMembers(ClubDataUtils.CLUB_PUBLIC_NON_MEMBER_ID);
			assertNotNull(athletes);
			for (final StravaAthlete athlete : athletes) {
				AthleteDataUtils.validateAthlete(athlete);
			}
		});
	}

	@Override
	protected void validate(StravaAthlete athlete) {
		AthleteDataUtils.validateAthlete(athlete);
	}

}
