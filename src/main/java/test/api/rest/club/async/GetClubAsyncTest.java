package test.api.rest.club.async;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.rest.callback.APIGetCallback;
import test.api.rest.club.GetClubTest;
import test.service.standardtests.data.ClubDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific tests and config for {@link API#getClubAsync(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetClubAsyncTest extends GetClubTest {
	@Override
	protected APIGetCallback<StravaClub, Integer> getter() {
		return ((api, id) -> api.getClubAsync(id).get());
	}

	@Override
	@Test
	public void testGetClub_privateClubIsMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaClub club = api().getClub(ClubDataUtils.CLUB_PRIVATE_MEMBER_ID);
			assertNotNull(club);
			ClubDataUtils.validate(club, ClubDataUtils.CLUB_PRIVATE_MEMBER_ID, club.getResourceState());
		});
	}

	@Override
	@Test
	public void testGetClub_privateClubIsNotMember() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getClub(ClubDataUtils.CLUB_PRIVATE_NON_MEMBER_ID);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned details of a private club of which the authenticated athlete is not a member"); //$NON-NLS-1$
		});
	}

}
