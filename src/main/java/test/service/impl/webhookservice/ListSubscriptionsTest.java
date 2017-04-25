/**
 *
 */
package test.service.impl.webhookservice;

import javastrava.config.JavastravaApplicationConfig;
import javastrava.model.webhook.StravaEventSubscription;
import javastrava.service.Strava;
import test.model.webhook.StravaEventSubscriptionTest;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for {@link Strava#listSubscriptions(Integer, String) methods}
 * </p>
 *
 * @author Dan Shannon
 */
public class ListSubscriptionsTest extends ListMethodTest<StravaEventSubscription, Integer> {

	@Override
	protected Class<StravaEventSubscription> classUnderTest() {
		return StravaEventSubscription.class;
	}

	@Override
	protected Integer idInvalid() {
		return null;
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
	protected ListCallback<StravaEventSubscription, Integer> lister() {
		return ((strava, id) -> strava.listSubscriptions(TestUtils.STRAVA_APPLICATION_ID, TestUtils.STRAVA_CLIENT_SECRET));
	}

	@Override
	public void testInvalidId() throws Exception {
		// Can't run the test unless application has Strava's permission to access the webhooks endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_WEBHOOKS_ENDPOINT) {

			super.testInvalidId();
		}
	}

	@Override
	public void testPrivateBelongsToOtherUser() throws Exception {
		// Can't run the test unless application has Strava's permission to access the webhooks endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_WEBHOOKS_ENDPOINT) {

			super.testPrivateBelongsToOtherUser();
		}
	}

	@Override
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		// Can't run the test unless application has Strava's permission to access the webhooks endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_WEBHOOKS_ENDPOINT) {

			super.testPrivateWithNoViewPrivateScope();
		}
	}

	@Override
	public void testPrivateWithViewPrivateScope() throws Exception {
		// Can't run the test unless application has Strava's permission to access the webhooks endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_WEBHOOKS_ENDPOINT) {

			super.testPrivateWithViewPrivateScope();
		}
	}

	@Override
	public void testValidParentWithEntries() throws Exception {
		// Can't run the test unless application has Strava's permission to access the webhooks endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_WEBHOOKS_ENDPOINT) {

			super.testValidParentWithEntries();
		}
	}

	@Override
	public void testValidParentWithNoEntries() throws Exception {
		// Can't run the test unless application has Strava's permission to access the webhooks endpoint
		if (JavastravaApplicationConfig.STRAVA_ALLOWS_WEBHOOKS_ENDPOINT) {

			super.testValidParentWithNoEntries();
		}
	}

	@Override
	protected void validate(StravaEventSubscription object) {
		StravaEventSubscriptionTest.validate(object);
	}

}
