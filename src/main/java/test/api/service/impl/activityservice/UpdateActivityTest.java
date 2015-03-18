package test.api.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityUpdate;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.service.ActivityService;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.ActivityServiceImpl;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;
import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.utils.TestUtils;

public class UpdateActivityTest {

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
		final StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setType(StravaActivityType.ALPINE_SKI);

		final TextProducer text = Fairy.create().textProducer();

		StravaActivity response = service().createManualActivity(activity);
		StravaActivityTest.validateActivity(response);

		final StravaActivityUpdate update = new StravaActivityUpdate();
		final String sentence = text.sentence();
		update.setName(sentence);

		response = service().updateActivity(response.getId(), update);
		StravaActivityTest.validateActivity(response);
		assertEquals(sentence, response.getName());

		service().deleteActivity(response.getId());
	}

	@Test
	public void testUpdateActivity_validUpdateType() {
		final StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setType(StravaActivityType.ALPINE_SKI);

		StravaActivity response = service().createManualActivity(activity);
		StravaActivityTest.validateActivity(response);

		final StravaActivityUpdate update = new StravaActivityUpdate();
		final StravaActivityType type = StravaActivityType.RIDE;
		update.setType(type);

		response = service().updateActivity(response.getId(), update);
		StravaActivityTest.validateActivity(response);
		assertEquals(type, response.getType());

		service().deleteActivity(response.getId());
	}

	@Test
	public void testUpdateActivity_validUpdatePrivate() {
		final StravaActivity activity = TestUtils.createDefaultActivity();

		StravaActivity response = service().createManualActivity(activity);
		StravaActivityTest.validateActivity(response);

		final StravaActivityUpdate update = new StravaActivityUpdate();
		final Boolean privateFlag = Boolean.TRUE;
		update.setPrivateActivity(privateFlag);

		response = service().updateActivity(response.getId(), update);
		StravaActivityTest.validateActivity(response);
		assertEquals(privateFlag, response.getPrivateActivity());

		service().deleteActivity(response.getId());
	}

	@Test
	public void testUpdateActivity_validUpdateCommute() {
		final StravaActivity activity = TestUtils.createDefaultActivity();

		StravaActivity response = service().createManualActivity(activity);
		StravaActivityTest.validateActivity(response);

		final StravaActivityUpdate update = new StravaActivityUpdate();
		final Boolean commute = Boolean.TRUE;
		update.setCommute(commute);

		response = service().updateActivity(response.getId(), update);
		StravaActivityTest.validateActivity(response);
		assertEquals(commute, response.getCommute());

		service().deleteActivity(response.getId());
	}

	@Test
	public void testUpdateActivity_validUpdateTrainer() {
		final StravaActivity activity = TestUtils.createDefaultActivity();

		StravaActivity response = service().createManualActivity(activity);
		StravaActivityTest.validateActivity(response);

		final StravaActivityUpdate update = new StravaActivityUpdate();
		final Boolean trainer = Boolean.TRUE;
		update.setTrainer(trainer);

		response = service().updateActivity(response.getId(), update);
		StravaActivityTest.validateActivity(response);
		assertEquals(trainer, response.getTrainer());

		service().deleteActivity(response.getId());
	}

	@Test
	public void testUpdateActivity_validUpdateGearId() {
		final StravaActivity activity = TestUtils.createDefaultActivity();

		StravaActivity response = service().createManualActivity(activity);
		StravaActivityTest.validateActivity(response);

		final StravaActivityUpdate update = new StravaActivityUpdate();
		final String gearId = TestUtils.GEAR_VALID_ID;
		update.setGearId(gearId);

		response = service().updateActivity(response.getId(), update);
		StravaActivityTest.validateActivity(response);
		assertEquals(gearId, response.getGearId());

		service().deleteActivity(response.getId());
	}

	@Test
	public void testUpdateActivity_validUpdateGearIDNone() {
		final StravaActivity activity = TestUtils.createDefaultActivity();

		StravaActivity response = service().createManualActivity(activity);
		StravaActivityTest.validateActivity(response);

		final StravaActivityUpdate update = new StravaActivityUpdate();
		final String gearId = "none";
		update.setGearId(gearId);

		response = service().updateActivity(response.getId(), update);
		StravaActivityTest.validateActivity(response);
		assertNull(response.getGearId());

		service().deleteActivity(response.getId());
	}

	@Test
	public void testUpdateActivity_validUpdateDescription() {
		final StravaActivity activity = TestUtils.createDefaultActivity();

		StravaActivity response = service().createManualActivity(activity);
		StravaActivityTest.validateActivity(response);

		final TextProducer text = Fairy.create().textProducer();
		final StravaActivityUpdate update = new StravaActivityUpdate();
		final String description = text.sentence();
		update.setDescription(description);

		response = service().updateActivity(response.getId(), update);
		StravaActivityTest.validateActivity(response);
		assertEquals(description, response.getDescription());

		service().deleteActivity(response.getId());
	}

	@Test
	public void testUpdateActivity_validUpdateAllAtOnce() {
		final StravaActivity activity = TestUtils.createDefaultActivity();

		StravaActivity response = service().createManualActivity(activity);
		StravaActivityTest.validateActivity(response);

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

		response = service().updateActivity(response.getId(), update);
		StravaActivityTest.validateActivity(response);
		assertEquals(description, response.getDescription());

		assertEquals(commute, response.getCommute());
		assertEquals(gearId, response.getGearId());
		assertEquals(name, response.getName());
		assertEquals(privateActivity, response.getPrivateActivity());
		assertEquals(trainer, response.getTrainer());
		assertEquals(type, response.getType());

		service().deleteActivity(response.getId());
	}

	@Test
	public void testUpdateActivity_tooManyActivityAttributes() {
		final StravaActivity activity = TestUtils.createDefaultActivity();
		activity.setName("testUpdateActivity_tooManyActivityAttributes");
		StravaActivity stravaResponse = service().createManualActivity(activity);

		final Float cadence = new Float(67.2);
		activity.setAverageCadence(cadence);
		activity.setId(stravaResponse.getId());

		stravaResponse = service().updateActivity(stravaResponse.getId(), new StravaActivityUpdate(activity));

		assertNull(stravaResponse.getAverageCadence());
		StravaActivityTest.validateActivity(stravaResponse);

		service().deleteActivity(stravaResponse.getId());
	}

	@Test
	public void testUpdateActivity_nullUpdate() {
		final StravaActivity activity = service().updateActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null);
		assertEquals(activity,service().getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER));
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

	private ActivityService service() {
		return ActivityServiceImpl.instance(TestUtils.getValidToken());
	}

	private ActivityService serviceWithoutWriteAccess() {
		return ActivityServiceImpl.instance(TestUtils.getValidTokenWithoutWriteAccess());
	}

}
