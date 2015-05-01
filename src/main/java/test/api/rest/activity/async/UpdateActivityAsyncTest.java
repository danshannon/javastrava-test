package test.api.rest.activity.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityUpdate;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.StravaUnknownAPIException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaActivityTest;
import test.api.rest.activity.UpdateActivityTest;
import test.issues.strava.Issue36;
import test.issues.strava.Issue72;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class UpdateActivityAsyncTest extends UpdateActivityTest {

	/**
	 * @param activity
	 *            The initial activity to create
	 * @param update
	 *            The update to apply
	 * @return The activity as it was created on Strava (although it is ALWAYS
	 *         deleted again)
	 * @throws Exception
	 *             if not found
	 */
<<<<<<< HEAD
	@Override
	protected StravaActivity createUpdateAndDelete(final StravaActivity activity, final StravaActivityUpdate update)
			throws Exception {
=======
	private StravaActivity createUpdateAndDelete(final StravaActivity activity, final StravaActivityUpdate update) throws Exception {
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
		final StravaActivity response = apiWithFullAccess().createManualActivityAsync(activity).get();
		StravaActivity updateResponse = null;
		try {
			updateResponse = apiWithFullAccess().updateActivityAsync(response.getId(), update).get();
		} catch (final Exception e) {
			forceDeleteActivity(response);
			throw e;
		}
		updateResponse = waitForUpdateCompletion(updateResponse);
		forceDeleteActivity(response);
		return updateResponse;
	}

	/**
<<<<<<< HEAD
=======
	 * @param updateResponse
	 * @return
	 */
	private StravaActivity waitForUpdateCompletion(final StravaActivity updateResponse) throws Exception {
		int i = 0;
		StravaActivity response = null;
		while (i < 600) {
			response = apiWithFullAccess().getActivityAsync(updateResponse.getId(), null).get();
			i++;
			if (response.getResourceState() != StravaResourceState.UPDATING) {
				return response;
			}
		}
		return response;
	}

	/**
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	 * <p>
	 * Test attempting to update an activity using a token that doesn't have
	 * write access
	 * </p>
	 *
	 * @throws UnauthorizedException
	 */
	@Override
	public void testUpdateActivity_accessTokenDoesNotHaveWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final TextProducer text = Fairy.create().textProducer();
<<<<<<< HEAD
			final StravaActivity activity = api().getActivityAsync(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null)
					.get();
=======
			final StravaActivity activity = api().getActivityAsync(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null).get();
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
			activity.setDescription(text.paragraph(1));

			try {
				api().updateActivityAsync(activity.getId(), new StravaActivityUpdate(activity)).get();
			} catch (final UnauthorizedException e) {
				// Expected behaviour
				return;
			}
			fail("Successfully updated an activity despite not having write access");
		} );
	}

	@Override
<<<<<<< HEAD
	@Test
=======
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	public void testUpdateActivity_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = TestUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_invalidActivity");
			activity.setId(TestUtils.ACTIVITY_INVALID);

			StravaActivity response = null;
			try {
<<<<<<< HEAD
				response = apiWithWriteAccess()
						.updateActivityAsync(activity.getId(), new StravaActivityUpdate(activity)).get();
=======
				response = apiWithWriteAccess().updateActivityAsync(activity.getId(), new StravaActivityUpdate(activity)).get();
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			assertNull("Updated an activity which doesn't exist?", response);
		} );
	}

	@Override
<<<<<<< HEAD
	@Test
=======
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	public void testUpdateActivity_nullUpdate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				apiWithWriteAccess().updateActivityAsync(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null).get();
			} catch (final StravaUnknownAPIException e) {
				// Expected
				return;
			}
			fail("Updated an activity with a null update");
		} );
	}

	@Override
<<<<<<< HEAD
	@Test
=======
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	public void testUpdateActivity_tooManyActivityAttributes() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up the test data
<<<<<<< HEAD
			final StravaActivity activity = TestUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_tooManyActivityAttributes");
			final StravaActivity update = new StravaActivity();
=======
				final StravaActivity activity = TestUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_tooManyActivityAttributes");
				final StravaActivity update = new StravaActivity();
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git

				final Float cadence = Float.valueOf(67.2f);
				update.setAverageCadence(cadence);

				// Do all the interaction with the Strava API at once
				final StravaActivity stravaResponse = createUpdateAndDelete(activity, new StravaActivityUpdate(update));

<<<<<<< HEAD
			// Test the results
			assertNull(stravaResponse.getAverageCadence());
			StravaActivityTest.validateActivity(stravaResponse);
		} );
=======
				// Test the results
				assertNull(stravaResponse.getAverageCadence());
				validate(stravaResponse);
			});
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	}

	@Override
<<<<<<< HEAD
	@Test
=======
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	public void testUpdateActivity_unauthenticatedAthletesActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
<<<<<<< HEAD
			final StravaActivity activity = apiWithWriteAccess()
					.getActivityAsync(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, null).get();
=======
			final StravaActivity activity = apiWithWriteAccess().getActivityAsync(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, null).get();
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git

			try {
				apiWithWriteAccess().updateActivityAsync(activity.getId(), new StravaActivityUpdate(activity)).get();
			} catch (final UnauthorizedException e) {
				// Expected behaviour
				return;
			}
			fail("Updated an activity which belongs to someone else??");
		} );
	}

	@Override
<<<<<<< HEAD
	@Test
=======
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	public void testUpdateActivity_validUpdateAllAtOnce() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up the test data
<<<<<<< HEAD
			final StravaActivity activity = TestUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateAllAtOnce");
=======
				final StravaActivity activity = TestUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateAllAtOnce");
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git

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
				validate(updateResponse);
				assertEquals(description, updateResponse.getDescription());

				assertEquals(gearId, updateResponse.getGearId());
				assertEquals(name, updateResponse.getName());
				assertEquals(privateActivity, updateResponse.getPrivateActivity());
				assertEquals(trainer, updateResponse.getTrainer());
				assertEquals(type, updateResponse.getType());

<<<<<<< HEAD
			// TODO This is a workaround for javastravav3api#36
			// When issue fixed, restore the assertions to normal code
			if (!new Issue36().isIssue()) {
				assertEquals(commute, updateResponse.getCommute());
			}
			// End of workaround
		} );
=======
				// TODO This is a workaround for javastravav3api#36
				// When issue fixed, restore the assertions to normal code
				if (!new Issue36().isIssue()) {
					assertEquals(commute, updateResponse.getCommute());
				}
				// End of workaround
		});
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	}

	@Override
<<<<<<< HEAD
	@Test
=======
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	public void testUpdateActivity_validUpdateCommute() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up the test data
<<<<<<< HEAD
			final StravaActivity activity = TestUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateCommute");
			StravaActivity updateResponse = null;
=======
				final StravaActivity activity = TestUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateCommute");
				StravaActivity updateResponse = null;
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git

				final StravaActivityUpdate update = new StravaActivityUpdate();
				final Boolean commute = Boolean.TRUE;
				update.setCommute(commute);

				// Do all the interaction with the Strava API at once
				updateResponse = createUpdateAndDelete(activity, update);

<<<<<<< HEAD
			// Validate the results
			StravaActivityTest.validateActivity(updateResponse);
			assertEquals(commute, updateResponse.getCommute());
		} );
=======
				// Validate the results
				StravaActivityTest.validateActivity(updateResponse);
				assertEquals(commute, updateResponse.getCommute());
			});
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	}

	@Override
<<<<<<< HEAD
	@Test
=======
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	public void testUpdateActivity_validUpdateDescription() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up test date
<<<<<<< HEAD
			final StravaActivity activity = TestUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateDescription");
=======
				final StravaActivity activity = TestUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateDescription");
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git

				final TextProducer text = Fairy.create().textProducer();
				final StravaActivityUpdate update = new StravaActivityUpdate();
				final String description = text.sentence();
				update.setDescription(description);

				// Do all the interaction with the Strava API at once
				final StravaActivity response = createUpdateAndDelete(activity, update);

<<<<<<< HEAD
			// Test the response
			StravaActivityTest.validateActivity(response);
			assertEquals(description, response.getDescription());
		} );
=======
				// Test the response
				StravaActivityTest.validateActivity(response);
				assertEquals(description, response.getDescription());
			});
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	}

	@Override
<<<<<<< HEAD
	@Test
=======
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	public void testUpdateActivity_validUpdateGearId() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// set up the test data
<<<<<<< HEAD
			final StravaActivity activity = TestUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateGearId");
			final StravaActivityUpdate update = new StravaActivityUpdate();
			final String gearId = TestUtils.GEAR_VALID_ID;
			update.setGearId(gearId);
=======
				final StravaActivity activity = TestUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateGearId");
				final StravaActivityUpdate update = new StravaActivityUpdate();
				final String gearId = TestUtils.GEAR_VALID_ID;
				update.setGearId(gearId);
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git

				// Do all the Strava API interaction at once
				final StravaActivity response = createUpdateAndDelete(activity, update);

<<<<<<< HEAD
			// Validate the results
			StravaActivityTest.validateActivity(response);
			assertEquals(gearId, response.getGearId());
		} );
=======
				// Validate the results
				StravaActivityTest.validateActivity(response);
				assertEquals(gearId, response.getGearId());
			});
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	}

	@Override
<<<<<<< HEAD
	@Test
=======
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	public void testUpdateActivity_validUpdateGearIDNone() throws Exception {
		RateLimitedTestRunner.run(() -> {

			// Set up all the test data
<<<<<<< HEAD
			final StravaActivity activity = TestUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateGearIdNone");
=======
				final StravaActivity activity = TestUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateGearIdNone");
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git

				final StravaActivityUpdate update = new StravaActivityUpdate();
				final String gearId = "none";
				update.setGearId(gearId);

				// Do all the Strava API interaction at once
				final StravaActivity response = createUpdateAndDelete(activity, update);

<<<<<<< HEAD
			// Validate the results
			StravaActivityTest.validateActivity(response);
			assertNull(response.getGearId());
		} );
=======
				// Validate the results
				StravaActivityTest.validateActivity(response);
				assertNull(response.getGearId());
			});
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
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
	 * <li>gear_id (also allows special case of 'none' which should remove the
	 * gear)</li>
	 * <li>description</li>
	 * </ol>
	 *
	 * @throws Exception
	 *
	 */
	@Override
<<<<<<< HEAD
	@Test
=======
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	public void testUpdateActivity_validUpdateName() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up the test data
<<<<<<< HEAD
			final StravaActivity activity = TestUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateName");
=======
				final StravaActivity activity = TestUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateName");
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git

				final TextProducer text = Fairy.create().textProducer();

				final StravaActivityUpdate update = new StravaActivityUpdate();
				final String sentence = text.sentence();
				update.setName(sentence);

				// Do all the Strava API interaction at once
				final StravaActivity response = createUpdateAndDelete(activity, update);

				// Validate the results
				StravaActivityTest.validateActivity(response);
				assertEquals(sentence, response.getName());

<<<<<<< HEAD
		} );
=======
			});
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	}

	@Override
<<<<<<< HEAD
	@Test
=======
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	public void testUpdateActivity_validUpdatePrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// set up the test data
<<<<<<< HEAD
			final StravaActivity activity = TestUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdatePrivate");
=======
				final StravaActivity activity = TestUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdatePrivate");
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git

				final StravaActivityUpdate update = new StravaActivityUpdate();
				final Boolean privateFlag = Boolean.TRUE;
				update.setPrivateActivity(privateFlag);

				// Do all the Strava API interaction at once
				final StravaActivity response = createUpdateAndDelete(activity, update);

<<<<<<< HEAD
			// Validate the results
			StravaActivityTest.validateActivity(response);
			assertEquals(privateFlag, response.getPrivateActivity());
		} );
=======
				// Validate the results
				StravaActivityTest.validateActivity(response);
				assertEquals(privateFlag, response.getPrivateActivity());
			});
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	}

	@Override
<<<<<<< HEAD
	@Test
=======
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	public void testUpdateActivity_validUpdatePrivateNoViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// TODO This is a workaround for issue javastravav3api#72
				if (new Issue72().isIssue()) {
					return;
				}
				// End of workaround

<<<<<<< HEAD
			final StravaActivity activity = TestUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdatePrivateNoViewPrivate");
			activity.setPrivateActivity(Boolean.TRUE);
=======
				final StravaActivity activity = TestUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdatePrivateNoViewPrivate");
				activity.setPrivateActivity(Boolean.TRUE);
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git

<<<<<<< HEAD
			// Create the activity
			StravaActivity response = apiWithFullAccess().createManualActivityAsync(activity).get();
			assertEquals(Boolean.TRUE, response.getPrivateActivity());
=======
				// Create the activity
				StravaActivity response = apiWithFullAccess().createManualActivityAsync(activity).get();
				assertEquals(Boolean.TRUE, response.getPrivateActivity());
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git

<<<<<<< HEAD
			// Try to update it without view private
			activity.setDescription("Updated description");
			try {
				response = apiWithWriteAccess()
						.updateActivityAsync(response.getId(), new StravaActivityUpdate(activity)).get();
			} catch (final UnauthorizedException e) {
				// expected
=======
				// Try to update it without view private
				activity.setDescription("Updated description");
				try {
					response = apiWithWriteAccess().updateActivityAsync(response.getId(), new StravaActivityUpdate(activity)).get();
				} catch (final UnauthorizedException e) {
					// expected
					forceDeleteActivity(response);
					return;
				}
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
				forceDeleteActivity(response);
				fail("Updated private activity without view_private authorisation");

<<<<<<< HEAD
		} );
=======
			});
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	}

	@Override
<<<<<<< HEAD
	@Test
=======
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	public void testUpdateActivity_validUpdateTrainer() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up the test data
<<<<<<< HEAD
			final StravaActivity activity = TestUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateTrainer");
=======
				final StravaActivity activity = TestUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateTrainer");
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git

				final StravaActivityUpdate update = new StravaActivityUpdate();
				final Boolean trainer = Boolean.TRUE;
				update.setTrainer(trainer);

				// Do all the Strava API interaction at once
				final StravaActivity response = createUpdateAndDelete(activity, update);

<<<<<<< HEAD
			// Validate the results
			StravaActivityTest.validateActivity(response);
			assertEquals(trainer, response.getTrainer());
		} );
=======
				// Validate the results
				StravaActivityTest.validateActivity(response);
				assertEquals(trainer, response.getTrainer());
			});
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	}

	@Override
<<<<<<< HEAD
	@Test
=======
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	public void testUpdateActivity_validUpdateType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Set up the test data
<<<<<<< HEAD
			final StravaActivity activity = TestUtils
					.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateType");
			activity.setType(StravaActivityType.ALPINE_SKI);
=======
				final StravaActivity activity = TestUtils.createDefaultActivity("UpdateActivityTest.testUpdateActivity_validUpdateType");
				activity.setType(StravaActivityType.ALPINE_SKI);
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git

				final StravaActivityUpdate update = new StravaActivityUpdate();
				final StravaActivityType type = StravaActivityType.RIDE;
				update.setType(type);

				// Do all the Strava API interaction at once
				final StravaActivity response = createUpdateAndDelete(activity, update);

<<<<<<< HEAD
			// Validate the results
			StravaActivityTest.validateActivity(response);
			assertEquals(type, response.getType());
		} );
=======
				// Validate the results
				StravaActivityTest.validateActivity(response);
				assertEquals(type, response.getType());
			});
>>>>>>> branch 'master' of https://github.com/danshannon/javastrava-test.git
	}

}
