package test.api.rest;

import static org.junit.Assert.assertEquals;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import test.utils.TestUtils;

/**
 * <p>
 * API test specifications
 * </p>
 *
 * @author Dan Shannon
 */
public abstract class APITest<T> {
	/**
	 * @return Get an API instance, without write access or view_private scope
	 */
	public static API api() {
		final API api = new API(TestUtils.getValidToken());
		return api;
	}

	/**
	 * @return Get an API instance, without view_private scope
	 */
	public static API apiWithFullAccess() {
		final API api = new API(TestUtils.getValidTokenWithFullAccess());
		return api;
	}

	/**
	 * @return Get an API instance, without write access
	 */
	public static API apiWithViewPrivate() {
		final API api = new API(TestUtils.getValidTokenWithViewPrivate());
		return api;
	}

	/**
	 * @return Get an API instance, with full access
	 */
	public static API apiWithWriteAccess() {
		final API api = new API(TestUtils.getValidTokenWithWriteAccess());
		return api;
	}

	/**
	 * <p>
	 * Create a private activity
	 * </p>
	 * 
	 * @param name
	 *            Name to give to the activity
	 * @return The activity created
	 */
	public static StravaActivity createPrivateActivity(final String name) {
		final StravaActivity activity = TestUtils.createDefaultActivity(name);
		activity.setPrivateActivity(Boolean.TRUE);
		final StravaActivity response = TestUtils.stravaWithFullAccess().createManualActivity(activity);
		assertEquals(Boolean.TRUE, response.getPrivateActivity());
		return response;
	}

	/**
	 * @return
	 */
	public static StravaComment createPrivateActivityWithComment(final String name) throws Exception {
		final StravaActivity activity = createPrivateActivity(name);
		final StravaComment comment = forceCreateComment(activity.getId(), "name");
		return comment;
	}

	/**
	 * @param activityId
	 * @param comment
	 * @return
	 */
	public static StravaComment forceCreateComment(final Long activityId, final String comment) throws Exception {
		return apiWithFullAccess().createComment(activityId, comment);
	}

	public static void forceDeleteActivity(final Long activityId) {
		if (activityId == null) {
			return;
		}
		try {
			apiWithFullAccess().deleteActivity(activityId);
		} catch (final NotFoundException e) {
			// ignore
		}
	}

	public static StravaActivity forceDeleteActivity(final StravaActivity activity) {
		if (activity == null) {
			return null;
		}
		try {
			return apiWithFullAccess().deleteActivity(activity.getId());
		} catch (final NotFoundException e) {
			// Ignore
			return null;
		}
	}

	public static void forceDeleteComment(final StravaComment comment) {
		try {
			apiWithFullAccess().deleteComment(comment.getActivityId(), comment.getId());
		} catch (final NotFoundException e) {
			// Ignore
		}
	}

	/**
	 * @param result
	 */
	protected abstract void validate(T result) throws Exception;
}
