package test.api.rest.activity;

import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.rest.APICreateTest;
import test.api.rest.TestCreateCallback;
import test.issues.strava.Issue49;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class CreateManualActivityTest extends APICreateTest<StravaActivity, Integer> {
	/**
	 * No argument constructor sets up the callback to use to create the manual activity
	 */
	public CreateManualActivityTest() {
		super();
		this.creationCallback = new TestCreateCallback<StravaActivity, Integer>() {

			@Override
			public StravaActivity run(final API api, final StravaActivity activity, final Integer id) throws Exception {
				return api.createManualActivity(activity);
			}
		};
	}

	@Test
	public void testCreateManualActivity_invalidType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// This is only a workaround for issue javastravav3api#49
				if (new Issue49().isIssue()) {
					return;
				}
				// End of workaround

				// Type must be one of the specified values
				final StravaActivity activity = createObject();
				StravaActivity stravaResponse = null;
				activity.setType(StravaActivityType.UNKNOWN);
				try {
					stravaResponse = this.creationCallback.run(apiWithWriteAccess(), activity, null);
				} catch (final BadRequestException e1) {
					// Expected behaviour
					return;
				}
				// If it did get created, delete it again
				forceDelete(stravaResponse);
				fail("Created an activity with invalid type in error (was " + StravaActivityType.UNKNOWN + ", is " + stravaResponse.getType() + ")");
			});
	}

	@Test
	public void testCreateManualActivity_noElapsedTime() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = createObject();
			StravaActivity stravaResponse = null;
			// Elapsed time is required
				activity.setElapsedTime(null);
				try {
					stravaResponse = this.creationCallback.run(apiWithWriteAccess(), activity, null);
				} catch (final BadRequestException e1) {
					// Expected behaviour
					return;
				}

				// If it did get created, delete it again
				forceDelete(stravaResponse);

				fail("Created an activity with no elapsed time in error" + stravaResponse);
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
		RateLimitedTestRunner.run(() -> {
			// Name is required
				final StravaActivity activity = createObject();
				StravaActivity stravaResponse = null;
				activity.setDescription(activity.getName());
				activity.setName(null);
				try {
					stravaResponse = this.creationCallback.run(apiWithWriteAccess(), activity, null);
				} catch (final BadRequestException e1) {
					// Expected behaviour
				return;
			}

			// If it did get created, delete it again
			if (stravaResponse != null) {
				forceDelete(stravaResponse);
			}

			fail("Created an activity with no name in error" + stravaResponse);
		});
	}

	@Test
	public void testCreateManualActivity_noStartDate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = createObject();
			;
			StravaActivity stravaResponse = null;
			// Start date is required
				activity.setStartDateLocal(null);
				try {
					stravaResponse = this.creationCallback.run(apiWithWriteAccess(), activity, null);
				} catch (final BadRequestException e) {
					// Expected behaviour
					return;
				}

				// If it did get created, delete it again
				forceDelete(stravaResponse);

				fail("Created an activity with no start date in error" + stravaResponse);
			});
	}

	@Test
	public void testCreateManualActivity_noType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Type is required
				final StravaActivity activity = createObject();
				StravaActivity stravaResponse = null;
				activity.setType(null);
				try {
					stravaResponse = this.creationCallback.run(apiWithWriteAccess(), activity, null);
				} catch (final BadRequestException e1) {
					// Expected behaviour
				return;
			}

			// If it did get created, delete it again
			forceDelete(stravaResponse);

			fail("Created an activity with no type in error" + stravaResponse);
		});
	}

	/**
	 * @see test.api.rest.APICreateTest#createObject()
	 */
	@Override
	protected StravaActivity createObject() {
		return TestUtils.createDefaultActivity("CreateManualActivityTest");
	}

	/**
	 * @see test.api.rest.APICreateTest#invalidParentId()
	 */
	@Override
	protected Integer invalidParentId() {
		return null;
	}

	/**
	 * @see test.api.rest.APICreateTest#privateParentId()
	 */
	@Override
	protected Integer privateParentId() {
		return null;
	}

	/**
	 * @see test.api.rest.APICreateTest#validParentId()
	 */
	@Override
	protected Integer validParentId() {
		return null;
	}

	/**
	 * @see test.api.rest.APICreateTest#privateParentOtherUserId()
	 */
	@Override
	protected Integer privateParentOtherUserId() {
		return null;
	}

	/**
	 * @see test.api.rest.APICreateTest#forceDelete(java.lang.Object)
	 */
	@Override
	protected void forceDelete(final StravaActivity activity) {
		forceDeleteActivity(activity);
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaActivity activity) throws Exception {
		StravaActivityTest.validateActivity(activity);
	}

	/**
	 * @see test.api.rest.APITest#validateList(java.util.List)
	 */
	@Override
	protected void validateList(final List<StravaActivity> activities) throws Exception {
		StravaActivityTest.validateList(activities);
	}

	/**
	 * @see test.api.rest.APICreateTest#create_invalidParent()
	 */
	@Override
	public void create_invalidParent() throws Exception {
		return;
	}

	/**
	 * @see test.api.rest.APICreateTest#create_privateParentWithViewPrivate()
	 */
	@Override
	public void create_privateParentWithViewPrivate() throws Exception {
		return;
	}

	/**
	 * @see test.api.rest.APICreateTest#create_privateParentWithoutViewPrivate()
	 */
	@Override
	public void create_privateParentWithoutViewPrivate() throws Exception {
		return;
	}

	/**
	 * @see test.api.rest.APICreateTest#create_privateParentBelongsToOtherUser()
	 */
	@Override
	public void create_privateParentBelongsToOtherUser() throws Exception {
		return;
	}

}
