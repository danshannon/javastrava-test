package test.service.impl.athleteservice;

import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.model.StravaAthlete;
import javastrava.service.exception.NotFoundException;
import javastrava.service.exception.UnauthorizedException;
import test.service.standardtests.GetMethodTest;
import test.service.standardtests.callbacks.GetCallback;
import test.service.standardtests.data.AthleteDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific test configs for get athlete methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetAthleteTest extends GetMethodTest<StravaAthlete, Integer> {

	@Override
	protected Integer getIdInvalid() {
		return AthleteDataUtils.ATHLETE_INVALID_ID;
	}

	@Override
	protected Integer getIdPrivate() {
		return null;
	}

	@Override
	protected Integer getIdPrivateBelongsToOtherUser() {
		return AthleteDataUtils.ATHLETE_PRIVATE_ID;
	}

	@Override
	protected Integer getIdValid() {
		return AthleteDataUtils.ATHLETE_VALID_ID;
	}

	@Override
	protected GetCallback<StravaAthlete, Integer> getter() {
		return ((strava, id) -> strava.getAthlete(id));
	}

	@Override
	@Test
	public void testPrivateBelongsToOtherUser() throws Exception {
		// Don't run if the id to test against is null
		if (getIdPrivateBelongsToOtherUser() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			final Integer id = getIdPrivateBelongsToOtherUser();

			// If there's Nosaj Thing, then quit
			if (id == null) {
				return;
			}

			// Get the data - should return OK
			try {
				getter().get(TestUtils.strava(), id);
			} catch (final UnauthorizedException e) {
				fail("Should have returned anonymised version of athlete but got an UnauthorizedException"); //$NON-NLS-1$
			} catch (final NotFoundException e) {
				fail("Should have returned anonymised version of athlete but got an NotFoundException"); //$NON-NLS-1$
			}

		});

	}

	@Override
	protected void validate(StravaAthlete object) {
		AthleteDataUtils.validateAthlete(object);
	}
}
