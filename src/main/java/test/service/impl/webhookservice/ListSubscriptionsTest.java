/**
 *
 */
package test.service.impl.webhookservice;

import javastrava.api.v3.model.webhook.StravaEventSubscription;
import javastrava.api.v3.service.Strava;
import test.api.model.webhook.StravaEventSubscriptionTest;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.AthleteDataUtils;

/**
 * <p>
 * Specific tests for {@link Strava#listSubscriptions() methods}
 * </p>
 *
 * @author Dan Shannon
 */
public class ListSubscriptionsTest extends ListMethodTest<StravaEventSubscription, Integer> {

	@Override
	protected ListCallback<StravaEventSubscription, Integer> lister() {
		return ((strava, id) -> strava.listSubscriptions());
	}

	@Override
	protected Integer idPrivate() {
		return null;
	}

	@Override
	protected Integer idPrivateBelongsToOtherUser() {
		return null;
	}

	@Override
	protected Integer idValidWithEntries() {
		return AthleteDataUtils.ATHLETE_AUTHENTICATED_ID;
	}

	@Override
	protected Integer idValidWithoutEntries() {
		return null;
	}

	@Override
	protected Integer idInvalid() {
		return null;
	}

	@Override
	protected void validate(StravaEventSubscription object) {
		StravaEventSubscriptionTest.validate(object);
	}

}
