package test.api.service.impl.clubservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.api.model.StravaClubTest;
import test.api.service.standardtests.GetMethodTest;
import test.api.service.standardtests.callbacks.GetCallback;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for get club methods
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class GetClubTest extends GetMethodTest<StravaClub, Integer> {
	/**
	 * <p>
	 * Private club of which current authenticated athlete is a member
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetClub_privateClubIsMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaClub club = TestUtils.strava().getClub(TestUtils.CLUB_PRIVATE_MEMBER_ID);
			assertNotNull(club);
			StravaClubTest.validate(club, TestUtils.CLUB_PRIVATE_MEMBER_ID, club.getResourceState());
		});
	}

	/**
	 * <p>
	 * Private club of which current authenticated athlete is NOT a member
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	// 4. Private club of which current authenticated athlete is NOT a member
	@Test
	public void testGetClub_privateClubIsNotMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaClub club = TestUtils.strava().getClub(TestUtils.CLUB_PRIVATE_NON_MEMBER_ID);
			final StravaClub comparison = new StravaClub();
			comparison.setId(TestUtils.CLUB_PRIVATE_NON_MEMBER_ID);
			comparison.setResourceState(StravaResourceState.PRIVATE);
			assertNotNull(club);
			assertEquals(comparison, club);
			StravaClubTest.validate(club, TestUtils.CLUB_PRIVATE_NON_MEMBER_ID, club.getResourceState());
		});
	}

	@Override
	protected Integer getIdValid() {
		return TestUtils.CLUB_VALID_ID;
	}

	@Override
	protected Integer getIdInvalid() {
		return TestUtils.CLUB_INVALID_ID;
	}

	@Override
	protected Integer getIdPrivate() {
		return TestUtils.CLUB_PRIVATE_MEMBER_ID;
	}

	@Override
	protected Integer getIdPrivateBelongsToOtherUser() {
		return TestUtils.CLUB_PRIVATE_NON_MEMBER_ID;
	}

	@Override
	protected GetCallback<StravaClub, Integer> getter() throws Exception {
		return ((strava, id) -> strava.getClub(id));
	}

	@Override
	protected void validate(StravaClub club) {
		StravaClubTest.validate(club);

	}

}
