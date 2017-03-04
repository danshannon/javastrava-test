package test.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;
import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityUpdate;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaActivityTest;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.GearDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class UpdateActivityTest {

	/**
	 * @param activity
	 *            The initial activity to create
	 * @param update
	 *            The update to apply
	 * @return The activity as it was created on Strava (although it is ALWAYS deleted again)
	 * @throws Exception
	 *             if not found
	 */
	private StravaActivity createUpdateAndDelete(final StravaActivity activity, final StravaActivityUpdate update)
			throws Exception {
		final StravaActivity response = TestUtils.stravaWithWriteAccess().createManualActivity(activity);
		StravaActivity updateResponse = null;
		try {
			updateResponse = TestUtils.stravaWithFullAccess().updateActivity(response.getId(), update);
			updateResponse = waitForUpdateToComplete(updateResponse.getId());
		} catch (final Exception e) {
			forceDeleteActivity(response);
			throw e;
		}
		forceDeleteActivity(response);
		return updateResponse;
	}

	private void forceDeleteActivity(StravaActivity activity) {
		TestUtils.stravaWithFullAccess().deleteActivity(activity);

	}

	/**
	 * <p>
	 * Test attempting to update an activity using a token that doesn't have write access
	 * </p>
	 *
	 * @throws UnauthorizedException
	 */
	@Test
	public void testUpdateActivity_accessTokenDoesNotHaveWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final TextProducer text = Fairy.create().textProducer();
			final StravaActivity activity = TestUtils.strava().getActivity(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
			activity.setDescription(text.paragraph(1));

			try {
				TestUtils.strava().updateActivity(activity.getId(), new StravaActivityUpdate(activity));
			} catch (final UnauthorizedException e) {
				// Expected behaviour
				return;
			}
			fail("Successfully updated an activity despite not having write access");
		});
	}

	@Test
	public void testUpdateActivity_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = ActivityDataUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_invalidActivity");
			activity.setId(ActivityDataUtils.ACTIVITY_INVALID);

			StravaActivity response = null;
			try {
				response = TestUtils.stravaWithWriteAccess().updateActivity(activity.getId(), new StravaActivityUpdate(activity));
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			assertNull("Updated an activity which doesn't exist?", response);
		});
	}

	@Test
	public void testUpdateActivity_nullUpdate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = TestUtils.stravaWithWriteAccess()
					.updateActivity(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null);
			assertEquals(activity, TestUtils.stravaWithWriteAccess().getActivity(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER));
		});
	}

	@Test
	public void testUpdateActivity_tooManyActivityAttributes() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up the test data
			final StravaActivity activity = ActivityDataUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_tooManyActivityAttributes");
			final StravaActivity update = new StravaActivity();

			final Float cadence = Float.valueOf(67.2f);
			update.setAverageCadence(cadence);

			// Do all the interaction with the Strava API at once
			final StravaActivity stravaResponse = createUpdateAndDelete(activity, new StravaActivityUpdate(update));

			// Test the results
			assertNull(stravaResponse.getAverageCadence());
			StravaActivityTest.validate(stravaResponse);
		});
	}

	@Test
	public void testUpdateActivity_unauthenticatedAthletesActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = TestUtils.stravaWithWriteAccess()
					.getActivity(ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);

			try {
				TestUtils.stravaWithWriteAccess().updateActivity(activity.getId(), new StravaActivityUpdate(activity));
			} catch (final UnauthorizedException e) {
				// Expected behaviour
				return;
			}
			fail("Updated an activity which belongs to someone else??");
		});
	}

	@Test
	public void testUpdateActivity_validUpdateAllAtOnce() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up the test data
			final StravaActivity activity = ActivityDataUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateAllAtOnce");

			final TextProducer text = Fairy.create().textProducer();
			final String description = text.sentence();
			final String name = text.sentence();
			final StravaActivityType type = StravaActivityType.RIDE;
			final Boolean privateActivity = Boolean.FALSE;
			final Boolean commute = Boolean.TRUE;
			final Boolean trainer = Boolean.TRUE;
			final String gearId = GearDataUtils.GEAR_VALID_ID;

			final StravaActivityUpdate update = new StravaActivityUpdate();
			update.setDescription(description);
			update.setCommute(commute);
			update.setGearId(gearId);
			update.setName(name);
			update.setPrivateActivity(privateActivity);
			update.setTrainer(trainer);
			update.setType(type);

			// Do all the interaction with the Strava API at once
			final StravaActivity updateResponse = createUpdateAndDelete(activity, update);

			// Validate the results
			StravaActivityTest.validate(updateResponse);
			assertEquals(description, updateResponse.getDescription());

			assertEquals(commute, updateResponse.getCommute());
			assertEquals(gearId, updateResponse.getGearId());
			assertEquals(name, updateResponse.getName());
			assertEquals(privateActivity, updateResponse.getPrivateActivity());
			assertEquals(trainer, updateResponse.getTrainer());
			assertEquals(type, updateResponse.getType());
		});
	}

	@Test
	public void testUpdateActivity_validUpdateCommute() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up the test data
			final StravaActivity activity = ActivityDataUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateCommute");
			StravaActivity updateResponse = null;

			final StravaActivityUpdate update = new StravaActivityUpdate();
			final Boolean commute = Boolean.TRUE;
			update.setCommute(commute);

			// Do all the interaction with the Strava API at once
			updateResponse = createUpdateAndDelete(activity, update);

			// Validate the results
			StravaActivityTest.validate(updateResponse);
			assertEquals(commute, updateResponse.getCommute());
		});
	}

	@Test
	public void testUpdateActivity_validUpdateDescription() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up test date
			final StravaActivity activity = ActivityDataUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateDescription");

			final TextProducer text = Fairy.create().textProducer();
			final StravaActivityUpdate update = new StravaActivityUpdate();
			final String description = text.sentence();
			update.setDescription(description);

			// Do all the interaction with the Strava API at once
			final StravaActivity response = createUpdateAndDelete(activity, update);

			// Test the response
			StravaActivityTest.validate(response);
			assertEquals(description, response.getDescription());
		});
	}

	@Test
	public void testUpdateActivity_validUpdateGearId() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// set up the test data
			final StravaActivity activity = ActivityDataUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateGearId");
			final StravaActivityUpdate update = new StravaActivityUpdate();
			final String gearId = GearDataUtils.GEAR_VALID_ID;
			update.setGearId(gearId);

			// Do all the Strava API interaction at once
			final StravaActivity response = createUpdateAndDelete(activity, update);

			// Validate the results
			StravaActivityTest.validate(response);
			assertEquals(gearId, response.getGearId());
		});
	}

	@Test
	public void testUpdateActivity_validUpdateGearIDNone() throws Exception {
		RateLimitedTestRunner.run(() -> {

			// Set up all the test data
			final StravaActivity activity = ActivityDataUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateGearIdNone");

			final StravaActivityUpdate update = new StravaActivityUpdate();
			final String gearId = "none";
			update.setGearId(gearId);

			// Do all the Strava API interaction at once
			final StravaActivity response = createUpdateAndDelete(activity, update);

			// Validate the results
			StravaActivityTest.validate(response);
			assertNull(response.getGearId());
		});
	}

	/**
	 * <p>
	 * Test cases: allowed to update the following attributes:
	 * </p>
	 * <ol>
	 * <li>name</li>
	 * <li>type</li>
	 * <li>private</li>
	 * <li>commute</li>
	 * <li>trainer</li>
	 * <li>gear_id (also allows special case of 'none' which should remove the gear)</li>
	 * <li>description</li>
	 * </ol>
	 *
	 * @throws Exception
	 *
	 */
	@Test
	public void testUpdateActivity_validUpdateName() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up the test data
			final StravaActivity activity = ActivityDataUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateName");

			final TextProducer text = Fairy.create().textProducer();

			final StravaActivityUpdate update = new StravaActivityUpdate();
			final String sentence = text.sentence();
			update.setName(sentence);

			// Do all the Strava API interaction at once
			final StravaActivity response = createUpdateAndDelete(activity, update);

			// Validate the results
			StravaActivityTest.validate(response);
			assertEquals(sentence, response.getName());

		});
	}

	@Test
	public void testUpdateActivity_validUpdatePrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// set up the test data
			final StravaActivity activity = ActivityDataUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdatePrivate");

			final StravaActivityUpdate update = new StravaActivityUpdate();
			final Boolean privateFlag = Boolean.TRUE;
			update.setPrivateActivity(privateFlag);

			// Do all the Strava API interaction at once
			final StravaActivity response = createUpdateAndDelete(activity, update);

			// Validate the results
			StravaActivityTest.validate(response);
			assertEquals(privateFlag, response.getPrivateActivity());
		});
	}

	@Test
	public void testUpdateActivity_validUpdatePrivateNoViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = ActivityDataUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdatePrivateNoViewPrivate");
			activity.setPrivateActivity(Boolean.TRUE);

			// Create the activity
			StravaActivity response = TestUtils.stravaWithFullAccess().createManualActivity(activity);
			assertEquals(Boolean.TRUE, response.getPrivateActivity());

			// Try to update it without view private
			activity.setDescription("Updated description");
			try {
				response = TestUtils.stravaWithWriteAccess().updateActivity(response.getId(), new StravaActivityUpdate(activity));
			} catch (final UnauthorizedException e) {
				// expected
				forceDeleteActivity(response);
				return;
			}
			forceDeleteActivity(response);
			fail("Updated private activity without view_private authorisation");

		});
	}

	@Test
	public void testUpdateActivity_validUpdateTrainer() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up the test data
			final StravaActivity activity = ActivityDataUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateTrainer");

			final StravaActivityUpdate update = new StravaActivityUpdate();
			final Boolean trainer = Boolean.TRUE;
			update.setTrainer(trainer);

			// Do all the Strava API interaction at once
			final StravaActivity response = createUpdateAndDelete(activity, update);

			// Validate the results
			StravaActivityTest.validate(response);
			assertEquals(trainer, response.getTrainer());
		});
	}

	@Test
	public void testUpdateActivity_validUpdateType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up the test data
			final StravaActivity activity = ActivityDataUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateType");
			activity.setType(StravaActivityType.ALPINE_SKI);

			final StravaActivityUpdate update = new StravaActivityUpdate();
			final StravaActivityType type = StravaActivityType.RIDE;
			update.setType(type);

			// Do all the Strava API interaction at once
			final StravaActivity response = createUpdateAndDelete(activity, update);

			// Validate the results
			StravaActivityTest.validate(response);
			assertEquals(type, response.getType());
		});
	}

	/**
	 * @param id
	 * @return
	 */
	private StravaActivity waitForUpdateToComplete(final Long id) {
		int i = 0;
		StravaActivity activity = null;
		while (i < 600) {
			activity = TestUtils.stravaWithFullAccess().getActivity(id);
			if (activity.getResourceState() != StravaResourceState.UPDATING) {
				return activity;
			}
			i++;
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				// ignore
			}
		}
		return activity;
	}

}
