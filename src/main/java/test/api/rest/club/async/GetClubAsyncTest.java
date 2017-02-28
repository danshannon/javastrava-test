package test.api.rest.club.async;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaClubTest;
import test.api.rest.TestGetCallback;
import test.api.rest.club.GetClubTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetClubAsyncTest extends GetClubTest {
	@Override
	protected TestGetCallback<StravaClub, Integer> getCallback() {
		return ((api, id) -> api.getClubAsync(id).get());
	}

	// 3. Private club of which current authenticated athlete is a member
	@Override
	@Test
	public void testGetClub_privateClubIsMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaClub club = api().getClub(TestUtils.CLUB_PRIVATE_MEMBER_ID);
			assertNotNull(club);
			StravaClubTest.validate(club, TestUtils.CLUB_PRIVATE_MEMBER_ID, club.getResourceState());
		});
	}

	// 4. Private club of which current authenticated athlete is NOT a member
	@Override
	@Test
	public void testGetClub_privateClubIsNotMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getClub(TestUtils.CLUB_PRIVATE_NON_MEMBER_ID);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned details of a private club of which the authenticated athlete is not a member");
		});
	}

}
