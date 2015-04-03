package test.api.rest;

import static org.junit.Assert.assertEquals;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class APITest {
	public static void forceDeleteActivity(final Integer activityId) {
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

	public static API api() {
		final API api = new API(TestUtils.getValidToken());
		return api;
	}

	public static API apiWithFullAccess() {
		final API api = new API(TestUtils.getValidTokenWithFullAccess());
		return api;
	}

	public static API apiWithViewPrivate() {
		final API api = new API(TestUtils.getValidTokenWithViewPrivate());
		return api;
	}

	public static API apiWithWriteAccess() {
		final API api = new API(TestUtils.getValidTokenWithWriteAccess());
		return api;
	}

	/**
	 * @param activityId
	 * @param comment
	 * @return
	 */
	public static StravaComment forceCreateComment(Integer activityId, String comment) throws Exception {
		return apiWithFullAccess().createComment(activityId, comment);
	}

	/**
	 * @return
	 */
	public static StravaComment createPrivateActivityWithComment(final String name) throws Exception {
		final StravaActivity activity = createPrivateActivity(name);
		StravaComment comment = forceCreateComment(activity.getId(),"name");
		return comment;
	}

	public static StravaActivity createPrivateActivity(final String name) {
		final StravaActivity activity = TestUtils.createDefaultActivity(name);
		activity.setPrivateActivity(Boolean.TRUE);
		final StravaActivity response = TestUtils.stravaWithFullAccess().createManualActivity(activity);
		assertEquals(Boolean.TRUE, response.getPrivateActivity());
		return response;
	}
	
}
