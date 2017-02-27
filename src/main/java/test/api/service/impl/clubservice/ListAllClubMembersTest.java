package test.api.service.impl.clubservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import test.api.model.StravaAthleteTest;
import test.api.service.standardtests.ListMethodTest;
import test.api.service.standardtests.callbacks.ListCallback;
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
			final List<StravaAthlete> athletes = TestUtils.strava().listAllClubMembers(TestUtils.CLUB_PRIVATE_MEMBER_ID);
			assertNotNull(athletes);
			for (final StravaAthlete athlete : athletes) {
				StravaAthleteTest.validateAthlete(athlete);
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
			final List<StravaAthlete> athletes = TestUtils.strava().listAllClubMembers(TestUtils.CLUB_PRIVATE_NON_MEMBER_ID);
			assertNotNull(athletes);
			assertEquals(0, athletes.size());
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
			final List<StravaAthlete> athletes = TestUtils.strava().listAllClubMembers(TestUtils.CLUB_PUBLIC_NON_MEMBER_ID);
			assertNotNull(athletes);
			for (final StravaAthlete athlete : athletes) {
				StravaAthleteTest.validateAthlete(athlete);
			}
		});
	}

	@Override
	protected ListCallback<StravaAthlete, Integer> lister() {
		return ((strava, id) -> strava.listAllClubMembers(id));
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
		return TestUtils.CLUB_VALID_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected Integer idInvalid() {
		return TestUtils.CLUB_INVALID_ID;
	}

	@Override
	protected void validate(StravaAthlete athlete) {
		StravaAthleteTest.validateAthlete(athlete);
	}

}
