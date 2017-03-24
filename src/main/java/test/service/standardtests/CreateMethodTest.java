/**
 *
 */
package test.service.standardtests;

import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaEntity;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.service.standardtests.callbacks.CreateCallback;
import test.service.standardtests.callbacks.DeleteCallback;
import test.service.standardtests.spec.CreateTests;
import test.service.standardtests.spec.PrivacyTests;
import test.service.standardtests.spec.StandardTests;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Standard tests for API methods that create data
 * </p>
 *
 *
 * @author Dan Shannon
 * @param <T>
 *            class of the object being created
 * @param <U>
 *            class of the object's identifier (usually Integer)
 */
public abstract class CreateMethodTest<T extends StravaEntity, U> extends MethodTest<T, U> implements CreateTests, StandardTests, PrivacyTests {
	/**
	 * @return The callback to use to create test data
	 */
	protected abstract CreateCallback<T> creator();

	/**
	 * @return The callback to use to delete test data
	 */
	protected abstract DeleteCallback<T> deleter();

	/**
	 * Force deletes the given object
	 *
	 * @param object
	 *            The object to be deleted
	 * @throws Exception
	 *             if something goes wrong in an unexpected way
	 */
	protected void forceDelete(T object) throws Exception {
		this.deleter().delete(TestUtils.stravaWithFullAccess(), object);
	}

	/**
	 * Create test data - an object which is not valid for persisting to Strava
	 * 
	 * @return An invalid object
	 */
	protected abstract T generateInvalidObject();

	/**
	 * Create test data - an object which is valid for persisting to Strava
	 * 
	 * @return A valid object
	 */
	protected abstract T generateValidObject();

	@Override
	@Test
	public void testCreateInvalidObject() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Create the object test data
			final T object = generateInvalidObject();

			T createdObject;

			// Create it in Strava
			try {
				createdObject = creator().create(TestUtils.stravaWithFullAccess(), object);
			} catch (final IllegalArgumentException e) {
				// Expected
				return;
			}
			// Finally, delete it
			forceDelete(createdObject);
			fail("Created an invalid object"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testCreateNoWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Create the object test data
			final T object = generateValidObject();

			T createdObject;

			// Create it in Strava
			try {
				createdObject = creator().create(TestUtils.strava(), object);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}

			// Finally, delete it
			forceDelete(createdObject);
			fail("Created an object, without write access"); //$NON-NLS-1$
		});
	}

	/**
	 * @see test.service.standardtests.spec.CreateTests#testCreateValidObject()
	 */
	@Override
	@Test
	public void testCreateValidObject() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Create the object test data
			final T object = generateValidObject();

			// Create it in Strava
			final T createdObject = creator().create(TestUtils.stravaWithFullAccess(), object);

			// Finally, delete it
			forceDelete(createdObject);
		});

	}

}
