package test.api.rest.activity.async;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.rest.AsyncAPITest;
import test.issues.strava.Issue29;
import test.issues.strava.Issue82;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GiveKudosAsyncTest extends AsyncAPITest {

	@Test
	public void testGiveKudos_activityAuthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			apiWithWriteAccess().giveKudos(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);

			// Check that kudos was NOT given
			final StravaAthlete[] kudoers = api().listActivityKudoers(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null, null).get();

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
	public void testGiveKudos_activityInvalid() throws Throwable {
		RateLimitedTestRunner.run(() -> {
			try {
				apiWithWriteAccess().giveKudos(TestUtils.ACTIVITY_INVALID).get();
			} catch (final NotFoundException ex) {
				// Expected behaviour
				return;
			}

			fail("Gave kudos to a non-existent activity");

		});
	}

	@Test
	public void testGiveKudos_activityOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			apiWithWriteAccess().giveKudos(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);

			// Check that kudos is now given
			final StravaAthlete[] kudoers = api().listActivityKudoers(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, null, null).get();

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
				apiWithWriteAccess().giveKudos(TestUtils.ACTIVITY_PRIVATE_OTHER_USER).get();
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
			// TODO This is a workaround for issue javastrava-api #29 (https://github.com/danshannon/javastravav3api/issues/29)
			final Issue29 issue29 = new Issue29();
			if (issue29.isIssue()) {
				return;
			}
			// End of workaround

			try {
				api().giveKudos(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Gave kudos without write access");
		});
	}

	@Test
	public void testGiveKudos_activityPrivateNoViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// TODO This is a workaround for issue #82
			final Issue82 issue = new Issue82();
			if (issue.isIssue()) {
				return;
			}
			// End of workaround

			try {
				apiWithWriteAccess().giveKudos(TestUtils.ACTIVITY_PRIVATE);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Gave kudos to a private activity without view_private access");
		});
	}

}
