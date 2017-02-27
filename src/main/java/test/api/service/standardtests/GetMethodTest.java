package test.api.service.standardtests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaEntity;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.service.standardtests.spec.GetTests;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 * @param <T>
 *            The type of Strava object under test
 * @param <U>
 *            The object type's identifier class
 */
public abstract class GetMethodTest<T extends StravaEntity, U> extends MethodTest<T, U> implements GetTests<T, U> {

	protected abstract U getIdValid();

	protected abstract U getIdInvalid();

	protected abstract U getIdPrivate();

	protected abstract U getIdPrivateBelongsToOtherUser();

	@Override
	@Test
	public void testPrivateBelongsToOtherUser() throws Exception {
		// Don't run if the id to test against is null
		if (getIdPrivateBelongsToOtherUser() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			final U id = getIdPrivateBelongsToOtherUser();

			// If there's Nosaj Thing, then quit
			if (id == null) {
				return;
			}

			// Get the data
			try {
				getter().get(TestUtils.strava(), id);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}

			fail("Retrieved object that is private and belongs to another user!"); //$NON-NLS-1$
		});

	}

	@Override
	@Test
	public void testPrivateWithViewPrivateScope() throws Exception {
		// Don't run if the id to test against is null
		if (getIdPrivate() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {

			final U id = getIdPrivate();

			// If there's Nosaj Thing, then quit
			if (id == null) {
				return;
			}

			// Get the data - it should work; if it doesn't there'll be an UnauthorisedException thrown
			final T object = getter().get(TestUtils.stravaWithViewPrivate(), id);
			assertNotNull(object);
			validate(object);
		});
	}

	@Override
	@Test
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		// Don't run if the id to test against is null
		if (getIdPrivate() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {

			final U id = getIdPrivate();

			// If there's Nosaj Thing, then quit
			if (id == null) {
				return;
			}

			// Get the data - it should throw an UnauthorisedException
			try {
				getter().get(TestUtils.strava(), id);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}

			fail("Retrieved object that is private, and token does not have view_private scope!"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testInvalidId() throws Exception {
		// Catered for by testGetInvalidId()
		return;

	}

	@Override
	@Test
	public void testGetInvalidId() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Don't run if the id to test against is null
			if (getIdInvalid() == null) {
				return;
			}

			final U id = getIdInvalid();

			// If there's Nosaj Thing, then quit
			if (id == null) {
				return;
			}

			// Get the data - it should be null
			final T object = getter().get(TestUtils.strava(), id);

			assertNull("Retrieved object that has an invalid id!", object); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testGetValidId() throws Exception {
		// Don't run if the id to test against is null
		if (getIdValid() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {

			final U id = getIdValid();

			// If there's Nosaj Thing, then quit
			if (id == null) {
				return;
			}

			// Get the data - it should work; if it doesn't there'll be an Exception thrown
			final T object = getter().get(TestUtils.strava(), id);
			assertNotNull(object);
			validate(object);
		});
	}

	@Override
	public void testGetNullId() throws Exception {
		final T object = getter().get(TestUtils.strava(), null);
		assertNull(object);
	}

}
