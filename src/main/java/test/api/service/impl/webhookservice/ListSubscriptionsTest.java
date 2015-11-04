/**
 *
 */
package test.api.service.impl.webhookservice;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.webhook.StravaEventSubscription;

import org.junit.Test;

import test.api.model.StravaEventSubscriptionTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;

/**
 * @author danshannon
 *
 */
public class ListSubscriptionsTest extends StravaTest {
	private final boolean stravaLetsMeUseThisBit = false;

	@Test
	public void list() throws Exception {
		if (!this.stravaLetsMeUseThisBit) {
			return;
		}
		RateLimitedTestRunner.run(() -> {
			final List<StravaEventSubscription> subs = strava().listSubscriptions();
			assertNotNull(subs);
			for (final StravaEventSubscription sub : subs) {
				StravaEventSubscriptionTest.validate(sub);
			}
		});
	}
}
