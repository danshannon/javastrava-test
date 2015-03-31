package test.api.rest;

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
	protected void forceDeleteActivity(final Integer activityId) {
		try {
			apiWithFullAccess().deleteActivity(activityId);
		} catch (final NotFoundException e) {
			// ignore
		}
	}

	protected StravaActivity forceDeleteActivity(final StravaActivity activity) {
		try {
			return apiWithFullAccess().deleteActivity(activity.getId());
		} catch (final NotFoundException e) {
			// Ignore
			return null;
		}
	}

	protected void forceDeleteComment(final StravaComment comment) {
		try {
			apiWithFullAccess().deleteComment(comment.getActivityId(), comment.getId());
		} catch (final NotFoundException e) {
			// Ignore
		}
	}

	protected API api() {
		final API api = new API(TestUtils.getValidToken());
		return api;
	}

	protected API apiWithFullAccess() {
		final API api = new API(TestUtils.getValidTokenWithFullAccess());
		return api;
	}

	protected API apiWithViewPrivate() {
		final API api = new API(TestUtils.getValidTokenWithViewPrivate());
		return api;
	}

	protected API apiWithWriteAccess() {
		final API api = new API(TestUtils.getValidTokenWithWriteAccess());
		return api;
	}

}
