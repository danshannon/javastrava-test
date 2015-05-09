/**
 *
 */
package test.api.service.impl.webhookservice;

import java.util.List;

import javastrava.api.v3.model.webhook.StravaEventSubscription;

import org.junit.Test;

import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;

/**
 * @author danshannon
 *
 */
public class ListSubscriptionsTest extends StravaTest {
	@Test
	public void list() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final List<StravaEventSubscription> subs = strava().listSubscriptions();
		});
	}
}
