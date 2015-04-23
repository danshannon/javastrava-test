package test.api.rest.async;

import static org.junit.Assert.assertNotNull;

import java.util.concurrent.CompletableFuture;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.rest.async.AsyncAPI;

import org.junit.Test;

import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public class GetActivityTest {
	@Test
	public void testGetActivity() throws Exception {
		final AsyncAPI api = new AsyncAPI(TestUtils.getValidToken());

		final CompletableFuture<StravaActivity> cf = new CompletableFuture<StravaActivity>();
		api.getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, Boolean.FALSE, cf);

		// Do a load of other stuff...

		final StravaActivity activity = cf.get();
		assertNotNull(activity);
	}
}
