package test.service.impl.activityservice;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

import java.util.List;

import org.junit.Test;

import javastrava.config.JavastravaApplicationConfig;
import javastrava.model.StravaAthlete;
import javastrava.service.exception.NotFoundException;
import javastrava.service.exception.UnauthorizedException;
import test.issues.strava.Issue163;
import test.issues.strava.Issue29;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.AthleteDataUtils;
import test.service.standardtests.spec.PrivacyTests;
import test.service.standardtests.spec.StandardTests;
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
		// Can't run the test unless the application has Strava permission to give kudos
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_GIVE_KUDOS);

		RateLimitedTestRunner.run(() -> {
			try {
				TestUtils.stravaWithWriteAccess().giveKudos(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}

			fail("Succeeded in giving kudos to the athlete's own activity with id " + ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER); //$NON-NLS-1$
		});
	}

	/**
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGiveKudos_activityOtherUser() throws Exception {
		// Can't run the test unless the application has Strava permission to give kudos
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_GIVE_KUDOS);

		RateLimitedTestRunner.run(() -> {
			TestUtils.stravaWithWriteAccess().giveKudos(ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);

			// Check that kudos is now given
			final List<StravaAthlete> kudoers = TestUtils.strava().listActivityKudoers(ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);

			boolean found = false;
			for (final StravaAthlete athlete : kudoers) {
				AthleteDataUtils.validateAthlete(athlete);
				if (athlete.getId().equals(AthleteDataUtils.ATHLETE_AUTHENTICATED_ID)) {
					found = true;
				}
			}
			assertTrue(found);
		});
	}

	/**
	 * @throws Exception
	 *             if the test fails in some unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGiveKudos_noWriteAccess() throws Exception {
		// Can't run the test unless the application has Strava permission to give kudos
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_GIVE_KUDOS);

		// Can't run the test if Strava still allows giving of kudos without write access
		assumeFalse(Issue29.issue);

		RateLimitedTestRunner.run(() -> {
			try {
				TestUtils.strava().giveKudos(ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);
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
	public void testInvalidId() throws Exception {
		// Can't run the test unless the application has Strava permission to give kudos
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_GIVE_KUDOS);

		RateLimitedTestRunner.run(() -> {
			try {
				TestUtils.stravaWithWriteAccess().giveKudos(ActivityDataUtils.ACTIVITY_INVALID);
			} catch (final NotFoundException e) {
				// Expected behaviour
				return;
			}

			fail("Gave kudos to a non-existent activity"); //$NON-NLS-1$

		});
	}

	@Override
	@Test
	public void testPrivateBelongsToOtherUser() throws Exception {
		// Can't run the test unless the application has Strava permission to give kudos
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_GIVE_KUDOS);

		RateLimitedTestRunner.run(() -> {
			try {
				TestUtils.stravaWithWriteAccess().giveKudos(ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Gave kudos to a private activity that belongs to another user"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		// Can't run the test unless the application has Strava permission to give kudos
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_GIVE_KUDOS);

		RateLimitedTestRunner.run(() -> {
			try {
				TestUtils.stravaWithWriteAccess().giveKudos(ActivityDataUtils.ACTIVITY_PRIVATE);
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
		// Can't run the test unless the application has Strava permission to give kudos
		assumeTrue(JavastravaApplicationConfig.STRAVA_ALLOWS_GIVE_KUDOS);

		// Skip the test if issue 163
		assumeFalse(Issue163.issue);

		RateLimitedTestRunner.run(() -> {
			TestUtils.stravaWithFullAccess().giveKudos(ActivityDataUtils.ACTIVITY_PRIVATE);
		});
	}

}
