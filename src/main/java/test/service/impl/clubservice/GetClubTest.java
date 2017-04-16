package test.service.impl.clubservice;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.StravaClub;
import test.service.standardtests.GetMethodTest;
import test.service.standardtests.callbacks.GetCallback;
import test.service.standardtests.data.ClubDataUtils;
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
	@Override
	protected Integer getIdInvalid() {
		return ClubDataUtils.CLUB_INVALID_ID;
	}

	@Override
	protected Integer getIdPrivate() {
		return null;
	}

	@Override
	protected Integer getIdPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer getIdValid() {
		return ClubDataUtils.CLUB_VALID_ID;
	}

	@Override
	protected GetCallback<StravaClub, Integer> getter() {
		return ((strava, id) -> strava.getClub(id));
	}

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
			final StravaClub club = TestUtils.strava().getClub(ClubDataUtils.CLUB_PRIVATE_MEMBER_ID);
			assertNotNull(club);
			ClubDataUtils.validate(club, ClubDataUtils.CLUB_PRIVATE_MEMBER_ID, club.getResourceState());
		});
	}

	@Override
	protected void validate(StravaClub club) {
		ClubDataUtils.validate(club);

	}

}
