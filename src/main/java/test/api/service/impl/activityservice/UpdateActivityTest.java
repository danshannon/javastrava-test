package test.api.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityUpdate;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;
import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.service.StravaTest;
import test.utils.TestUtils;

public class UpdateActivityTest extends StravaTest {

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
	 */
	@Test
	public void testUpdateActivity_validUpdateName() {
		// Set up the test data
		final StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setType(StravaActivityType.ALPINE_SKI);

		final TextProducer text = Fairy.create().textProducer();

		final StravaActivityUpdate update = new StravaActivityUpdate();
		final String sentence = text.sentence();
		update.setName(sentence);

		// Do all the Strava API interaction at once
		final StravaActivity response = createUpdateAndDelete(activity, update);

		// Validate the results
		StravaActivityTest.validateActivity(response);
		assertEquals(sentence, response.getName());

	}

	@Test
	public void testUpdateActivity_validUpdateType() {
		// Set up the test data
		final StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setType(StravaActivityType.ALPINE_SKI);

		final StravaActivityUpdate update = new StravaActivityUpdate();
		final StravaActivityType type = StravaActivityType.RIDE;
		update.setType(type);

		// Do all the Strava API interaction at once
		final StravaActivity response = createUpdateAndDelete(activity, update);

		// Validate the results
		StravaActivityTest.validateActivity(response);
		assertEquals(type, response.getType());
	}

	@Test
	public void testUpdateActivity_validUpdatePrivate() {
		// set up the test data
		final StravaActivity activity = TestUtils.createDefaultActivity();

		final StravaActivityUpdate update = new StravaActivityUpdate();
		final Boolean privateFlag = Boolean.TRUE;
		update.setPrivateActivity(privateFlag);

		// Do all the Strava API interaction at once
		final StravaActivity response = createUpdateAndDelete(activity, update);

		// Validate the results
		StravaActivityTest.validateActivity(response);
		assertEquals(privateFlag, response.getPrivateActivity());
	}

	@Test
	public void testUpdateActivity_validUpdateCommute() {
		// Set up the test data
		final StravaActivity activity = TestUtils.createDefaultActivity();
		StravaActivity updateResponse = null;

		final StravaActivityUpdate update = new StravaActivityUpdate();
		final Boolean commute = Boolean.TRUE;
		update.setCommute(commute);

		// Do all the interaction with the Strava API at once
		updateResponse = createUpdateAndDelete(activity, update);

		// Validate the results
		StravaActivityTest.validateActivity(updateResponse);
		assertEquals(commute, updateResponse.getCommute());

	}

	/**
	 * @param activity
	 *            The initial activity to create
	 * @param update
	 *            The update to apply
	 * @return The activity as it was created on Strava (although it is ALWAYS deleted again)
	 */
	private StravaActivity createUpdateAndDelete(final StravaActivity activity, final StravaActivityUpdate update) {
		final StravaActivity response = service().createManualActivity(activity);
		StravaActivity updateResponse = null;
		try {
			updateResponse = service().updateActivity(response.getId(), update);
		} catch (final Throwable e) {
			service().deleteActivity(response.getId());
			throw e;
		}
		service().deleteActivity(response.getId());
		return updateResponse;
	}

	@Test
	public void testUpdateActivity_validUpdateTrainer() {
		// Set up the test data
		final StravaActivity activity = TestUtils.createDefaultActivity();

		final StravaActivityUpdate update = new StravaActivityUpdate();
		final Boolean trainer = Boolean.TRUE;
		update.setTrainer(trainer);

		// Do all the Strava API interaction at once
		final StravaActivity response = createUpdateAndDelete(activity, update);

		// Validate the results
		StravaActivityTest.validateActivity(response);
		assertEquals(trainer, response.getTrainer());
	}

	@Test
	public void testUpdateActivity_validUpdateGearId() {
		// set up the test data
		final StravaActivity activity = TestUtils.createDefaultActivity();
		final StravaActivityUpdate update = new StravaActivityUpdate();
		final String gearId = TestUtils.GEAR_VALID_ID;
		update.setGearId(gearId);

		// Do all the Strava API interaction at once
		final StravaActivity response = createUpdateAndDelete(activity, update);

		// Validate the results
		StravaActivityTest.validateActivity(response);
		assertEquals(gearId, response.getGearId());
	}

	@Test
	public void testUpdateActivity_validUpdateGearIDNone() {

		// Set up all the test data
		final StravaActivity activity = TestUtils.createDefaultActivity();

		final StravaActivityUpdate update = new StravaActivityUpdate();
		final String gearId = "none";
		update.setGearId(gearId);

		// Do all the Strava API interaction at once
		final StravaActivity response = createUpdateAndDelete(activity, update);

		// Validate the results
		StravaActivityTest.validateActivity(response);
		assertNull(response.getGearId());

	}

	@Test
	public void testUpdateActivity_validUpdateDescription() {
		// Set up test date
		final StravaActivity activity = TestUtils.createDefaultActivity();

		final TextProducer text = Fairy.create().textProducer();
		final StravaActivityUpdate update = new StravaActivityUpdate();
		final String description = text.sentence();
		update.setDescription(description);

		// Do all the interaction with the Strava API at once
		final StravaActivity response = createUpdateAndDelete(activity, update);

		// Test the response
		StravaActivityTest.validateActivity(response);
		assertEquals(description, response.getDescription());

	}

	@Test
	public void testUpdateActivity_validUpdateAllAtOnce() {
		// Set up the test data
		final StravaActivity activity = TestUtils.createDefaultActivity();

		final TextProducer text = Fairy.create().textProducer();
		final String description = text.sentence();
		final String name = text.sentence();
		final StravaActivityType type = StravaActivityType.RIDE;
		final Boolean privateActivity = Boolean.TRUE;
		final Boolean commute = Boolean.TRUE;
		final Boolean trainer = Boolean.TRUE;
		final String gearId = TestUtils.GEAR_VALID_ID;

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
		StravaActivityTest.validateActivity(updateResponse);
		assertEquals(description, updateResponse.getDescription());

		assertEquals(commute, updateResponse.getCommute());
		assertEquals(gearId, updateResponse.getGearId());
		assertEquals(name, updateResponse.getName());
		assertEquals(privateActivity, updateResponse.getPrivateActivity());
		assertEquals(trainer, updateResponse.getTrainer());
		assertEquals(type, updateResponse.getType());

	}

	@Test
	public void testUpdateActivity_tooManyActivityAttributes() {
		// Set up the test data
		final StravaActivity activity = TestUtils.createDefaultActivity();
		final StravaActivity update = new StravaActivity();
		update.setName("testUpdateActivity_tooManyActivityAttributes");

		final Float cadence = Float.valueOf(67.2f);
		update.setAverageCadence(cadence);

		// Do all the interaction with the Strava API at once
		final StravaActivity stravaResponse = createUpdateAndDelete(activity, new StravaActivityUpdate(update));

		// Test the results
		assertNull(stravaResponse.getAverageCadence());
		StravaActivityTest.validateActivity(stravaResponse);

	}

	@Test
	public void testUpdateActivity_nullUpdate() {
		final StravaActivity activity = service().updateActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null);
		assertEquals(activity, service().getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER));
	}

	@Test
	public void testUpdateActivity_invalidActivity() {
		final StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setId(TestUtils.ACTIVITY_INVALID);

		final StravaActivity response = service().updateActivity(activity.getId(), new StravaActivityUpdate(activity));
		assertNull("Updated an activity which doesn't exist?", response);
	}

	@Test
	public void testUpdateActivity_unauthenticatedAthletesActivity() {
		final StravaActivity activity = service().getActivity(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);

		try {
			service().updateActivity(activity.getId(), new StravaActivityUpdate(activity));
		} catch (final UnauthorizedException e) {
			// Expected behaviour
			return;
		}
		fail("Updated an activity which belongs to someone else??");
	}

	/**
	 * <p>
	 * Test attempting to update an activity using a token that doesn't have write access
	 * </p>
	 *
	 * @throws UnauthorizedException
	 */
	@Test
	public void testUpdateActivity_accessTokenDoesNotHaveWriteAccess() {
		final TextProducer text = Fairy.create().textProducer();
		final StravaActivity activity = serviceWithoutWriteAccess().getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		activity.setDescription(text.paragraph(1));

		try {
			serviceWithoutWriteAccess().updateActivity(activity.getId(), new StravaActivityUpdate(activity));
		} catch (final UnauthorizedException e) {
			// Expected behaviour
			return;
		}
		fail("Successfully updated an activity despite not having write access");
	}

}
