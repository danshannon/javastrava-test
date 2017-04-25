package test.service.standardtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assume.assumeFalse;

import org.junit.Test;

import javastrava.model.StravaEntity;
import javastrava.model.reference.StravaResourceState;
import test.service.standardtests.spec.GetTests;
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

	/**
	 * @return Identifier for an entity that does not exist on Strava
	 */
	protected abstract U getIdInvalid();

	/**
	 * @return Identifier for an entity that exists on Strava, belongs to the authenticated user, and is flagged as private
	 */
	protected abstract U getIdPrivate();

	/**
	 * @return Identifier for an entity that exists on Strava, belongs to someone other than the authenticated user, and is flagged as private
	 */
	protected abstract U getIdPrivateBelongsToOtherUser();

	/**
	 * @return Identifier for an entity that exists on Strava, belongs to the authenticated user, and is NOT flagged as private
	 */
	protected abstract U getIdValid();

	@Override
	@Test
	public void testGetInvalidId() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Don't run if the id to test against is null
			assumeFalse(getIdInvalid() == null);

			final U id = getIdInvalid();

			// Get the data - it should be null
			final T object = getter().get(TestUtils.strava(), id);

			assertNull("Retrieved object that has an invalid id!", object); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testGetNullId() throws Exception {
		final T object = getter().get(TestUtils.strava(), null);
		assertNull(object);
	}

	@Override
	@Test
	public void testGetValidId() throws Exception {
		// Don't run if the id to test against is null
		assumeFalse(getIdValid() == null);

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
	@Test
	public void testInvalidId() throws Exception {
		// Test is dealt with by testGetInvalidId()
		assumeFalse(true);
	}

	@Override
	@Test
	public void testPrivateBelongsToOtherUser() throws Exception {
		// Don't run if the id to test against is null
		assumeFalse(getIdPrivateBelongsToOtherUser() == null);

		RateLimitedTestRunner.run(() -> {
			final U id = getIdPrivateBelongsToOtherUser();

			// If there's Nosaj Thing, then quit
			if (id == null) {
				return;
			}

			// Get the data
			final T object = getter().get(TestUtils.strava(), id);
			assertEquals(StravaResourceState.PRIVATE, object.getResourceState());
		});

	}

	@Override
	@Test
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		// Don't run if the id to test against is null
		assumeFalse(getIdPrivate() == null);

		RateLimitedTestRunner.run(() -> {

			final U id = getIdPrivate();

			// If there's Nosaj Thing, then quit
			if (id == null) {
				return;
			}

			// Get the data - it should return an object with resource state PRIVATE
			final T object = getter().get(TestUtils.strava(), id);
			assertEquals(StravaResourceState.PRIVATE, object.getResourceState());
		});
	}

	@Override
	@Test
	public void testPrivateWithViewPrivateScope() throws Exception {
		// Don't run if the id to test against is null
		assumeFalse(getIdPrivate() == null);

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

}
