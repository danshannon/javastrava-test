package test.api.service.impl.activityservice.async;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GiveKudosAsyncTest extends StravaTest {

	@Test
	public void testGiveKudos_activityAuthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			stravaWithWriteAccess().giveKudos(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);

			// Check that kudos was NOT given
				final List<StravaAthlete> kudoers = strava().listActivityKudoersAsync(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER).get();

				boolean found = false;
				for (final StravaAthlete athlete : kudoers) {
					StravaAthleteTest.validateAthlete(athlete);
					if (athlete.getId().equals(TestUtils.ATHLETE_AUTHENTICATED_ID)) {
						found = true;
					}
				}
				assertFalse(found);
			});
	}

	@Test
	public void testGiveKudos_activityInvalid() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				stravaWithWriteAccess().giveKudosAsync(TestUtils.ACTIVITY_INVALID).get();
			} catch (final NotFoundException e) {
				// Expected behaviour
				return;
			}

			fail("Gave kudos to a non-existent activity");

		});
	}

	@Test
	public void testGiveKudos_activityOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			stravaWithWriteAccess().giveKudos(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);

			// Check that kudos is now given
				final List<StravaAthlete> kudoers = strava().listActivityKudoersAsync(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER).get();

				boolean found = false;
				for (final StravaAthlete athlete : kudoers) {
					StravaAthleteTest.validateAthlete(athlete);
					if (athlete.getId().equals(TestUtils.ATHLETE_AUTHENTICATED_ID)) {
						found = true;
					}
				}
				assertTrue(found);
			});
	}

	@Test
	public void testGiveKudos_activityPrivateOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				stravaWithWriteAccess().giveKudosAsync(TestUtils.ACTIVITY_PRIVATE_OTHER_USER).get();
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Gave kudos to a private activity that belongs to another user");
		});
	}

	@Test
	public void testGiveKudos_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				strava().giveKudosAsync(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER).get();
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			// TODO This is a workaround for issue javastrava-api #29 (https://github.com/danshannon/javastravav3api/issues/29)
			// fail("Gave kudos without write access");
		});
	}

	@Test
	public void testGiveKudos_activityPrivateNoViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				stravaWithWriteAccess().giveKudosAsync(TestUtils.ACTIVITY_PRIVATE).get();
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Gave kudos to a private activity without view_private access");
		});
	}
}
