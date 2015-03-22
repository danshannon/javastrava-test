package test.api.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.service.impl.util.ListCallback;
import test.api.service.impl.util.PagingListMethodTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;
import test.utils.TestUtils;

public class ListActivityKudoersTest extends PagingListMethodTest<StravaAthlete, Integer> {
	/**
	 * <p>
	 * List {@link StravaAthlete athletes} giving kudos for an {@link StravaActivity} which has >0 kudos
	 * </p>
	 * 
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityKudoers_hasKudoers() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaAthlete> kudoers = service().listActivityKudoers(TestUtils.ACTIVITY_WITH_KUDOS);

				assertNotNull("Returned null kudos array for activity with kudos", kudoers);
				assertNotEquals("Returned empty kudos array for activity with kudos", 0, kudoers.size());
				for (final StravaAthlete athlete : kudoers) {
					StravaAthleteTest.validateAthlete(athlete);
				}
			}
		});
	}

	/**
	 * <p>
	 * List {@link StravaAthlete athletes} giving kudos for an {@link StravaActivity} which has NO kudos
	 * </p>
	 *
	 * <p>
	 * Should return an empty array of {@link StravaAthlete athletes}
	 * </p>
	 * 
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 * @throws NotFoundException
	 */
	@Test
	public void testListActivityKudoers_hasNoKudoers() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaAthlete> kudoers = service().listActivityKudoers(TestUtils.ACTIVITY_WITHOUT_KUDOS);

				assertNotNull("Returned null kudos array for activity without kudos", kudoers);
				assertEquals("Did not return empty kudos array for activity with no kudos", 0, kudoers.size());
			}
		});
	}

	/**
	 * <p>
	 * Attempt to list {@link StravaAthlete athletes} giving kudos for an {@link StravaActivity} which does not exist
	 * </p>
	 *
	 * <p>
	 * Should return <code>null</code>
	 * </p>
	 * 
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityKudoers_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaAthlete> kudoers = service().listActivityKudoers(TestUtils.ACTIVITY_INVALID);

				assertNull("Returned a non-null array of kudoers for an invalid activity", kudoers);
			}
		});
	}

	@Test
	public void testListActivityKudoers_privateActivity() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaAthlete> kudoers = service().listActivityKudoers(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
				assertNotNull(kudoers);
				assertEquals(0, kudoers.size());
			}
		});
	}

	@Override
	protected void validate(final StravaAthlete athlete, final Integer id, final StravaResourceState state) {
		StravaAthleteTest.validateAthlete(athlete, id, state);

	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		validate(athlete, athlete.getId(), athlete.getResourceState());

	}

	@Override
	protected ListCallback<StravaAthlete> callback() {
		return (new ListCallback<StravaAthlete>() {

			@Override
			public List<StravaAthlete> getList(final Paging paging) {
				return service().listActivityKudoers(TestUtils.ACTIVITY_WITH_KUDOS, paging);
			}

		});
	}
}
