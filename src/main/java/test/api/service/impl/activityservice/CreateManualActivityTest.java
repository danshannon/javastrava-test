package test.api.service.impl.activityservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;
import test.utils.TestUtils;

public class CreateManualActivityTest extends StravaTest {
	/**
	 * <p>
	 * Attempt to create a valid manual {@link StravaActivity} for the user associated with the security token
	 * </p>
	 *
	 * <p>
	 * Should successfully create the activity, and the activity should be retrievable immediately and identical to the one used to create
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testCreateManualActivity_validActivity() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				StravaActivity activity = TestUtils.createDefaultActivity();
				activity.setName("testCreateManualActivity_validActivity");
				activity = service().createManualActivity(TestUtils.createDefaultActivity());

				assertNotNull(activity);

				// Load it from Strava
				final StravaActivity stravaActivity = service().getActivity(activity.getId());
				assertNotNull(stravaActivity);
				StravaActivityTest.validateActivity(stravaActivity, stravaActivity.getId(), stravaActivity.getResourceState());

				// And delete it
				service().deleteActivity(activity.getId());
			}
		});
	}

	/**
	 * <p>
	 * Attempt to create a valid manual {@link StravaActivity} for the user associated with the security token, where the user has NOT granted write access via
	 * the OAuth process
	 * </p>
	 *
	 * <p>
	 * Should fail to create the activity and throw an {@link UnauthorizedException}, which is trapped in the test because it it expected
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 */
	@Test
	public void testCreateManualActivity_accessTokenDoesNotHaveWriteAccess() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				StravaActivity activity = null;
				try {
					activity = TestUtils.createDefaultActivity();
					activity.setName("testCreateManualActivity_accessTokenDoesNotHaveWriteAccess");
					activity = serviceWithoutWriteAccess().createManualActivity(activity);
				} catch (final UnauthorizedException e) {
					// This is the expected behaviour - creation has failed because there's no write access
					return;
				} catch (final Throwable e) {
					service().deleteActivity(activity.getId());
				}

				service().deleteActivity(activity.getId());
				fail("Created a manual activity but should have failed and thrown an UnauthorizedException!");
			}
		});
	}

	/**
	 * <p>
	 * Attempt to create an incomplete manual {@link StravaActivity} for the user where not all required attributes are set
	 * </p>
	 *
	 * <p>
	 * Should fail to create the activity in each case where a required attribute is missing
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 */
	@Test
	public void testCreateManualActivity_noName() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				// Name is required
				final StravaActivity activity = TestUtils.createDefaultActivity();
				StravaActivity stravaResponse = null;
				activity.setName(null);
				activity.setDescription("testCreateManualActivity_noName");
				try {
					stravaResponse = service().createManualActivity(activity);
				} catch (final IllegalArgumentException e) {
					// Expected behaviour
					return;
				} catch (final Exception e) {
					// Ignore ALL other exceptions
				}

				// If it did get created, delete it again
				if (stravaResponse != null) {
					service().deleteActivity(stravaResponse.getId());
				}

				fail("Created an activity with no type in error" + stravaResponse);
			}
		});
	}

	@Test
	public void testCreateManualActivity_noType() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				// Type is required
				final StravaActivity activity = TestUtils.createDefaultActivity();
				StravaActivity stravaResponse = null;
				activity.setType(null);
				activity.setName("testCreateManualActivity_noType");
				try {
					stravaResponse = service().createManualActivity(activity);
				} catch (final IllegalArgumentException e) {
					// Expected behaviour
					return;
				} catch (final Exception e) {
					// Ignore ALL other exceptions
				}

				// If it did get created, delete it again
				if (stravaResponse != null) {
					service().deleteActivity(stravaResponse.getId());
				}

				fail("Created an activity with no type in error" + stravaResponse);
			}
		});
	}

	@Test
	public void testCreateManualActivity_invalidType() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				// Type must be one of the specified values
				final StravaActivity activity = TestUtils.createDefaultActivity();
				StravaActivity stravaResponse = null;
				activity.setName("testCreateManualActivity_invalidType");
				activity.setType(StravaActivityType.UNKNOWN);
				try {
					stravaResponse = service().createManualActivity(activity);
				} catch (final IllegalArgumentException e) {
					// Expected behaviour
					return;
				} catch (final Exception e) {
					// Ignore ALL other exceptions
				}

				// If it did get created, delete it again
				if (stravaResponse != null) {
					service().deleteActivity(stravaResponse.getId());
				}

				fail("Created an activity with invalid type in error");
			}
		});
	}

	@Test
	public void testCreateManualActivity_noStartDate() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final StravaActivity activity = TestUtils.createDefaultActivity();
				StravaActivity stravaResponse = null;
				// Start date is required
				activity.setName("testCreateManualActivity_noStartDate");
				activity.setStartDateLocal(null);
				try {
					stravaResponse = service().createManualActivity(activity);
				} catch (final IllegalArgumentException e) {
					// Expected behaviour
					return;
				} catch (final Exception e) {
					// Ignore ALL other exceptions
				}

				// If it did get created, delete it again
				if (stravaResponse != null) {
					service().deleteActivity(stravaResponse.getId());
				}

				fail("Created an activity with no start date in error" + stravaResponse);
			}
		});
	}

	@Test
	public void testCreateManualActivity_noElapsedTime() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final StravaActivity activity = TestUtils.createDefaultActivity();
				StravaActivity stravaResponse = null;
				// Elapsed time is required
				activity.setName("testCreateManualActivity_noElapsedTime");
				activity.setElapsedTime(null);
				try {
					stravaResponse = service().createManualActivity(activity);
				} catch (final IllegalArgumentException e) {
					// Expected behaviour
					return;
				} catch (final Exception e) {
					// Ignore ALL other exceptions
				}

				// If it did get created, delete it again
				if (stravaResponse != null) {
					service().deleteActivity(stravaResponse.getId());
				}

				fail("Created an activity with no elapsed time in error" + stravaResponse);
			}
		});
	}

}
