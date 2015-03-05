package test.api.service.impl.retrofit.activityservices;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.ActivityServices;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.ActivityServicesImpl;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.utils.TestUtils;

public class GiveKudosTest {

	@Test
	public void testGiveKudos_activityOtherUser() throws NotFoundException {
		service().giveKudos(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);

		// Check that kudos is now given
		final List<StravaAthlete> kudoers = service().listActivityKudoers(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);

		boolean found = false;
		for (final StravaAthlete athlete : kudoers) {
			StravaAthleteTest.validateAthlete(athlete);
			if (athlete.getId().equals(TestUtils.ATHLETE_AUTHENTICATED_ID)) {
				found = true;
			}
		}
		assertTrue(found);
	}

	@Test
	public void testGiveKudos_activityAuthenticatedUser() throws NotFoundException {
		service().giveKudos(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);

		// Check that kudos was NOT given
		final List<StravaAthlete> kudoers = service().listActivityKudoers(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);

		boolean found = false;
		for (final StravaAthlete athlete : kudoers) {
			StravaAthleteTest.validateAthlete(athlete);
			if (athlete.getId().equals(TestUtils.ATHLETE_AUTHENTICATED_ID)) {
				found = true;
			}
		}
		assertFalse(found);
	}

	@Test
	public void testGiveKudos_activityInvalid() {
		try {
			service().giveKudos(TestUtils.ACTIVITY_INVALID);
		} catch (final NotFoundException e) {
			// Expected behaviour
			return;
		}

		fail("Gave kudos to a non-existent activity");

	}

	@Test
	public void testGiveKudos_activityPrivateOtherUser() throws NotFoundException {
		try {
			service().giveKudos(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
		} catch (final UnauthorizedException e) {
			// Expected
			return;
		}
		fail("Gave kudos to a private activity that belongs to another user");
	}

	@Test
	public void testGiveKudos_noWriteAccess() throws NotFoundException {
		try {
			serviceWithoutWriteAccess().giveKudos(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER);
		} catch (final UnauthorizedException e) {
			// Expected
			return;
		}
		// TODO This is a workaround for issue javastrava-api #29 (https://github.com/danshannon/javastravav3api/issues/29)
		// fail("Gave kudos without write access");
	}

	private ActivityServices service() {
		return ActivityServicesImpl.implementation(TestUtils.getValidToken());
	}

	private ActivityServices serviceWithoutWriteAccess() {
		return ActivityServicesImpl.implementation(TestUtils.getValidTokenWithoutWriteAccess());
	}

}
