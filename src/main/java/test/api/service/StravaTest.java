package test.api.service;

import static org.junit.Assert.assertTrue;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.service.Strava;
import javastrava.api.v3.service.exception.NotFoundException;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class StravaTest {
	protected void forceDeleteActivity(final Integer activityId) {
		stravaWithFullAccess().deleteActivity(activityId);
	}

	protected StravaActivity forceDeleteActivity(final StravaActivity activity) {
		return stravaWithFullAccess().deleteActivity(activity.getId());
	}

	protected void forceDeleteComment(final StravaComment comment) {
		try {
			stravaWithFullAccess().deleteComment(comment);
		} catch (final NotFoundException e) {
			// Ignore
		}
	}

	protected Strava strava() {
		final Strava strava = TestUtils.strava();
		assertTrue(strava.hasExactAuthorisationScopes());
		return strava;
	}

	protected Strava stravaWithFullAccess() {
		final Strava strava = TestUtils.stravaWithFullAccess();
		assertTrue(strava.hasExactAuthorisationScopes(AuthorisationScope.WRITE, AuthorisationScope.VIEW_PRIVATE));
		return strava;
	}

	protected Strava stravaWithViewPrivate() {
		final Strava strava = TestUtils.stravaWithViewPrivate();
		assertTrue(strava.hasExactAuthorisationScopes(AuthorisationScope.VIEW_PRIVATE));
		return strava;
	}

	protected Strava stravaWithWriteAccess() {
		final Strava strava = TestUtils.stravaWithWriteAccess();
		assertTrue(strava.hasExactAuthorisationScopes(AuthorisationScope.WRITE));
		return strava;
	}

}
