package test.api.service.impl.activityservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaAthleteTest;
import test.api.service.standardtests.spec.PrivacyTests;
import test.api.service.standardtests.spec.StandardTests;
import test.issues.strava.Issue29;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for giving kudos to activities
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GiveKudosTest implements PrivacyTests, StandardTests {

	/**
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGiveKudos_activityAuthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			TestUtils.stravaWithWriteAccess().giveKudos(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);

			// Check that kudos was NOT given
			final List<StravaAthlete> kudoers = TestUtils.strava().listActivityKudoers(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);

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

	@Override
	@Test
	public void testInvalidId() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				TestUtils.stravaWithWriteAccess().giveKudos(TestUtils.ACTIVITY_INVALID);
			} catch (final NotFoundException e) {
				// Expected behaviour
				return;
			}

			fail("Gave kudos to a non-existent activity"); //$NON-NLS-1$

		});
	}

	/**
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGiveKudos_activityOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			TestUtils.stravaWithWriteAccess().giveKudos(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);

			// Check that kudos is now given
			final List<StravaAthlete> kudoers = TestUtils.strava().listActivityKudoers(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);

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

	@Override
	@Test
	public void testPrivateBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				TestUtils.stravaWithWriteAccess().giveKudos(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Gave kudos to a private activity that belongs to another user"); //$NON-NLS-1$
		});
	}

	/**
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGiveKudos_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				TestUtils.strava().giveKudos(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			// TODO This is a workaround for issue javastrava-api #29 (https://github.com/danshannon/javastravav3api/issues/29)
			if (!(new Issue29().isIssue())) {
				fail("Gave kudos without write access"); //$NON-NLS-1$
			}
		});
	}

	@Override
	@Test
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				TestUtils.stravaWithWriteAccess().giveKudos(TestUtils.ACTIVITY_PRIVATE);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Gave kudos to a private activity without view_private access"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testPrivateWithViewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			TestUtils.stravaWithFullAccess().giveKudos(TestUtils.ACTIVITY_PRIVATE);
		});
	}

}
