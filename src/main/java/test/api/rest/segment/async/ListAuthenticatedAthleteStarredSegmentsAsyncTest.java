/**
 *
 */
package test.api.rest.segment.async;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegment;
import test.api.rest.callback.TestListArrayCallback;
import test.api.rest.segment.ListAuthenticatedAthleteStarredSegmentsTest;
import test.api.rest.util.ArrayCallback;
import test.issues.strava.Issue71;
import test.utils.RateLimitedTestRunner;

/**
 * @author Dan Shannon
 *
 */
public class ListAuthenticatedAthleteStarredSegmentsAsyncTest extends ListAuthenticatedAthleteStarredSegmentsTest {
	/**
	 * @see test.api.rest.segment.ListAuthenticatedAthleteStarredSegmentsTest#pagingCallback()
	 */
	@Override
	protected ArrayCallback<StravaSegment> pagingCallback() {
		return paging -> api().listAuthenticatedAthleteStarredSegmentsAsync(paging.getPage(), paging.getPageSize()).get();
	}

	/**
	 * @see test.api.rest.segment.ListAuthenticatedAthleteStarredSegmentsTest#listCallback()
	 */
	@Override
	protected TestListArrayCallback<StravaSegment, Integer> listCallback() {
		return (api, id) -> api.listAuthenticatedAthleteStarredSegmentsAsync(null, null).get();
	}

	@Override
	public void list_privateWithoutViewPrivate() throws Exception {
		// TODO This is a workaround for issue javastravav3api#71
		final Issue71 issue71 = new Issue71();
		if (issue71.isIssue()) {
			return;
		}
		// End of workaround

		final StravaSegment[] segments = this.listCallback().list(api(), null);
		for (final StravaSegment segment : segments) {
			if ((segment.getPrivateSegment() != null) && segment.getPrivateSegment().equals(Boolean.TRUE)) {
				fail("Returned at least one private starred segment");
			}
		}
	}

	@Override
	@Test
	public void list_private() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment[] segments = this.listCallback().list(apiWithViewPrivate(), null);
			boolean pass = false;
			for (final StravaSegment segment : segments) {
				if (segment.getPrivateSegment()) {
					pass = true;
				}
			}
			assertTrue(pass);
		});
	}

}
